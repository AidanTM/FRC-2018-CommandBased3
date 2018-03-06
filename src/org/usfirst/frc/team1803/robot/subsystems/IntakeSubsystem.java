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

	Talon leftMotor;
	Talon rightMotor;
	
	DifferentialDrive motorGroup;

    public void initDefaultCommand() {
    	//Initiate the Talon and DifferentialDrive
    	leftMotor = RobotMap.leftIntakeMotor;
    	rightMotor = RobotMap.rightIntakeMotor;
    	motorGroup = new DifferentialDrive(leftMotor, rightMotor);
    	leftMotor.setSafetyEnabled(false);
    	rightMotor.setSafetyEnabled(false);
    	motorGroup.setSafetyEnabled(false);
    	//Set the default command
        setDefaultCommand(new IntakeCommand());
    }
    
    public void intakeSpeed(double speed)
    {
    	motorGroup.tankDrive(speed, speed);
    }
}

