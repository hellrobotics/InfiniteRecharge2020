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
  public boolean IntakeActive = false;


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
    //Togle intake code
      if(oi.stick.getRawButtonPressed(3)){
        if(IntakeActive == true){
          IntakeActive = false;
        }else{
          IntakeActive = true;
        }
      }

      //Pneumatic change
      ssIntake.RaiseIntake(IntakeActive);

      //Motors run  
      if(IntakeActive == true){
      ssIntake.RunIntake(oi.controller.getRawAxis(1));
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
