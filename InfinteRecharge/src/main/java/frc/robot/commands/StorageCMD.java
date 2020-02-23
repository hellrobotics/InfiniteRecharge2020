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


    //Start of PIDza
    if(oi.stick.getRawButton(1)){
      int SpeD = ssStore.getPizzaSpED();

      if(SpeD > 11) {
        Speed2 -= 0.1;
      }
      if (10 > SpeD) {
        Speed2 += 0.1;
      }

      SmartDashboard.putNumber("SpED", SpeD);
      SmartDashboard.putNumber("Speed PizzaWheel", Speed2*0.8);

      Speed2 = -1;
      ssStore.RunPizza(Speed2*0.5);
      ssStore.RunFeeding(-1);
    }
    else if(oi.stick.getPOV() == 90){
      ssStore.RunPizza(1);
      ssStore.RunFeeding(0.3);
    }
    else if(oi.stick.getPOV() == 270){
      ssStore.RunPizza(-1);
      ssStore.RunFeeding(-0.3);
    } /*else if (oi.stick.getPOV() == 0) {
      ssStore.RunPizza(-0.4);
      ssStore.RunFeeding(-0.2);
    } */else {
      ssStore.RunPizza(0);
      ssStore.RunFeeding(0);
    }
    //End of PIDza



    SmartDashboard.putBoolean("EndstopA", ssStore.getEndstopA());
    SmartDashboard.putBoolean("EndstopB", ssStore.getEndstopB());
    SmartDashboard.putBoolean("EndstopC", ssStore.getEndstopC());

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
