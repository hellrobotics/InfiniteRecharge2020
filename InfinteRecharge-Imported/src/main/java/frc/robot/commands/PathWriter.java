// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;



public class PathWriter extends Command {

  private OI oi;
  PrintWriter pw;

  public String filepath = "U/FRCLogPath/";
  private String filePathFull;

  private double driveDir = 0;
  
  public PathWriter(String filename) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    oi = OI.getInstance();
    filePathFull = filepath+filename;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Started path recording");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(oi.stick.getRawAxis(3) < 0 ){
      driveDir = -1;
    }
    else{
      driveDir = 1;
    }
    writeLineToCSV((oi.stick.getRawAxis(1)*(oi.stick.getRawAxis(3))) + ";" + (oi.stick.getRawAxis(0)*(oi.stick.getRawAxis(3))*driveDir*0.75) + ";");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return oi.stick.getRawButtonPressed(10);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("Stopping path recording");
    pw.close();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}

  public void writeLineToCSV (String content){
     pw.write(content+"\r\n");
     pw.flush();
  }

}

