package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.OI;
import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	
	//Set up the identifiers
	XboxController controller = OI.controller;
	//Values for the individual controller triggers
	double speedValLeft;
	double speedValRight;

    public IntakeCommand() {
        requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Initiate the speed values from the controller.
    	speedValLeft = OI.controller.getTriggerAxis(Hand.kLeft);
    	speedValRight = OI.controller.getTriggerAxis(Hand.kRight);
    	//if (OI.controller.getAButton()) speedVal *= -1;
    	//Set the intake speed based off the values of the triggers
    	if (speedValLeft > .1)
    		Robot.intakeSubsystem.intakeSpeed(speedValLeft * -1);
    	else if (speedValRight > .1)
    		Robot.intakeSubsystem.intakeSpeed(speedValRight);
    	else
    		Robot.intakeSubsystem.intakeSpeed(0);
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
