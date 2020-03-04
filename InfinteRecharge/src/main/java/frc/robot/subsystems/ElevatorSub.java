/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class ElevatorSub extends SubsystemBase {
  /**
   * Creates a new ElevatorSub.
   */
  public WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(RobotMap.ELEVATORMOTOR);
   public Solenoid elevatorLock = new Solenoid(RobotMap.ELEVATORLOCK);
   public Servo skralleServo = new Servo(RobotMap.SKRALLESERVO);
   //public Solenoid elevatorActiator = new Solenoid(RobotMap.ELEVATORPUSHER);
   
  public ElevatorSub() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void RunElevator(double speed){
    elevatorMotor.set(speed);
  }
  public void activateElevator(boolean state){
    //elevatorActiator.set(state);
    elevatorLock.set(state);
  }
  public void setSkralleServo(double pos){
    skralleServo.set(pos);
  }

  public double getSkralleServo() {
    return skralleServo.get();
  }

  private static ElevatorSub m_instance;
	public static synchronized ElevatorSub getInstance() {
		if (m_instance == null){
			m_instance = new ElevatorSub();
		}
		return m_instance;
  }
}
