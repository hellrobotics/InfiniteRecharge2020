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
import frc.robot.subsystems.ElevatorSub;

public class ElevatorCMD extends Command {
  /**
   * Creates a new ElevatorCMD.
   */
  private ElevatorSub ssElevate;
  private OI oi;
  private boolean isActivated = false;
  private double elevatorDirection = -1;
   
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

    if(oi.figthStick.getRawButtonPressed(6)) {
      elevatorDirection *= -1;
    }
      
    if(oi.figthStick.getRawAxis(3) > 0.1){
      ssElevate.RunElevator(0.8*elevatorDirection);
    } else {
      ssElevate.RunElevator(0);
    }
    
    if (elevatorDirection < 0) {
      ssElevate.setSkralleServo(0.25);
    } else {
      ssElevate.setSkralleServo(0.4);
    }


    ssElevate.activateElevator(isActivated);
    SmartDashboard.putBoolean("Elevator active", isActivated);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
