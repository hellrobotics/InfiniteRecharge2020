/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.subsystems.DriveSub;

public class DriveCMD extends Command {
  /**
   * Creates a new DriveCMD.
   */

  public String filepath = "U/FRCLogPath/";
  PathWriter pw;
  boolean isRecording = false;
  public String filename = "tmpName";

  boolean isPlaying = false;
  int currentPlayLine = 0;
  public String filenamePlay = "tmpName";
  public List<String> fileLines;
  public ArrayList<Vector2d> inputLines;
  

  private DriveSub ssDrive;
  private OI oi;
  private double driveDir;

  

  public DriveCMD() {
    // Use addRequirements() here to declare subsystem dependencies.
    ssDrive = DriveSub.getInstance();
    requires(ssDrive);
    oi = OI.getInstance();
  }
  
  private void requires(DriveSub ssDrive2) {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //SmartDashboard.putString("Path name", filename);
    //SmartDashboard.putString("Playback path name", filenamePlay);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //xCoord = Robot.centerX;

    //make sure turning is always the correct direction.
    if(oi.stick.getRawAxis(3) < 0 ){
      driveDir = -1;
    }
    else{
      driveDir = 1;
    }

    /* OLD Autonomous route replay
    if(oi.stick.getRawButtonPressed(9) && !isRecording){
      if(!isPlaying){
        filenamePlay = SmartDashboard.getString("Playback path name", filenamePlay);
        ReadCSV(filenamePlay);
      } else {
        isPlaying = false;
      }
    }*/
    /*
    if(isPlaying){
      ssDrive.Arcade(inputLines.get(currentPlayLine).x, inputLines.get(currentPlayLine).y);
      currentPlayLine++;
      if(currentPlayLine > inputLines.size()){
        isPlaying = false;
      }
    } else {*/

      //standard drivetrain code with adjustable speed
      ssDrive.Arcade(oi.stick.getRawAxis(1)*(oi.stick.getRawAxis(3)), oi.stick.getRawAxis(0)*(oi.stick.getRawAxis(3))*driveDir*0.75)  ;
 
    //}

    
    /* OLD Autonomous route recording
    if(oi.stick.getRawButtonPressed(10) && !isRecording){
      filename = SmartDashboard.getString("Path name", filename);
      pw = new PathWriter(filename+".csv");
      pw.start();
    }
    if(pw != null && pw.isFinished()){
      isRecording = false;
    }*/
 
  }

  // Called once the command ends or is interrupted.
  @Override
  protected void end() {
    isPlaying = false;
    isRecording = false;
  }
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void ReadCSV (String filename_) {
    String fullPath = filepath + filename_ + ".csv";
    Path csvPath = Paths.get(fullPath);
    fileLines = null;
    inputLines = null;
    
    try {
      fileLines = Files.readAllLines(csvPath);
    } catch(NoSuchFileException e) {
      System.out.println("Oops!!! Trajectory file not found!!!!");
      isPlaying = false;
    } catch(IOException e) {
      isPlaying = false;
    }


    if (fileLines != null) {
      inputLines= new ArrayList<Vector2d>();
      for (int i = 0; i < fileLines.size(); i++) {
        String[] values = fileLines.get(i).split(";");
        inputLines.add(new Vector2d(Double.parseDouble(values[0]), Double.parseDouble(values[1])));
      }
    }

    if(inputLines != null){
      currentPlayLine = 0;
      isPlaying = true;
    }

  }

}
