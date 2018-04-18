package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSetBucketCommand extends Command {

	int loopCount = 0;
	boolean goingUp = false;
	int totalTime = 20;
	
    public AutoSetBucketCommand(double time) { //1 for up, -1 for down
        requires(Robot.bucketSubsystem);
        if (time >= 0) goingUp = true;
        else goingUp = false;
        goingUp = true;
        totalTime = (int) Math.ceil(Math.abs(time) * 20);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Auto Bucket");
    	//totalTime = 20;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (goingUp) Robot.bucketSubsystem.setBucketSpeed(-0.5);
    	else Robot.bucketSubsystem.setBucketSpeed(0.1);
    	loopCount++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (loopCount > totalTime) 
    		{
    			Robot.bucketSubsystem.setBucketSpeed(0);
    			return true;
    		}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
