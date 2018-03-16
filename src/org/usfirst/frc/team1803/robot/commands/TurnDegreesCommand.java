package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegreesCommand extends Command {

	double degreeCount;
	
	final int MIN_TURNTIME = 20; //In 1/50 seconds.
	
	int timerVal; //We need to set a minimal
	//time to finish the turn since the robot needs time to set up the turn
	
    public TurnDegreesCommand(double angleMod) {
        requires(Robot.gyroscopeSubsystem);
        degreeCount = angleMod;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timerVal = 0;
    	Robot.gyroscopeSubsystem.turnDegrees(degreeCount);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (timerVal < MIN_TURNTIME) timerVal++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timerVal >= MIN_TURNTIME && Robot.gyroscopeSubsystem.isTurning());
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