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
import frc.robot.subsystems.StorageSub;

public class StorageCMD extends Command {
  /**
   * Creates a new StorageCMD.
   */
  private final StorageSub ssStore;
  private final OI oi;
  private int LastPos = 0;
  private double Speed2 = 0.0;

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

    //Start of pizza PID
    int pos = ssStore.getPizzaPos();
    if(oi.stick.getRawButton(1)){
    pos = ssStore.getPizzaPos();
    if(pos - LastPos > 13){
      Speed2 -= 0.1;
    }
   if(12 > pos - LastPos ){
      Speed2 += 0.1;
    }
    
    SmartDashboard.putNumber("grabPos", pos);
    SmartDashboard.putNumber("Speed PizzaWheel", Speed2*0.8);
    SmartDashboard.putNumber("Speed difference pizza", pos - LastPos);

    LastPos = pos;

  }
  //End of PID pizza wheel

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
