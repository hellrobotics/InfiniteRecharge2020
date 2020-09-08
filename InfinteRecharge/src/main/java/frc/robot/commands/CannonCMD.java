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
import frc.robot.subsystems.CannonSubsystem;

public class CannonCMD extends Command {

  private CannonSubsystem ssCannon;
  private OI oi;

  public boolean isRunning2 = false;
  public boolean isShooting = false;
  
  private double ServPos = 0.5;
  private double yCoord = -1;
  private double xCoord = -1;
  private double extraPower = 0;
  /**
   * Creates a new CannonCMD.
   */
  public CannonCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssCannon = CannonSubsystem.getInstance();
    //requires(ssCannon);
    oi = OI.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("CAnnoncmd");
    ssCannon.ConfigPID();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //Declare local X, Y cordinates for ease of use
    yCoord = Robot.centerY;
    xCoord = Robot.centerX;

    //toggle if shooting is running
    if (oi.stick.getRawButtonPressed(2)) {
      isRunning2 = !isRunning2;
    }

    //Toggle tracking and only tracking
    if (oi.stick.getRawButtonPressed(9)) {
      Robot.isTracking = !Robot.isTracking;
    }


    //Adjustable cannonPower
    double cannonPower = (((oi.stick.getThrottle()+1)/2)*(-0.3)-0.7)*-5800;

    //Smartdashboard mass information output
    SmartDashboard.putNumber("Cannon power", cannonPower);
    SmartDashboard.putBoolean("Is running =", isRunning2);
    SmartDashboard.putNumber("Cannon Speed", ssCannon.getWheelSpeed());
    SmartDashboard.putNumber("Exra RPM", extraPower);
    SmartDashboard.putNumber("Servo pos", ServPos);
    SmartDashboard.putNumber("Target X", xCoord);



  //Shoot cannon
  if(oi.stick.getRawButton(1)){
    ssCannon.RunShootWheelPID(cannonPower);
    isShooting = true;
  } 
  
  //Shoot only launcher wheel
  else if(isRunning2 == true){
    ssCannon.RunShootWheelPID(cannonPower);
    isShooting = false;
  } 
  
  else{
    ssCannon.RunShootWheel(0);
    isShooting = false;
  }


  //Add power to launcher wheel
  if(oi.figthStick.getRawButtonPressed(7)){
    extraPower += 100;
  } 
  
  //Remove power from launcher wheel
  else if(oi.figthStick.getRawButtonPressed(8)){
    extraPower -= 100;
  }
  

  //Set extra power to 5000
  else if(oi.figthStick.getRawButtonPressed(9)){
    extraPower = 5000;
  }
  
  //Tracking
  if(isRunning2 && !isShooting) {    
    ssCannon.TrackTurret(xCoord);
    System.out.println("Tracking: " + xCoord + ", " + yCoord);
  } 
  
  //
  else {

    //manual spin of turret
    if(oi.figthStick.getPOV() == 90){
      ssCannon.SpinTurret(-0.2);
    }
    else if(oi.figthStick.getPOV() == 270){
      ssCannon.SpinTurret(0.2);
    }
    else{
    ssCannon.SpinTurret(-0.2*oi.figthStick.getRawAxis(0));
  }
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
