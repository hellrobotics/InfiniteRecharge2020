/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.CannonSubsystem;

public class CannonCMD extends Command {

  private CannonSubsystem ssCannon;
  private OI oi;

  /**
   * Creates a new CannonCMD.
   */
  public CannonCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssCannon = CannonSubsystem.getInstance();
    requires(ssCannon);
    oi = OI.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("CAnnoncmd");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (oi.stick != null) {
      if (oi.stick.getPOV() == 90) {
        ssCannon.MoveTurret(0.5);
      } else if (oi.stick.getPOV() == 270) {
        ssCannon.MoveTurret(-0.5);
      } else {
        ssCannon.MoveTurret(0);
      }

      if (oi.stick.getRawButton(0)) {
        ssCannon.RunShotWheel(0.5);
      } else {
        ssCannon.RunShotWheel(0);
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
