/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.ElevatorSub;

public class ElevatorCMD extends Command {
  /**
   * Creates a new ElevatorCMD.
   */
  private ElevatorSub ssElevate;
  private OI oi;
  private boolean isActivated = false;
   
  public ElevatorCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssElevate = ElevatorSub.getInstance();
    addRequirements(ssElevate);
    oi = OI.getInstance();
  }

  private void addRequirements(ElevatorSub ssElevate2) {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(oi.figthStick.getRawButtonPressed(3)){
      isActivated = !isActivated;
    }
    if(oi.figthStick.getRawButton(6)){
      ssElevate.setSkralleServo(0);
    }
    else if(oi.figthStick.getRawAxis(3) > 0.1){
      ssElevate.setSkralleServo((0.2));
    }

    if(oi.figthStick.getRawButton(6) && ssElevate.getSkralleServo() <= 0){
      ssElevate.RunElevator(0.5);
    } else if(oi.figthStick.getRawAxis(3) > 0.1 && ssElevate.getSkralleServo() >= 0.2){
      ssElevate.RunElevator(-0.5);
    } else{
      ssElevate.RunElevator(0);
    }
    ssElevate.activateElevator(isActivated);
  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
