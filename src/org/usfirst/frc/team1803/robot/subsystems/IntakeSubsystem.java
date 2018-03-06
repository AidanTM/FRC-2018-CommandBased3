package org.usfirst.frc.team1803.robot.subsystems;

import org.usfirst.frc.team1803.robot.RobotMap;
import org.usfirst.frc.team1803.robot.commands.IntakeCommand;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {

	//Identify the two talons for the intake
	Talon leftMotor;
	Talon rightMotor;
	//And then group them up
	DifferentialDrive motorGroup;

    public void initDefaultCommand() {
    	//Initiate the Talon and DifferentialDrive
    	leftMotor = RobotMap.leftIntakeMotor;
    	rightMotor = RobotMap.rightIntakeMotor;
    	motorGroup = new DifferentialDrive(leftMotor, rightMotor);
    	//Remove safety for extra speed
    	leftMotor.setSafetyEnabled(false);
    	rightMotor.setSafetyEnabled(false);
    	motorGroup.setSafetyEnabled(false);
    	//Set the default command
        setDefaultCommand(new IntakeCommand());
    }
    
    /**
     * Sets the intake speed of the intake.
     * @param speed The intake value. Use a negative value to spit out cube.
     */
    public void intakeSpeed(double speed)
    {
    	motorGroup.tankDrive(speed, speed);
    }
}

