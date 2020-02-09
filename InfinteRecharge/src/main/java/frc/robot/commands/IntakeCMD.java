/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCMD extends Command {
  /**
   * Creates a new IntakeCMD.
   */
  private IntakeSubsystem ssIntake;
  private OI oi;


  public IntakeCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssIntake = IntakeSubsystem.getInstance();
    requires(ssIntake);
    oi = OI.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Intakecmd");
    if(oi.stick != null) {
      if (oi.stick.getRawButton(4)) {
        ssIntake.RunIntake(0.5);
      } else if (oi.stick.getRawButton(2)) {
        ssIntake.RunIntake(-0.5);
      } else {
        ssIntake.RunIntake(0);
      }

      if (oi.stick.getRawButtonPressed(5)) {
        ssIntake.RaiseIntake(true);
      } else if (oi.stick.getRawButtonPressed(3)) {
        ssIntake.RaiseIntake(false);
      } 
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  protected void end() {
  }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
