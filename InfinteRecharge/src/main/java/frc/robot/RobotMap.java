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

  public static int MOTOR1 = 1;
  public static int MOTOR2 = 2;
  public static int MOTOR3 = 3;
  public static int MOTOR4 = 4;

  //PCM
  public static int SOLENOID0 = 0;
  public static int SOLENOID1 = 1;
  public static int SOLENOID2 = 2;
  public static int SOLENOID3 = 3;
  public static int SOLENOID4 = 4;
  public static int SOLENOID5 = 5;

  //DIO
  public static int ENCODER1 = 0;
  public static int ENCODER2 = 1;
  public static int ENDSTOP1 = 2;
}
