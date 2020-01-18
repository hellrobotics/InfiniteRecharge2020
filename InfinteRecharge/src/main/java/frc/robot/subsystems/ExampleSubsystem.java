/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * An example subsystem. You can replace with me with your own subsystem.
 */
public class ExampleSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public WPI_VictorSPX motor1 = new WPI_VictorSPX(RobotMap.MOTOR1);
  public WPI_VictorSPX motor2 = new WPI_VictorSPX(RobotMap.MOTOR2);
  public WPI_VictorSPX motor3 = new WPI_VictorSPX(RobotMap.MOTOR3);
  public WPI_VictorSPX motor4 = new WPI_VictorSPX(RobotMap.MOTOR4);




  @Override
  protected void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

 
  

  
  @Override
  public Command getCurrentCommand() {
    return super.getCurrentCommand();
  }
  
 
  
  public void RunMotor1(double speed) {
    motor1.set(speed);
  }
  public void RunMotor2(double speed) {
    motor2.set(speed);
  }
  public void RunMotor3(double speed) {
    motor3.set(speed);
  }
  public void RunMotor4(double speed) {
    motor4.set(speed);
  }
/*

  */
  private static ExampleSubsystem m_instance;
	public static synchronized ExampleSubsystem getInstance() {
		if (m_instance == null){
			m_instance = new ExampleSubsystem();
		}
		return m_instance;
  }
}
