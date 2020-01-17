/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class pneumaticSub extends SubsystemBase {
  /**
   * Creates a new pneumaticSub.
   * 
   */
  public Solenoid solenoid0 = new Solenoid(RobotMap.SOLENOID0);
  public Solenoid solenoid1 = new Solenoid(RobotMap.SOLENOID1);
  public Solenoid solenoid2 = new Solenoid(RobotMap.SOLENOID2);
  public Solenoid solenoid3 = new Solenoid(RobotMap.SOLENOID3);
  public Solenoid solenoid4 = new Solenoid(RobotMap.SOLENOID4);
  public Solenoid solenoid5 = new Solenoid(RobotMap.SOLENOID5);
 
  public pneumaticSub() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void Solenoid0(boolean extend){
    solenoid0.set(extend);
  }
  public void Solenoid1(boolean extend){
    solenoid1.set(extend);
  }
  public void Solenoid2(boolean extend){
    solenoid2.set(extend);
  }
  public void Solenoid3(boolean extend){
    solenoid3.set(extend);
  }
  public void Solenoid4(boolean extend){
    solenoid4.set(extend);
  }
  public void Solenoid5(boolean extend){
    solenoid5.set(extend);
  }
  private static pneumaticSub m_instance;
	public static synchronized pneumaticSub getInstance() {
		if (m_instance == null){
			m_instance = new pneumaticSub();
    }
    return m_instance;
  }
}
  
