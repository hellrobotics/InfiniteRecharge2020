package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoSetDrive;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.CannonCMD;
import frc.robot.commands.DriveCMD;
import frc.robot.commands.ElevatorCMD;
import frc.robot.commands.IntakeCMD;
import frc.robot.commands.StorageCMD;


public class Robot extends TimedRobot {
  public static OI m_oi;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  /*
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  */

  //define all Commands as commands
  Command driveRun = new DriveCMD();
  Command cannonRun = new CannonCMD();
  Command intakeRun = new IntakeCMD();
  Command storageRun = new StorageCMD();
  Command elevatorRun = new ElevatorCMD();
  SequentialCommandGroup autoCMD;

  //declare Image width
  private static final int IMG_WIDTH = 320;

  //Camera thing
  private final Object imgLock = new Object();

  //mass variable declaring
  public static double centerX = 0;
  public static double centerY = 0;
  public static boolean isTracking = false;
  public static double distance = 0;
  public static boolean SensorA = false;
  public static double visionError = 0.0;
  public static double visionErrorY = 0.0;
  public static double CenteX = 0.0;
  public static double CenteY = 0.0;

  //Network table fetchig
  NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
  private static NetworkTableEntry centerXEntry;
  private static NetworkTableEntry centerYEntry;



  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();


    //m_chooser.addDefault("Nothing", new ExampleCommand());
		//m_chooser.addObject("Auto Mid Switch", new AutoCMDGRP());
    //autoCMD = new AutoCMDGRP();

    //initiate automatic code
    autoCMD = new SequentialCommandGroup(new AutoShoot().withTimeout(10.0),new AutoSetDrive(-0.5).withTimeout(1.0));
    /*
    final NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
    final NetworkTable table = ntinst.getTable("visionTable");
    centerXEntry = table.getEntry("centerX"); 
    centerYEntry = table.getEntry("centerY");
    */

    //Fetch x and Y cordinates
    final NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
    final NetworkTable table = ntinst.getTable("limelight");
    centerXEntry = table.getEntry("tx"); 
    centerYEntry = table.getEntry("ty");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  
    Scheduler.getInstance().run();
    CommandScheduler.getInstance().run();

    //Fetch vision Cordinates
    centerX = centerXEntry.getDouble(-1);
    centerY = centerYEntry.getDouble(-1);

    //processing vision cordinates
    CenteX = ((centerX/255) -0.5);
    CenteY = ((centerY/255) -0.5);
    
    
    //System.out.println(CenteY);
    //declare more vision things
    double centerXp;
    double centerYp;

    //Knut ka e here?
    synchronized (imgLock) {
      
      centerXp = Robot.centerX;
      centerYp = Robot.centerY;
  
    }
    if (centerXp != -1) {
      visionError = centerXp - (IMG_WIDTH / 2.0*0.25);
      //System.out.println(centerXp + " " + visionError);

    } else {
     //System.out.println("No targets X-axis");
      visionError = 0.0;
    }
    if (centerYp != -1){
      visionErrorY =centerYp - (IMG_WIDTH / 2.0*0.25);
      //System.out.println(centerYp + " " + visionError);

    } else{
      //System.out.println("No targets Y-axis");
      visionErrorY = 0.0;
    }

    //Knut ka e here? slut

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    //m_autonomousCommand = m_chooser.getSelected();

    autoCMD.schedule();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    autoCMD.cancel();
    Scheduler.getInstance().run();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    System.out.println("TELEOPINIT");
    
    //Start all manual Commands
    driveRun.start();
    storageRun.start();
    intakeRun.start();
    cannonRun.start();
    elevatorRun.start();
    
    //print when Manual controll stage is done.
    System.out.println("TELEOP DONE");

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    centerX = centerXEntry.getDouble(-1);
    centerY = centerYEntry.getDouble(-1);

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
