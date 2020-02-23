/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class DriveSub extends SubsystemBase {
  /**
   * Creates a new DriveSub.
   */
  public WPI_VictorSPX TopLeft = new WPI_VictorSPX(RobotMap.TOPLEFT);
  public WPI_VictorSPX BottomLeft = new WPI_VictorSPX(RobotMap.BOTTOMLEFT);
  SpeedControllerGroup leftMotors = new SpeedControllerGroup(TopLeft, BottomLeft);


  public WPI_VictorSPX TopRigth = new WPI_VictorSPX(RobotMap.TOPRIGTH);
  public WPI_VictorSPX BottomRigth = new WPI_VictorSPX(RobotMap.BOTTOMRIGTH);
  SpeedControllerGroup rigthMotors = new SpeedControllerGroup(TopRigth, BottomRigth);

  DifferentialDrive allDrive = new DifferentialDrive(leftMotors, rigthMotors);

  public DriveSub() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void Arcade (double moveValue, double rotateValue) {
    allDrive.arcadeDrive(moveValue, rotateValue);
  }

  public void TrackTarget (double target) {
    if (target >= 0) {
      double error = 160.0 - target;
      double pk = 1/300.0;
      Arcade(0, error*pk*-1);
      System.out.println("tracking, object found " + error);
    } else {
      System.out.println("tracking no target");
    }
  }

  private static DriveSub m_instance;
	public static synchronized DriveSub getInstance() {
		if (m_instance == null){
			m_instance = new DriveSub();
		}
		return m_instance;
  }
}
