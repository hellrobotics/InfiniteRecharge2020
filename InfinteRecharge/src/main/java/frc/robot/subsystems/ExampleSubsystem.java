
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


public class ExampleSubsystem extends Subsystem {
  // Inserting all Motorcontrollers
  public WPI_VictorSPX motor1 = new WPI_VictorSPX(RobotMap.MOTOR1);
  public WPI_VictorSPX motor2 = new WPI_VictorSPX(RobotMap.MOTOR2);
  public WPI_VictorSPX motor3 = new WPI_VictorSPX(RobotMap.MOTOR3);
  public WPI_VictorSPX motor4 = new WPI_VictorSPX(RobotMap.MOTOR4);

  @Override
  protected void initDefaultCommand() {  
  }

  @Override
  public Command getCurrentCommand() {
    return super.getCurrentCommand();
  }

  //Function for motor speed controll
  public void RunMotor1(double speed) {
    motor1.set(speed);
  }
  public void RunMotor2(double speed) {
    motor2.set(speed);
  }
  public void RunMotor3(double speed) {
    motor3.set(speed);
  }
  public void RunMotor4(double speed) {
    motor4.set(speed);
  }
  //Idk tbh
  private static ExampleSubsystem m_instance;
	public static synchronized ExampleSubsystem getInstance() {
		if (m_instance == null){
			m_instance = new ExampleSubsystem();
		}
		return m_instance;
  }
}