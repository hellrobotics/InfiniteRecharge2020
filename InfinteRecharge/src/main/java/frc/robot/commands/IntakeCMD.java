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
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCMD extends Command {
  /**
   * Creates a new IntakeCMD.
   */
  private IntakeSubsystem ssIntake;
  private OI oi;
  public boolean IntakeActive = false;
  public int PIDspeed = 2000;
  public boolean IntakeSplit = false;
  public IntakeCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssIntake = IntakeSubsystem.getInstance();
    requires(ssIntake);
    oi = OI.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ssIntake.ConfigPID();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  //Togle intake code

    //Toggle intake secound driver
     if(oi.figthStick.getRawButtonPressed(5)){
       IntakeActive = !IntakeActive;
       IntakeSplit = false;

      } 

      //Toggle intake first driver
      if(oi.stick.getRawButtonPressed(3)){
        IntakeActive = !IntakeActive;
        IntakeSplit = false;
       }

       //split intake secound driver
       if(oi.figthStick.getRawButtonPressed(4)){
         IntakeSplit = !IntakeSplit;
         IntakeActive = false;
       }

       //Time of fligth sensor automation.
      if(Robot.SensorA == true){
        PIDspeed = 0;
      }
      else{
        PIDspeed = 2000;
      }

      //Cheack if split or not
      if(IntakeActive == true && IntakeSplit == false){

      //Run motors as non split
      if(oi.stick.getPOV() == 0){
        ssIntake.RunIntake(-1);
        ssIntake.RunIntakePID(PIDspeed);

      } 
      
      else if(oi.stick.getPOV() == 180){
        ssIntake.RunIntake(0.8);
        ssIntake.RunIntakePID(-PIDspeed);
      }
      
      else if(oi.figthStick.getRawButton(1)){
        ssIntake.RunIntake(0.8);
        ssIntake.RunIntakePID(-PIDspeed);
      }
      
      else if(oi.figthStick.getRawButton(2)){
        ssIntake.RunIntake(-1);
        ssIntake.RunIntakePID(PIDspeed);
      }
      
       else {
        ssIntake.RunIntake(0);
        ssIntake.RunIntakePID(0);
      } 

      

    }
    

    //Cheack if in Split mode
    else if(IntakeActive == false && IntakeSplit == true){ 

      //Run motors in Split mode
      if(oi.figthStick.getRawButton(1)){
        ssIntake.RunIntakePID(-PIDspeed);
      } 
      
      else if(oi.figthStick.getRawButton(2)){
        ssIntake.RunIntakePID(PIDspeed);
      }
      
      if(oi.stick.getPOV() == 0){
        ssIntake.RunIntakePID(PIDspeed);

      } 
      
      else if(oi.stick.getPOV() == 180){
        ssIntake.RunIntakePID(-PIDspeed);
      }
      
      else{
      ssIntake.RunIntake(0);
      ssIntake.RunIntakePID(0);
    }
  }
    
    //Put the intake in NON split position
    if(IntakeSplit == false){
      ssIntake.RaiseIntake(IntakeActive);
      ssIntake.HelperIntake(IntakeActive);
      } 
      
      //Put the intake in SPLIT position
      else if(IntakeSplit == true){
        ssIntake.RaiseIntake(IntakeActive);
        ssIntake.HelperIntake(!IntakeActive);
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
