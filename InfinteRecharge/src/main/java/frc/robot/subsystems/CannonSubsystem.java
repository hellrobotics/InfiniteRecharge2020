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

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  private WPI_TalonSRX TurretSpinner = new WPI_TalonSRX(RobotMap.TURRETSPINNER);
  public Servo VissionServ = new Servo(RobotMap.VISSIONSERVO);
  
  //public Servo cameraServo1 = new Servo(RobotMap.SERVO1);

  private double integral_prior = 0;
  private double last_time = 0;
  private double integral_prior2 = 0;
  private double last_time2 = 0;

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

  public void ConfigPID() {
    wheelPID.setP(1e-5);
    wheelPID.setI(6e-9);
    wheelPID.setD(0);
    wheelPID.setIZone(0);
    wheelPID.setFF(0.00017);
    wheelPID.setOutputRange(-1,0);
  }

  public void RunShootWheel(double power) {
    FlyWheelMotor.set(power);
  }

  public void RunShootWheelPID(double RPM) {
    wheelPID.setReference(-RPM, ControlType.kVelocity);
    
  }

  public void SetVissionServo(double pos){
    VissionServ.set(pos);
  }

  public void SetVissionServoSpeed(double speed){
    VissionServ.set(0.5+(speed/2));
    
  }

  public double GetVissionServo(){
    return VissionServ.get();
  }

  public double getWheelSpeed(){
    return -wheelEncoder.getVelocity();
  }

  public double calculateWheelSpeed(double x) {
    //return Math.max(0, Math.min(5700,(0.0119*Math.pow(x,4)-0.1862*Math.pow(x,3)+1.0849*Math.pow(x,2)-2.7357*x+3.3842)*5700));
    //return Math.max(0, Math.min(5800,(76.61*Math.pow(x,6)-1594.4*Math.pow(x,5)+13543.61*Math.pow(x, 4)-60002.49*Math.pow(x,3)+146105.17*Math.pow(x,2)-185161.87*x+99975.37)));
    return Math.max(0, Math.min(5800,(72.09*Math.pow(x,4)-1014.57*Math.pow(x,3)+5159.6*Math.pow(x,2)-10926.16*x+13202.19)));
  }

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
  public void SpinTurret(double speed){
    TurretSpinner.set(speed);
  }

  public void TrackTurret (double target) {
    if (target >= 0) {
      double error = (80.0 - target);
      double iteration_time = Timer.getFPGATimestamp() - last_time2;
      double integral = Math.max(0.1, Math.min(-0.1, integral_prior2 + error * iteration_time));
      //Kommenter ut *0.45 og endre til det dirrer rundt punktet, derreter ukomenter og gang (1.2*kp)/tiden et dirr tar
      double kp = 0.01;//*0.45;
      double ki = (1.2*kp)/0.2;
      integral_prior2 = integral;
      last_time2 = Timer.getFPGATimestamp();
      SpinTurret((error*kp + integral*ki*0));  
    } else {
      SpinTurret(0);
    }
  }
}
