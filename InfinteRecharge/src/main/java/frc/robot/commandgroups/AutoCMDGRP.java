/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoSetDrive;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutoTrackTarget;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoCMDGRP extends SequentialCommandGroup {
  /**
   * Creates a new AutoCMDGRP.
   */
  public AutoCMDGRP() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    /*super(
      new AutoSetDrive(0.5).withTimeout(1.0),
      new AutoShoot().withTimeout(5.0)
    );*/  
    System.out.println("GAMER");
    addCommands(new AutoSetDrive(0.5).withTimeout(1000.0), new AutoShoot().withTimeout(5000.0));
  }
}
