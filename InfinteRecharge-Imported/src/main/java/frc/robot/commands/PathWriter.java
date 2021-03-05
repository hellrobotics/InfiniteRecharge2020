// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import java.io.FileWriter;
import java.io.PrintWriter;



public class PathWriter extends Command {

  private OI oi;
  private String filename;
  PrintWriter pw;

  public String filepath = "U/FRCLogPath/";
  
  public PathWriter(String filename_) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    oi = OI.getInstance();
    filename = filename_;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(File.Exists(filepath))
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {}

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
     pw.write(content+"");
     pw.flush();
  }

}

