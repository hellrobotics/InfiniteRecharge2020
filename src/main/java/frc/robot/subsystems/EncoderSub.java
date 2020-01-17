/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class EncoderSub extends SubsystemBase {
  /**
   * Creates a new EncoderSub
   * .
   */

  public DigitalInput endStop1 = new DigitalInput(RobotMap.ENDSTOP1);
  public Encoder encoder1 = new Encoder(RobotMap.ENCODER1, RobotMap.ENCODER2);


  public EncoderSub() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public int getEncoderPos1() {
    return encoder1.get();
  }
  
  public boolean getEndstop1() {
    return endStop1.get();
  }
  private static EncoderSub m_instance;
	public static synchronized EncoderSub getInstance() {
		if (m_instance == null){
			m_instance = new EncoderSub();
		}
		return m_instance;
  }

}
