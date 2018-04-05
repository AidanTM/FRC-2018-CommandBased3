package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveAutoCommand extends TimedCommand {

	double driveSpeed;
	
    public DriveAutoCommand(double timeout, double speed) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrainSubsystem);
        driveSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//DriverStation.reportWarning("driveauto:" + driveSpeed, false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrainSubsystem.Drive(driveSpeed, 0);
    }

    // Called once after timeout
    protected void end() {
    	Robot.drivetrainSubsystem.Drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
