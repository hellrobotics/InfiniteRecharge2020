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
  private double servoAngle = 0;
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
    SmartDashboard.putNumber("Exra RPM",0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    yCoord = Robot.centerY;
    xCoord = Robot.centerX;
    SmartDashboard.putNumber("Target X", xCoord);
    double distance = ssCannon.CalculateDistance(yCoord);

    if (oi.stick.getRawButtonPressed(2)) {
      isRunning2 = !isRunning2;
    }
    //if (oi.stick.getRawButtonPressed(9)) {
    //  Robot.isTracking = !Robot.isTracking;
    //}

    double cannonPower = (((oi.stick.getThrottle()+1)/2)*(-0.3)-0.7)*-5800;
    SmartDashboard.putNumber("Cannon power", cannonPower);
    SmartDashboard.putBoolean("Is running =", isRunning2);
    SmartDashboard.putNumber("Cannon Speed", ssCannon.getWheelSpeed());
    SmartDashboard.putNumber("Exra RPM", extraPower);
  if(oi.stick.getRawButton(1)){
    ssCannon.RunShootWheelPID(ssCannon.calculateWheelSpeed(distance)+extraPower);
    //ssCannon.RunShootWheelPID(cannonPower);
    isShooting = true;
  } else if(isRunning2 == true){
    ssCannon.RunShootWheelPID(ssCannon.calculateWheelSpeed(distance)+extraPower);
    //ssCannon.RunShootWheelPID(cannonPower);
    isShooting = false;
  } else{
    ssCannon.RunShootWheel(0);
    isShooting = false;
  }
  if(oi.figthStick.getRawButtonPressed(7)){
    extraPower += 100;
  } else if(oi.figthStick.getRawButtonPressed(8)){
    extraPower -= 100;
  }
  else if(oi.figthStick.getRawButtonPressed(9)){
    extraPower = 5000;
  }
  if(isRunning2 && xCoord != -1 && !isShooting) {
    //ssCannon.TrackServo(yCoord);
    
    ssCannon.TrackTurret(xCoord);
    SmartDashboard.putNumber("Servo pos", ServPos);
    System.out.println("Tracking: " + xCoord + ", " + yCoord);
  } else {
    if(oi.figthStick.getPOV() == 0){
      ServPos += 0.0002;
      //ssCannon.SetVissionServoSpeed(0.5);
    } else if(oi.figthStick.getPOV() == 180){
      ServPos -= 0.0002;
      //ssCannon.SetVissionServoSpeed(-0.5);
    } else {
      //ssCannon.SetVissionServoSpeed(0);
      ServPos -= 0.0002*oi.figthStick.getRawAxis(1);
    }
    //ssCannon.SetVissionServo(ServPos);
    //ServPos = Math.max(0.39, Math.min(0.7, ssCannon.GetVissionServo()));
    //SmartDashboard.putNumber("Servo pos", ServPos); 
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
