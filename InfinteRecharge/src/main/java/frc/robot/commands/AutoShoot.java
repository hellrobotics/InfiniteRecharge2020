/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.CannonSubsystem;
import frc.robot.subsystems.StorageSub;

public class AutoShoot extends CommandBase {

  private CannonSubsystem ssCannon;
  private StorageSub ssStore;
  private double xCoord = -1;
  private double yCoord = -1;

  /**
   * Creates a new AutoShoot.
   */
  public AutoShoot() {
    ssCannon = CannonSubsystem.getInstance();
    ssStore = StorageSub.getInstance();
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Shooting start");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    xCoord = Robot.centerX;
    yCoord = Robot.centerY;
    ssCannon.TrackTurret(xCoord);
    System.out.println("AIMING");
    if (xCoord < 80+5 && xCoord > 80-5) {
      double distance = ssCannon.CalculateDistance(yCoord);
      double shotSpeed = ssCannon.calculateWheelSpeed(distance);
      ssCannon.RunShootWheelPID(shotSpeed+200);
      System.out.println("SPINNING UP");
      if (ssCannon.getWheelSpeed() < shotSpeed+50 && ssCannon.getWheelSpeed() > shotSpeed-50) {
        ssStore.RunPizza(-1);
        ssStore.RunFeeding(-1);
        System.out.println("SHOOTING");
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ssStore.RunPizza(0);
    ssStore.RunFeeding(0);
    ssCannon.SpinTurret(0);
    ssCannon.RunShootWheel(0);
    System.out.println("Auto end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
