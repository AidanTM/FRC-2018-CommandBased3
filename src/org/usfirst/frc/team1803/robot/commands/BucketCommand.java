package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.OI;
import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BucketCommand extends Command {

	XboxController controller = OI.controller;
	int bucketSpeed = 0;
	
    public BucketCommand() {
        requires(Robot.bucketSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.bucketSubsystem.setBucketSpeed(controller.getY(Hand.kRight) * 0.5);
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
