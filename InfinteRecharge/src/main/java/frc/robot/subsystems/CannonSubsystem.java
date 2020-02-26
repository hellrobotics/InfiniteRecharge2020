/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class CannonSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public CANSparkMax FlyWheelMotor = new CANSparkMax(RobotMap.FLYWHEELMOTOR, MotorType.kBrushless);
  private CANEncoder wheelEncoder = FlyWheelMotor.getEncoder();
  private CANPIDController wheelPID = FlyWheelMotor.getPIDController();
  public Servo VissionServ = new Servo(RobotMap.VISSIONSERVO);
  
  //public Servo cameraServo1 = new Servo(RobotMap.SERVO1);

  private double integral_prior = 0;
  private double last_time = 0;


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
    wheelPID.setI(0);
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
  public double GetVissionServo(){
    return VissionServ.get();
  }

  public double getWheelSpeed(){
    return -wheelEncoder.getVelocity();
  }

  public double calculateWheelSpeed(double x) {
    return Math.max(0, Math.min(5700,(0.0119*Math.pow(x,4)-0.1862*Math.pow(x,3)+1.0849*Math.pow(x,2)-2.7357*x+3.3842)*5700));
  }

  public void TrackServo(double target) {
    if (target >= 0) {
      double error = 60.0 - target;
      double iteration_time = Timer.getFPGATimestamp() - last_time;
      double integral = Math.max(0.02, Math.min(-0.02, integral_prior + error * iteration_time));
      double kp = 0.00015*0.45;
      double ki = (1.2*kp)/0.65;
      VissionServ.set(Math.max(0.6, Math.min(0.8, VissionServ.get()+(error*kp + integral*ki))));
      integral_prior = integral;
      last_time = Timer.getFPGATimestamp();
    }
  }

}
