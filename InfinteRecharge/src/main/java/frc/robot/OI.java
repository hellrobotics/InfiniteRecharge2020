/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//Joystick
  public Joystick stick = new Joystick(0);

	//Controller
	public Joystick controller = new Joystick(1);
	//Gyro


    private static OI m_instance;
	public static synchronized OI getInstance() {
		if (m_instance == null){
			m_instance = new OI();
		}
		return m_instance;
		
	}
}
