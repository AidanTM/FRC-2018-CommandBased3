package org.usfirst.frc.team1803.robot.subsystems;

import org.usfirst.frc.team1803.robot.RobotMap;
import org.usfirst.frc.team1803.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/**
 *
 */
public class DrivetrainSubsystem extends Subsystem {

	//Identify the four Sparks that make up
	//the drivetrain subsystem.
	Spark leftFront;
	Spark leftBack;
	Spark rightFront;
	Spark rightBack;
	
	//Identify the Spark groups 
	SpeedControllerGroup left;
	SpeedControllerGroup right;
	public DifferentialDrive drivetrain; //Differential Drive reverses the two values of the motors.
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//Initalize the Sparks.
    	leftFront = RobotMap.leftFrontDrivetrainMotor;
    	leftBack = RobotMap.leftBackDrivetrainMotor;
    	rightFront = RobotMap.rightFrontDrivetrainMotor;
    	rightBack = RobotMap.rightBackDrivetrainMotor;
    	
    	//Initialize the Spark groups
    	left = new SpeedControllerGroup(leftFront, leftBack);
    	right = new SpeedControllerGroup(rightFront, rightBack);
    	
    	drivetrain = new DifferentialDrive(left, right);
    	//Disable safety
    	leftFront.setSafetyEnabled(false);
    	leftBack.setSafetyEnabled(false);
    	rightFront.setSafetyEnabled(false);
    	rightBack.setSafetyEnabled(false);
    	drivetrain.setSafetyEnabled(false);
    	
    	setDefaultCommand(new DriveCommand());
    }
    /**
     * Sets the speed value of the drive train.
     * @param y
     * @param x
     */
    public void Drive(double y, double x) {
    	//drivetrain.arcadeDrive(y, x);
    	drivetrain.curvatureDrive(y, x + (x * 1.2), true);
    }
}

