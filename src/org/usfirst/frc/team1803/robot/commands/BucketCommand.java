package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.OI;
import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BucketCommand extends Command {

	XboxController controller = OI.controller;
	Joystick controller2 = OI.controller2;
	int bucketSpeed = 0;
	
    public BucketCommand() {
        requires(Robot.bucketSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.driveMode == 0)
    	{
    		Robot.bucketSubsystem.setBucketSpeed(controller.getY(Hand.kRight) * 0.55);
    	}
    	else if (Robot.driveMode == 1)
    	{
    		double speedVal = controller2.getRawAxis(0);
        	speedVal = Math.round(speedVal * 100) / 100;
        	if (speedVal > 0.5) Robot.bucketSubsystem.setBucketSpeed(speedVal - 0.5);
        	else if (speedVal < -0.5) Robot.bucketSubsystem.setBucketSpeed(speedVal + 0.5);
        	else Robot.bucketSubsystem.setBucketSpeed(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
