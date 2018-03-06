/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1803.robot;

//import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static Talon leftFrontDrivetrainMotor = new Talon(1);
	public static Talon leftBackDrivetrainMotor = new Talon(2);
	
	public static Talon rightFrontDrivetrainMotor = new Talon(3);
	public static Talon rightBackDrivetrainMotor = new Talon(4);
	
	public static Talon leftIntakeMotor = new Talon(5);
	public static Talon rightIntakeMotor = new Talon(6);
	
	public static Talon bucketMotor = new Talon(7);

}
