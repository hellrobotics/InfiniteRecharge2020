/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class CannonSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public CANSparkMax FlyWheelMotor = new CANSparkMax(RobotMap.FLYWHEELMOTOR, MotorType.kBrushless);
  public Servo VissionServ = new Servo(RobotMap.VISSIONSERVO);
  private CANEncoder wheelEncoder = FlyWheelMotor.getEncoder();
  //public Servo cameraServo1 = new Servo(RobotMap.SERVO1);

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

  public void RunShootWheel(double power) {
    FlyWheelMotor.set(power);
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
    return (0.0119*Math.pow(x,4)-0.1862*Math.pow(x,3)+1.0849*Math.pow(x,2)-2.7357*x+3.3842);
  }

  public void TrackServo(double target) {
    if (target >= 0) {
      double error = 160.0 - target;
      double pk = 1/300.0;
      SetVissionServo(GetVissionServo() + error*pk);
    } else {
      
    }
  }

}
