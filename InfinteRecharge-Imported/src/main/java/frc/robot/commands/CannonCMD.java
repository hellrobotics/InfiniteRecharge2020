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

  public boolean isRunning = false;
  public boolean isTracking = false;
  public static boolean isFlipped = false;
  public boolean normalCamera = false;

  
  private double ServPos = 0.5;
  private double yCoord = -1;
  private double xCoord = -1;
  private double extraPower = 0;
  private double cannonPower = 5800;
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

    if(oi.stick.getRawButtonPressed(7)){
      normalCamera = !normalCamera;
    }

    //System.out.println(ssCannon.CalculateDistance(yCoord));
    //toggle shooting
    if (oi.stick.getRawButtonPressed(3)) {
      isRunning = !isRunning;

    }

    //Toggle tracking and only tracking
    if (oi.stick.getRawButtonPressed(5)) {
      isTracking = !isTracking;
    }

    if(oi.stick.getRawButtonPressed(2) || oi.figthStick.getRawButtonPressed(9)){
      if(isRunning != isTracking){
        isRunning = false;
        isTracking = false;
      }else{
        isRunning = !isRunning;
        isTracking = !isTracking;
      }
    }

    //Toggle camera position
    if(oi.stick.getRawButtonPressed(6) || oi.figthStick.getRawButtonPressed(3) ){
      isFlipped = !isFlipped;
    }
    //Set camera position
    ssCannon.setCameraPos(isFlipped);

    //Adjustable cannonPower
    //cannonPower = ((oi.stick.getThrottle()+1)/2*5800);
    
    //Auto cannonPower
    cannonPower = ssCannon.calculateWheelSpeed(ssCannon.CalculateDistance(yCoord));
    //System.out.println( ssCannon.calculateWheelSpeed(ssCannon.CalculateDistance(yCoord)));
 
    //Smartdashboard mass information output
    SmartDashboard.putNumber("Cannon power", cannonPower);
    SmartDashboard.putBoolean("Is running =", isRunning);
    SmartDashboard.putNumber("Cannon Speed", ssCannon.getWheelSpeed());
    SmartDashboard.putNumber("Exra RPM", extraPower);
    SmartDashboard.putNumber("Servo pos", ServPos);
    SmartDashboard.putNumber("Target X", xCoord);

    //Disable tracking and shooting while flipped
    if(!isFlipped){
      if(isTracking){
        ssCannon.setPipeline(0);
      }else{
      if(normalCamera){
        ssCannon.setPipeline(2);
      }else{
      ssCannon.setPipeline(0);
      }
    }
  //Shoot cannon
  if(oi.stick.getRawButton(1) || oi.figthStick.getRawButton(6)){
    ssCannon.RunShootWheelPID(cannonPower);
  } 

  
  //Shoot only launcher wheel
  else if(isRunning == true){
    ssCannon.RunShootWheelPID(cannonPower);
  } 
  
  else{
    ssCannon.RunShootWheel(0);
  }

    //Tracking and not shooting
 if(isTracking) {    
  ssCannon.TrackTurret(xCoord);
  System.out.println("Tracking: " + xCoord + ", " + yCoord);
} else {


  //manual spin of turret
  if(oi.figthStick.getPOV() == 90 || oi.stick.getRawButton(12)){
    ssCannon.SpinTurret(-0.5);
  } else if(oi.figthStick.getPOV() == 270 ||oi.stick.getRawButton(11)){
    ssCannon.SpinTurret(0.5);
  } else{
  ssCannon.SpinTurret(-0.2*oi.figthStick.getRawAxis(0));
}
}

  //Add power to launcher wheel
  if(oi.figthStick.getRawButtonPressed(7)){
    extraPower += 100;
  } 
  
  //Remove power from launcher wheel
  else if(oi.figthStick.getRawButtonPressed(8)){
    extraPower -= 100;
  }
  


  //End of stopping if flipped
} else{
  ssCannon.setPipeline(1);

}
//End of stopping if flipped
 
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
