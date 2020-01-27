/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Servo;


public class ServoSub extends SubsystemBase {

  /**
   * Creates a new Servo.
   */
  public Servo servo1 = new Servo(RobotMap.SERVO1);

  public ServoSub() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void ServoPos(double pos){
    servo1.set(pos);
  }

  //Idk fam
  private static ServoSub m_instance;
	public static synchronized ServoSub getInstance() {
		if (m_instance == null){
			m_instance = new ServoSub();
    }
    return m_instance;
  }

}