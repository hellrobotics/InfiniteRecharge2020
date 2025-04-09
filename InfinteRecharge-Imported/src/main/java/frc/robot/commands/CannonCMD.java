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
  private boolean camToggleHold = false;
  public boolean normalCamera = true;

  
  private double ServPos = 0.5;
  private double yCoord = -1;
  private double xCoord = -1;
  private double extraPower = 0;
  private double cannonPower = 5800;
  private double defCannonPower = 5800;
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
    SmartDashboard.putNumber("SET Cannon Power", defCannonPower);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //Declare local X, Y cordinates for ease of use
    yCoord = Robot.centerY;
    xCoord = Robot.centerX;

    ssCannon.setCameraPos(isFlipped);

    //Shoot cannon
    if(oi.stick.getRawButton(1)){
      ssCannon.RunShootWheelPID(cannonPower);
    } else {
      ssCannon.RunShootWheel(0);
    }

    //Toggle tracking and only tracking
    if (oi.stick.getRawButtonPressed(2)) {
      isTracking = !isTracking;
    }

    if(isTracking){
      //Set visioncam, Auto cannonPower and spin turret
      isFlipped = false;
      normalCamera = false;
      cannonPower = ssCannon.calculateWheelSpeed(ssCannon.CalculateDistance(yCoord));
      ssCannon.TrackTurret(xCoord);
      System.out.println("Tracking: " + xCoord + ", " + yCoord);
    } else {
      //Get power from smartdashboard
      cannonPower = SmartDashboard.getNumber("SET Cannon Power", defCannonPower);
      //Adjustable cannonPower
      //cannonPower = ((oi.stick.getThrottle()+1)/2*5800);
    }
  
    //Toggle camera position
    if(oi.stick.getRawButtonPressed(4)){
      isFlipped = !isFlipped;
      if(isFlipped){
        isTracking = false;
      }
    }
    //Toggle non-tracking camera and disable tracking
    if(oi.stick.getRawButtonPressed(3)){
      if(isTracking){
        isTracking = false;
        normalCamera = true;
      } else {
        normalCamera = !normalCamera;
      }
    }
    //Set camera type
    if(normalCamera){
      if(isFlipped){
        ssCannon.setPipeline(3);
      } else {
        ssCannon.setPipeline(2);
      } 
    } else {
      ssCannon.setPipeline(0);
    }

    //Smartdashboard mass information output
    SmartDashboard.putNumber("Cannon power", cannonPower);
    SmartDashboard.putNumber("Cannon Speed", ssCannon.getWheelSpeed());
    SmartDashboard.putNumber("Target X", xCoord);

    //manual spin of turret
    if(oi.stick.getPOV() == 90){
      isTracking = false;
      ssCannon.SpinTurret(-0.5);
    } else if(oi.stick.getPOV() == 270){
      isTracking = false;
      ssCannon.SpinTurret(0.5);
    } else if(!isTracking){
      ssCannon.SpinTurret(0);
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
