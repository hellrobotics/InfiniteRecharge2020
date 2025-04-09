/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.StorageSub;

public class StorageCMD extends Command {
  /**
   * Creates a new StorageCMD.
   */
  private final StorageSub ssStore;
  private final OI oi;
  public StorageCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssStore = StorageSub.getInstance();
    requires(ssStore);
    oi = OI.getInstance();
  }

  private void requires(final StorageSub ssStore2) {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //Manual control over Pizza and feeding motors
    if(oi.stick.getPOV() == 0) {
      ssStore.RunPizza(-0.5);
      ssStore.RunFeeding(-0.3);
    } 
    else if(oi.stick.getPOV() == 180) {
      ssStore.RunPizza(0.5);
      ssStore.RunFeeding(0.3);
    }
    else if(oi.stick.getRawButton(1) && Robot.readyToFire){
      ssStore.RunPizza(-0.5);
      ssStore.RunFeeding(-0.3);
    }
    else {
      ssStore.RunPizza(0);
      ssStore.RunFeeding(0);
    }
  }
  

  @Override
  protected void end() {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
