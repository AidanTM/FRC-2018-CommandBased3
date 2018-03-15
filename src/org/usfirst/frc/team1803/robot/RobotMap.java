/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1803.robot;

//import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static Spark leftFrontDrivetrainMotor = new Spark(1);
	public static Spark leftBackDrivetrainMotor = new Spark(2);
	
	public static Spark rightFrontDrivetrainMotor = new Spark(3);
	public static Spark rightBackDrivetrainMotor = new Spark(4);
	
	public static Spark leftIntakeMotor = new Spark(5);
	public static Spark rightIntakeMotor = new Spark(6);
	
	public static Spark bucketMotor = new Spark(7);
	
	public static Spark cameraXMotor = new Spark(8);
	//public static Spark cameraYMotor = new Spark(9);

}
