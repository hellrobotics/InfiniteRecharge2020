package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.EncoderSub;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ServoSub;
import frc.robot.subsystems.pneumaticSub;

public class ExampleCommand extends Command {

  // Add HID device and Subsystem grabs
  private final OI oi;
  private final ExampleSubsystem ssRun;
  private final EncoderSub ssGrab;
  private final ServoSub ssServ;
  private final pneumaticSub ssPneu;
  private int LastPos = 0;
  private double Speed2 = 0.0;

  public ExampleCommand() {
    //Finalise adding of subsystem grabs and HID
    ssRun = ExampleSubsystem.getInstance();
    requires(ssRun);
    ssGrab = EncoderSub.getInstance();
    requires(ssGrab);
    ssPneu = pneumaticSub.getInstance();
    requires(ssPneu);
    ssServ = ServoSub.getInstance();
    requires(ssServ);
    oi = OI.getInstance();

  }

  private void requires(ServoSub ssServ2) {
  }

  // Add requires() for ssPneu and ssGrab
  private void requires(final pneumaticSub ssPneu2) {
  }

  private void requires(final EncoderSub ssGrab2) {
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {


    
    //Encoder testing
    int pos = ssGrab.getEncoderPos1();
    if(oi.stick.getRawButton(1)){
    pos = ssGrab.getEncoderPos1();
    if(pos - LastPos > 13){
      Speed2 -= 0.1;
    }
   if(12 > pos - LastPos ){
      Speed2 += 0.1;
    }

   
    
    //More encoder testing
    /*
    final double error = 0 - pos;
    final double Speed2 = error * 0.05;
*/
     
    SmartDashboard.putNumber("grabPos", pos);
    SmartDashboard.putNumber("Speed2", Speed2*0.8);
    SmartDashboard.putNumber("Speed difference ", pos - LastPos);

    LastPos = pos;

  }
  
  
  
    //Vision control
    if (oi.controller.getRawButton(1)) {
   double error = 0 -  Robot.visionError;
   double speed = error * 1;
        System.out.println(speed);
        ssRun.RunMotor2(error);
    }
    else{
    ssRun.RunMotor2(oi.controller.getRawAxis(0)/(-3));

    }
    if(oi.controller.getRawButton(2)){
      ssServ.ServoPos(1);
    }
    else if(oi.controller.getRawButton(3)){
      ssServ.ServoPos(0);
    }
    
    //Run motors
   if(oi.stick.getRawButton(2)){
     ssRun.RunMotor1(Speed2*0.8);
   }
   else{
     ssRun.RunMotor1(0);
   }
    //Solenoid controll
/*
    if(!ssGrab.getEndstopC()) {
      ssServ.ServoPos(0);
    }
    else if (!ssGrab.getEndstopA()){
      ssServ.ServoPos(1);
    }
    else if (!ssGrab.getEndstopB()){
      ssServ.ServoPos(1);
    }
    else{
      ssServ.ServoPos(0);
    }*/
    if(oi.stick.getRawButton(8)){
      ssPneu.Solenoid0(true);
    } else{
      ssPneu.Solenoid0(false);
    }
    if(oi.stick.getRawButton(7)){
      ssPneu.Solenoid1(true);
    } else{
      ssPneu.Solenoid1(false);
    }
    if(oi.stick.getRawButton(10)){
      ssPneu.Solenoid2(true);
    } else{
      ssPneu.Solenoid2(false);
    }
    if(oi.stick.getRawButton(9)){
      ssPneu.Solenoid3(true);
    } else{
      ssPneu.Solenoid3(false);
    }
    if(oi.stick.getRawButton(12)){
      ssPneu.Solenoid4(true);
    } else{
      ssPneu.Solenoid4(false);
    }
    if(oi.stick.getRawButton(11)){
      ssPneu.Solenoid5(true);
    } else{
      ssPneu.Solenoid5(false);
    }
 
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
