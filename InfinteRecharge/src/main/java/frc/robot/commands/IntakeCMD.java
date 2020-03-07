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
  public double IntakeSensetivity = 0.85;
  public static double ABSspeed;
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
    ABSspeed = ((Math.abs(oi.stick.getRawAxis(0)) + Math.abs(oi.stick.getRawAxis(1)))* Math.abs(oi.stick.getRawAxis(3)));

    if(ABSspeed > IntakeSensetivity){
      IntakeActive = false;
    } if(oi.figthStick.getRawButtonPressed(5)){
       IntakeActive = !IntakeActive;
       IntakeSplit = false;

      } 
      if(oi.stick.getRawButtonPressed(3)){
        IntakeActive = !IntakeActive;
        IntakeSplit = false;
       }
       if(oi.figthStick.getRawButtonPressed(10)){
         IntakeSplit = !IntakeSplit;
       }

      if(Robot.SensorA == true){
        PIDspeed = 0;
      }
      else{
        PIDspeed = 2000;
      }
      SmartDashboard.putNumber("Abs speed", ABSspeed);
      //Motors run  
      if(IntakeActive == true && IntakeSplit == false){
      if(oi.stick.getPOV() == 0){
        ssIntake.RunIntake(-1);
        ssIntake.RunIntakePID(PIDspeed);

      } else if(oi.stick.getPOV() == 180){
        ssIntake.RunIntake(0.8);
        ssIntake.RunIntakePID(-PIDspeed);
      }else if(oi.figthStick.getRawButton(1)){
        ssIntake.RunIntake(0.8);
        ssIntake.RunIntakePID(-PIDspeed);
      }else if(oi.figthStick.getRawButton(2)){
        ssIntake.RunIntake(-1);
        ssIntake.RunIntakePID(PIDspeed);
      }
       else {
        ssIntake.RunIntake(0);
        ssIntake.RunIntakePID(0);

      } 

      

    }else if(IntakeActive == false && IntakeSplit == true){ 
      if(oi.figthStick.getRawButton(1)){
        ssIntake.RunIntakePID(-PIDspeed);
      } else if(oi.figthStick.getRawButton(2)){
        ssIntake.RunIntakePID(PIDspeed);
      }if(oi.stick.getPOV() == 0){
        ssIntake.RunIntakePID(PIDspeed);

      } else if(oi.stick.getPOV() == 180){
        ssIntake.RunIntakePID(-PIDspeed);
      }
    }else{
      ssIntake.RunIntake(0);
      ssIntake.RunIntakePID(0);
    }
    
    if(IntakeSplit == false){
      ssIntake.RaiseIntake(IntakeActive);
      ssIntake.HelperIntake(IntakeActive);
      } else if(IntakeSplit == true){
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
