/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1803.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1803.robot.commands.AutoBucketCommand;
import org.usfirst.frc.team1803.robot.commands.AutoDefaultCommand;
import org.usfirst.frc.team1803.robot.commands.AutoSetBucketCommand;
import org.usfirst.frc.team1803.robot.commands.AutoSideCommand;
import org.usfirst.frc.team1803.robot.subsystems.*;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI m_oi;

	Integer m_autonomousCommand;
	public static boolean m_autoswitch;
	public static int m_station;
	Command m_selectedCommand;
	
	//Command m_autonomousCommand;
	//SendableChooser<Command> m_chooser;
	SendableChooser<Integer> m_chooser;
	SendableChooser<Integer> m_chooser2;
	SendableChooser<Integer> m_chooser3;
	SendableChooser<Boolean> m_chooser4;

	public static DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
	public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
	public static BucketSubsystem bucketSubsystem = new BucketSubsystem();
	public static GyroscopeSubsystem gyroscopeSubsystem = new GyroscopeSubsystem();
	//public static CameraSubsystem cameraSubsystem = new CameraSubsystem();
	
	Thread m_visionThread;
	
	int testing = 0;
	boolean testing2 = false;
	
	public static int driveMode;
	
	int autonomousMode = 0;
	int autonomousTimer = 0; //Used to count loops in autonomous mode, 50 loops = 1 second
	int autonomousStep = 0; //Used to keep track of the current autonomous step
	String gameData; //Will store the game specific message in AutonomousInit
	UsbCamera camera;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		camera = CameraServer.getInstance().startAutomaticCapture();
		/*m_chooser = new SendableChooser<Command>();
		
		m_chooser.addDefault("Middle Start - Wait, Navigate to right of Switch.", new AutoDefaultCommand());
		m_chooser.addObject("Left / Right Start - Straight forward", new AutoSideCommand());
		SmartDashboard.putData("Autonomous Selector", m_chooser);*/
		
		m_chooser = new SendableChooser<Integer>();
		
		m_chooser.addDefault("Choose automatically.", 0);
		m_chooser.addObject("Middle Start - Wait, Navigate to right of Switch.", 1);
		m_chooser.addObject("Left / Right Start - Straight forward", 2);
		m_chooser.addObject("Do nothing.", -100);
		SmartDashboard.putData("Autonomous Selector", m_chooser);
		
		m_chooser2 = new SendableChooser<Integer>();
		m_chooser2.addDefault("XBOX360 (Port 0)", 0);
		m_chooser2.addObject("Extreme3D (Port 1)", 1);
		SmartDashboard.putData("Controller Selection", m_chooser2);
		
		m_chooser3 = new SendableChooser<Integer>();
		m_chooser3.addDefault("Choose Automatically.", -1);
		m_chooser3.addObject("Station 1 (Left)", 1);
		m_chooser3.addObject("Station 2 (Middle)", 2);
		m_chooser3.addObject("Station 3 (Right)", 3);
		SmartDashboard.putData("Station Selection", m_chooser3);
		
		m_chooser4 = new SendableChooser<Boolean>();
		m_chooser4.addDefault("Do <NOT> do auto switch.", false);
		m_chooser4.addObject("Attempt auto switch.", true);
		SmartDashboard.putData("Auto Switch Selection", m_chooser4);
		
		//initCamera();
		
		Servo cameraServo = new Servo(9); //TODO Attempted to get the servo to rotate, did not work
		cameraServo.setAngle(90);

	}
	
	public void initCamera()
	{
		m_visionThread = new Thread(() -> {
			// Get the Axis camera from CameraServer
			AxisCamera camera
					= CameraServer.getInstance().addAxisCamera("axis-camera.local");
			// Set the resolution
			camera.setResolution(640, 480);

			//TODO not sure if you need the code below
			
			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream
					= CameraServer.getInstance().putVideo("Rectangle", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				// Put a rectangle on the image
				Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
						new Scalar(255, 255, 255), 5);
				// Give the output stream a new image to display
				outputStream.putFrame(mat);
			}
		});
		m_visionThread.setDaemon(true);
		m_visionThread.start();
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
	public void autonomousInit() { //TODO: Clean up this code...
		//m_autonomousCommand = m_chooser.getSelected();
		
		Robot.gyroscopeSubsystem.resetAngle(); //reset the angle of the gyro
		autonomousTimer = 0; //Reset the timer
		autonomousStep = 1; //Reset the step number
		gameData = DriverStation.getInstance().getGameSpecificMessage(); //Get the game specific message, will be RRR, LLL, RLR, LRL
		//m_autonomousCommand = m_chooser.getSelected();
		m_autoswitch = m_chooser4.getSelected();
		if (m_chooser3.getSelected() == -1) m_station = DriverStation.getInstance().getLocation();
		else m_station = m_chooser3.getSelected();
		//m_autonomousCommand = new AutoSideCommand();
		// schedule the autonomous command (example)
		//m_selectedCommand = new AutoSideCommand();
		System.out.println(m_chooser.getSelected());
		if (m_chooser.getSelected() != -100)
		{
			if (m_chooser.getSelected() < 1)
			{
				System.out.println("X");
				if (DriverStation.getInstance().getLocation() == 2)
				{
					m_selectedCommand = new AutoDefaultCommand();
					if (m_autoswitch && DriverStation.getInstance().getGameSpecificMessage().substring(0, 1).equals("R"))
						m_selectedCommand = new AutoBucketCommand();
				}
				else m_selectedCommand = new AutoSideCommand();
			}
			else if (m_chooser.getSelected() != -100)
			{
				System.out.println("A");
				if (m_chooser.getSelected() == 1)
					{
						m_selectedCommand = new AutoDefaultCommand();
						System.out.println("B");
						if (m_autoswitch && DriverStation.getInstance().getGameSpecificMessage().substring(0, 1).equals("R"))
						{
							System.out.println("C");
							m_selectedCommand = new AutoBucketCommand();
						}
							
					}
				else m_selectedCommand = new AutoSideCommand();
			}
			
			
			if (m_selectedCommand != null) {
				m_selectedCommand.start();
			}
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() { //Called every 1/50 of a second.
		Scheduler.getInstance().run(); //TODO not sure if this should be removed
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_selectedCommand != null) {
			m_selectedCommand.cancel();
		}
		
		driveMode = m_chooser2.getSelected();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called at the beginning of test mode.
	 */
	@Override
	public void testInit() { //Currently testing the gyroscope spin.
		//DriverStation.reportWarning("Testing.",false);
		//Robot.gyroscopeSubsystem.turnDegrees(90);
		//Robot.gyroscopeSubsystem.turnDegrees(-90);
		//Command buckets = new AutoSetBucketCommand(1);
		
		//buckets.start();
		
		testing2 = false;
	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		if (!testing2)
		{
			Command buckets = new AutoSetBucketCommand(1);
			
			buckets.start();
			testing2 = true;
		}
		
	}
}
