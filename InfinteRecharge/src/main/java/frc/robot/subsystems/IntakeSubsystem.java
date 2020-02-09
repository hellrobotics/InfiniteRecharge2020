/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class IntakeSubsystem extends Subsystem {

  public Solenoid intakeSolenoid = new Solenoid(RobotMap.SOLENOID0);
  public VictorSPX intakeMotor = new VictorSPX(RobotMap.INTAKEMOTOR);
  public boolean intakeRaised = true;

  private static IntakeSubsystem m_instance;
	public static synchronized IntakeSubsystem getInstance() {
		if (m_instance == null){
			m_instance = new IntakeSubsystem();
    }
    return m_instance;
  }
  /**
   * Creates a new IntakeSubsystem.
   */
  public IntakeSubsystem() {

  }

  

  @Override
  public void initDefaultCommand() {
    
  }

  public void RunIntake(double power) {
    intakeMotor.set(ControlMode.PercentOutput, power);
  }

  public void RaiseIntake(boolean state) {
    intakeSolenoid.set(state);
    intakeRaised = state;
  }

  public void ToggleIntake() {
    RaiseIntake(!intakeRaised);
  }
}
