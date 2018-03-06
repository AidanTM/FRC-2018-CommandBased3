package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegreesCommand extends Command {

	double initDegrees;
	
    public TurnDegreesCommand() {
        requires(Robot.gyroscopeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize(double anglemod) {
    	//initDegrees = Robot.gyroscopeSubsystem.getAngle();
    	initDegrees = Robot.gyroscopeSubsystem.getAngle() + anglemod;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrainSubsystem.Drive(0, 0.5);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Math.abs(initDegrees - getAngle()) < 1) return true;
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    protected double getAngle() {
    	return Robot.gyroscopeSubsystem.getAngle();
    }
}