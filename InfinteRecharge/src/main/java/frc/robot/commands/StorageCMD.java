/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.StorageSub;

public class StorageCMD extends Command {
  /**
   * Creates a new StorageCMD.
   */
  private StorageSub ssStore;
  private OI oi;
  public StorageCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssStore = StorageSub.getInstance();
    requires(ssStore);
    oi = OI.getInstance();
  }

  private void requires(StorageSub ssStore2) {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(oi.controller.getRawButton(1)){
      ssStore.RunPizza(0.3);
    }
    else if(oi.controller.getRawButton(2)){
      ssStore.RunPizza(-0.3);
    }
    else if(oi.controller.getRawButton(3)){
      ssStore.RunPizza(1);
    }
    else if(oi.controller.getRawButton(4)){
      ssStore.RunPizza(-1);
    }
    else{
      ssStore.RunPizza(0);
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
