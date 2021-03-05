/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

/*
 * Add your docs here.*
 */
public class CannonSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public CANSparkMax FlyWheelMotor = new CANSparkMax(RobotMap.FLYWHEELMOTOR, MotorType.kBrushless);
  private CANEncoder wheelEncoder = FlyWheelMotor.getEncoder();
  private CANPIDController wheelPID = FlyWheelMotor.getPIDController();

  private Solenoid cameraFlipper = new Solenoid(RobotMap.CAMERAFLIPER);

  private WPI_TalonSRX TurretSpinner = new WPI_TalonSRX(RobotMap.TURRETSPINNER);

  public Servo VissionServ = new Servo(RobotMap.VISSIONSERVO);

  private DigitalInput leftEnd = new DigitalInput(RobotMap.ENDSTOPCANNONLEFT);
  private DigitalInput rightEnd = new DigitalInput(RobotMap.ENDSTOPCANNONRIGHT);

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");




  //public Servo cameraServo1 = new Servo(RobotMap.SERVO1);

  private double integral_prior = 0;
  private double last_time = 0;
  private double integral_prior2 = 0;
  private double last_time2 = 0;

  private double ServPos = 0.5;

  private static CannonSubsystem m_instance;
	public static synchronized CannonSubsystem getInstance() {
		if (m_instance == null){
			m_instance = new CannonSubsystem();
    }
    return m_instance;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }


  
  //Configure launcher wheel PID
  public void ConfigPID() {
    wheelPID.setP(1e-5);
    wheelPID.setI(6e-9);
    wheelPID.setD(0);
    wheelPID.setIZone(0);
    wheelPID.setFF(0.00017);
    wheelPID.setOutputRange(-1,0);
  }

  public void setPipeline(int pipeline) {
		NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
    	pipelineEntry.setNumber(pipeline);
    }

  //Run launcher wheel at power(0-1)
  public void RunShootWheel(double power) {
    FlyWheelMotor.set(power);
  }

  //Run launcher wheel at a set RPM(0-3600RPM)
  public void RunShootWheelPID(double RPM) {
    wheelPID.setReference(-RPM, ControlType.kVelocity);

  }

  public void setCameraPos(boolean state){
    cameraFlipper.set(state);
  }

  //Maybe redundant?
  public void SetVissionServo(double pos){
    VissionServ.set(pos);
  }


  //Maybe redundant?
  public void SetVissionServoSpeed(double speed){
    VissionServ.set(0.5+(speed/2));

  }

  //maybe redundant?
  public double GetVissionServo(){
    return VissionServ.get();
  }

  //Get current RPM of launcher wheel
  public double getWheelSpeed(){
    return -wheelEncoder.getVelocity();
  }


    //Function for finding speed of launcher wheel
    public double calculateWheelSpeed(double x) {
      return Math.max(0, Math.min(5800,(-94.79*Math.pow(x,3)+1246.22*Math.pow(x,2)-4821.54*x+10915.78)));
    }


  //maybe redudant?
  public void TrackServo(double target) {
    if (target >= 0) {
      double error = 360.0 - target;
      double iteration_time = Timer.getFPGATimestamp() - last_time;
      double integral = Math.max(10, Math.min(-10, integral_prior + error * iteration_time));
      SmartDashboard.putNumber("Turret integral", integral);
      double kp = 0.003*0.45;//0.00003*0.45;
      double ki = (1.2*kp)/1;
      //VissionServ.set(Math.max(0.4, Math.min(0.7, VissionServ.get()+(error*kp + integral*ki))));
      SetVissionServoSpeed(error*kp + integral*ki*0);
      integral_prior = integral;
      last_time = Timer.getFPGATimestamp();
    }
  }

  //Basic manual turning of turret wit endstops.
  public void SpinTurret(double speed){
    if(rightEnd.get() && speed < 0) {
      TurretSpinner.set(0);
    } else if (leftEnd.get() && speed > 0) {
      TurretSpinner.set(0);
    } else {
      TurretSpinner.set(speed*-1);
    }
  }


  //automatic Turret tracking
  public void TrackTurret (double target) {
    //if (target >= 0) {

      //Negative = left/ Positive = Right
      double cameraOffset = -5.3;

      double TrackingSpeed = 5;
      double error = ((target + cameraOffset)*-TrackingSpeed);
      SmartDashboard.putBoolean("leftEndstop", leftEnd.get());
      SmartDashboard.putBoolean("rigthEndstop", rightEnd.get());
      if(rightEnd.get() && error < 0) {
        TurretSpinner.set(0);
        System.out.println("Rigth Endstop hit");
      } else if (leftEnd.get() && error > 0) {
        TurretSpinner.set(0);
        System.out.println("LeftEndstop hit");
      } else {
      double iteration_time = Timer.getFPGATimestamp() - last_time2;
      double integral = Math.max(0.1, Math.min(-0.1, integral_prior2 + error * iteration_time));
      //Kommenter ut *0.45 og endre til det dirrer rundt punktet, derreter ukomenter og gang (1.2*kp)/tiden et dirr tar
      double kp = 0.01;//*0.45;
      double ki = (1.2*kp)/0.2;
      integral_prior2 = integral;
      last_time2 = Timer.getFPGATimestamp();
      SpinTurret((error*kp + integral*ki*0.5));
      }
    
      
    /*} else {
      SpinTurret(0);
    }*/
  }


  //Calculate curent distance to target
  public double CalculateDistance (double yCoord) {
    double pixelsFromBottom = (yCoord);
    double degreesFromBottom = (pixelsFromBottom)*(33.75/240)+5;
    double distance = ((2.04-0.54) / Math.tan(Math.toRadians(degreesFromBottom)));
    SmartDashboard.putNumber("Servo angle", (ServPos-0.39)* 350);
    SmartDashboard.putNumber("Degrees from bottom", degreesFromBottom);
    SmartDashboard.putNumber("Calculated Cannon Power", calculateWheelSpeed(distance));
    SmartDashboard.putNumber("Calculated distance", distance);
    Robot.distance = distance;
    return distance;
  }

  public double findDistance(){
    double cameraAngle = 0.0;
    double cameraHeight = 0.0;
    double targetHeight = 0.0;
    double y_angle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double distance = (targetHeight - cameraHeight) / Math.tan(cameraAngle + y_angle);
    SmartDashboard.putNumber("limeLigthX", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0));
    SmartDashboard.putNumber(("LimeLightY"), y_angle);
    return(distance);

  }
}
