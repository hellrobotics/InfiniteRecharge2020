/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  //CAN

  public static int TOPLEFT = 1;
  public static int TOPRIGTH = 2;
  public static int BOTTOMLEFT = 3;
  public static int BOTTOMRIGTH = 4;
  public static int INTAKE = 5;
  public static int FEEDING1 = 7;
  public static int PIZZAWHEEL = 6;
  public static int FEEDING2 = 9;
  public static int FLYWHEELMOTOR = 10;
  public static int INTAKETOP = 11;
  public static int TURRETSPINNER = 8;
  public static int BEANWHEEL = 12;
  


  //PCM
  public static int INTAKESOLENOID = 5;
  public static int HELPERSOLENOID = 6;
  public static int ELEVATORLOCK = 4;
  //public static int ELEVATORPUSHER = 4;
  //DIO
  public static int ENCODER1 = 0;
  public static int ENCODER2 = 1;
  public static int ENDSTOPCANNONLEFT = 2;
  public static int ENDSTOPCANNONRIGHT = 3;

  public static int ENDSTOPA = 8;
  public static int ENDSTOPB = 7;
  public static int ENDSTOPC = 9;

  //PWM
  public static int SKRALLESERVO = 0;
  public static int VISSIONSERVO = 9;
  
}

