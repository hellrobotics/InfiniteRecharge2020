/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
//beføl mæ
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSub;

public class AutoSetDrive extends CommandBase {
  /**
   * Creates a new AutoCMD.
   */
  private boolean isDone = false;
  private DriveSub ssDrive;
  private double speed = 0;
  public AutoSetDrive(double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    ssDrive = DriveSub.getInstance();
    addRequirements(ssDrive);
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //isDone = true;
    System.out.println("Starting drive");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("HER");
    ssDrive.Arcade(speed, 0);
    System.out.println("DRIVING");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ssDrive.Arcade(0, 0);
    System.out.println("Drive end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
