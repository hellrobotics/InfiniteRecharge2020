/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.subsystems.EncoderSub;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.pneumaticSub;

/**
 * An example command. You can replace me with your own command.
 */
public class ExampleCommand extends Command {
  private final OI oi;
  private final ExampleSubsystem ssRun;
  private final EncoderSub ssGrab;
  private final pneumaticSub ssPneu;

  public ExampleCommand() {
    // Use requires() here to declare subsystem dependencies
    ssRun = ExampleSubsystem.getInstance();
    requires(ssRun);
    ssGrab = EncoderSub.getInstance();
    requires(ssGrab);
    ssPneu = pneumaticSub.getInstance();
    requires(ssPneu);
    oi = OI.getInstance();

  }


  private void requires(pneumaticSub ssPneu2) {
  }

  private void requires(EncoderSub ssGrab2) {
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int refference = (int) (oi.stick.getRawAxis(3)*1000);
    int pos = ssGrab.getEncoderPos1();
    if(!ssGrab.getEndstop1()){
    pos = ssGrab.getEncoderPos1();

    double error = refference - pos;
    double SPeD = error * 0.05;
    SmartDashboard.putNumber("grabPos", pos);
    SmartDashboard.putNumber("SPeD", SPeD);
    ssRun.RunMotor2((SPeD) * -1);
    
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  } else{
    double error = 0 - pos;
    double SPeD = error * 0.05;
    SmartDashboard.putNumber("grabPos", pos);
    SmartDashboard.putNumber("SPeD", SPeD);
  }
  
  
  
  
    if (oi.stick.getRawButton(1)) {
      ssRun.RunMotor1(1);
    } else if (oi.stick.getRawButton(2)) {
      ssRun.RunMotor1(-1);
    } else {
      ssRun.RunMotor1(0);
    }

    if (oi.stick.getRawButton(3)) {
      ssRun.RunMotor2(1);
    } 
    else if (oi.stick.getRawButton(4)){
    ssRun.RunMotor2(-1);

      }   else{
      ssRun.RunMotor2(0);
    }

    if(oi.stick.getRawButton(8)){
      ssPneu.Solenoid0(true);
    } else{
      ssPneu.Solenoid0(false);
    }
    if(oi.stick.getRawButton(7)){
      ssPneu.Solenoid1(true);
    } else{
      ssPneu.Solenoid1(false);
    }
    if(oi.stick.getRawButton(10)){
      ssPneu.Solenoid2(true);
    } else{
      ssPneu.Solenoid2(false);
    }
    if(oi.stick.getRawButton(9)){
      ssPneu.Solenoid3(true);
    } else{
      ssPneu.Solenoid3(false);
    }
    if(oi.stick.getRawButton(12)){
      ssPneu.Solenoid4(true);
    } else{
      ssPneu.Solenoid4(false);
    }
    if(oi.stick.getRawButton(11)){
      ssPneu.Solenoid5(true);
    } else{
      ssPneu.Solenoid5(false);
    }
 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
