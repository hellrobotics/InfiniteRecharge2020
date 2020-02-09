
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class EncoderSub extends SubsystemBase {
  //Add endstop and encoder
  public DigitalInput endStop1 = new DigitalInput(RobotMap.ENDSTOP1);

  public DigitalInput endStopA = new DigitalInput(RobotMap.ENDSTOPA);

  public DigitalInput endStopB = new DigitalInput(RobotMap.ENDSTOPB);

  public DigitalInput endStopC = new DigitalInput(RobotMap.ENDSTOPC);

  public Encoder encoder1 = new Encoder(RobotMap.ENCODER1, RobotMap.ENCODER2);


  public EncoderSub() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  //Fetch encoder posisjon
  public int getEncoderPos1() {
    return encoder1.get();
  }
  
  //Grab endstop state
  public boolean getEndstop1() {
    return endStop1.get();
  }
  public boolean getEndstopA() {
    return endStopA.get();
  }
  public boolean getEndstopB() {
    return endStopB.get();
  }
  public boolean getEndstopC() {
    return endStopC.get();
  }
  
  //Idk tbh
  private static EncoderSub m_instance;
	public static synchronized EncoderSub getInstance() {
		if (m_instance == null){
			m_instance = new EncoderSub();
		}
		return m_instance;
  }

}
