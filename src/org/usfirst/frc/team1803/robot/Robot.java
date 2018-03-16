/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1803.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1803.robot.commands.*;
import org.usfirst.frc.team1803.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	public static DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
	public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
	public static BucketSubsystem bucketSubsystem = new BucketSubsystem();
	public static GyroscopeSubsystem gyroscopeSubsystem = new GyroscopeSubsystem();
	public static CameraSubsystem cameraSubsystem = new CameraSubsystem();
	
	Thread m_visionThread;
	
	int testing = 0;
	
	int autonomousMode = 0;
	int autonomousTimer = 0; //Used to count loops in autonomous mode, 50 loops = 1 second
	int autonomousStep = 0; //Used to keep track of the current autonomous step
	String gameData; //Will store the game specific message in AutonomousInit
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser = new SendableChooser<Command>();
		m_chooser.addDefault("Middle Start - Wait, Navigate to right of Switch.", new AutoDefaultCommand());
		m_chooser.addObject("Left / Right Start - Straight forward", new AutoSideCommand());
		SmartDashboard.putData("Autonomous Selector", m_chooser);
		
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
	public void autonomousInit() {
		//m_autonomousCommand = m_chooser.getSelected();
		
		Robot.gyroscopeSubsystem.resetAngle(); //reset the angle of the gyro
		autonomousTimer = 0; //Reset the timer
		autonomousStep = 1; //Reset the step number
		gameData = DriverStation.getInstance().getGameSpecificMessage(); //Get the game specific message, will be RRR, LLL, RLR, LRL
		
		m_autonomousCommand = m_chooser.getSelected();
		
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
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
	}

	/**
	 * This function is called at the beginning of test mode.
	 */
	@Override
	public void testInit() { //Currently testing the gyroscope spin.
		//Robot.gyroscopeSubsystem.turnDegrees(180);
		//DriverStation.reportWarning("" + Robot.gyroscopeSubsystem.getAngle(),false);
	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		//.reportWarning("TriggerAxis Left: " + OI.controller.getTriggerAxis(Hand.kLeft), false);
		//DriverStation.reportWarning("TriggerAxis Right: " + OI.controller.getTriggerAxis(Hand.kRight), false);
		//testing++;
		//if (testing > 100)
		//{
		//	DriverStation.reportWarning("Gyro:" + gyroscopeSubsystem.getAngle(), false);
		//	testing = 0;
		//}
	}
}
