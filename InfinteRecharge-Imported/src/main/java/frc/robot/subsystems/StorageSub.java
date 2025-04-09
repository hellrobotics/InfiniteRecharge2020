/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class StorageSub extends SubsystemBase {
  /**
   * Creates a new StorageSub.
   */
public WPI_VictorSPX pizzaWheel = new WPI_VictorSPX(RobotMap.PIZZAWHEEL);

public WPI_TalonSRX Feeding1 = new WPI_TalonSRX(RobotMap.FEEDING1);
public WPI_TalonSRX Feeding2 = new WPI_TalonSRX(RobotMap.FEEDING2);

NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");


  public StorageSub() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  private static StorageSub m_instance;
public static synchronized StorageSub getInstance() {
		if (m_instance == null){
			m_instance = new StorageSub();
    }
    return m_instance;
    }


    //Run pizza. Used when balls are stuck
  public void RunPizza(double speed) {
    pizzaWheel.set(ControlMode.PercentOutput, speed);
    }

    //Run main Pizza wheel speed with PID controler.
    public double getPizzaSpED() {
      return(pizzaWheel.getSelectedSensorVelocity());
    }

    //runs the feeding motors
  public void RunFeeding(double speed){
      Feeding1.set(ControlMode.PercentOutput, speed*-1);
      Feeding2.set(ControlMode.PercentOutput, speed*-1);
    }

    public void TrackStorage(){
      NetworkTableEntry tv = table.getEntry("tv");
      if(tv.getDouble(0.0) == 0){
        pizzaWheel.set(ControlMode.PercentOutput, -0.5);
        Feeding1.set(ControlMode.PercentOutput, 1);
        System.out.println("ball in intake");
        
      } else{
        pizzaWheel.set(ControlMode.PercentOutput, 0);
        Feeding1.set(ControlMode.PercentOutput, 0);
      }
  
    }


}
