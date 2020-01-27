package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.EncoderSub;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ServoSub;
import frc.robot.subsystems.pneumaticSub;


public class ExampleCommand extends Command {

  //Add HID device and Subsystem grabs
  private final OI oi;
  private final ExampleSubsystem ssRun;
  private final EncoderSub ssGrab;
  private final ServoSub  ssServ;
  private final pneumaticSub ssPneu;

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


    /*
    //Encoder testing
    final int refference = (int) (oi.stick.getRawAxis(3)*1000);
    int pos = ssGrab.getEncoderPos1();
    if(!ssGrab.getEndstop1()){
    pos = ssGrab.getEncoderPos1();

    //More encoder testing
    final double error = refference - pos;
    final double SPeD = error * 0.05;

    SmartDashboard.putNumber("grabPos", pos);
    SmartDashboard.putNumber("SPeD", SPeD);
    ssRun.RunMotor2((SPeD) * -1);
    
    try {
      Thread.sleep(10);
    } catch (final InterruptedException e) {
      
      e.printStackTrace();
    }

  } else{
    final double error = 0 - pos;
    final double SPeD = error * 0.05;
    SmartDashboard.putNumber("grabPos", pos);
    SmartDashboard.putNumber("SPeD", SPeD);
  }
  
  */
  
    //Vision control
    if (oi.controller.getRawButton(1)) {
   double error = 0 -  Robot.CenteX;
   double speed = error * 1;
        System.out.println(speed);
        ssRun.RunMotor2(error);
    }
    else{
    ssRun.RunMotor2(oi.controller.getRawAxis(0)/(-3));

    }
    
    //Run motors
   

  
    //Solenoid controll

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
