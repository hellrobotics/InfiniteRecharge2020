/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class IntakeSubsystem extends Subsystem {

  public Solenoid intakeSolenoid = new Solenoid(RobotMap.INTAKESOLENOID);
  public Solenoid helperSolenoid = new Solenoid(RobotMap.HELPERSOLENOID);
  public WPI_VictorSPX intakeMotor = new WPI_VictorSPX(RobotMap.INTAKE);
  public CANSparkMax topIntakeMotor = new CANSparkMax(RobotMap.INTAKETOP, MotorType.kBrushless);
  private CANPIDController wheelPID = topIntakeMotor.getPIDController();

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
  public void ConfigPID() {
    wheelPID.setP(1e-5);
    wheelPID.setI(0);
    wheelPID.setD(0);
    wheelPID.setIZone(0);
    wheelPID.setFF(0.00017);
    wheelPID.setOutputRange(-1,1);
  }
  public void RunIntakePID(double RPM) {
    wheelPID.setReference(-RPM, ControlType.kVelocity);
    
  }
  public void RunIntake(double power) {
    intakeMotor.set(power);
    //topIntakeMotor.set(power*0.3); 
  }

  public void RaiseIntake(boolean state) {
    intakeSolenoid.set(state);
    helperSolenoid.set(state);
  }
//Røva mi e meget brukbar

}
