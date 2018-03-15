package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.OI;
import org.usfirst.frc.team1803.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveCommand extends Command {

	XboxController controller = OI.controller;
	
	double[] speedVal = new double[2];
	
	int gearLevel = 1;
	
	boolean test = true;
	
    public DriveCommand() {
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	speedVal[0] = -controller.getY(Hand.kLeft);
    	speedVal[1] = controller.getX(Hand.kLeft)*.5;
    	
    	SmartDashboard.putNumber("Left Stick Y", speedVal[0]);
    	SmartDashboard.putNumber("Left Stick X", speedVal[1]);
    	
    	if (controller.getPOV() == 315) gearLevel = -1;
    	if (controller.getPOV() == 0) gearLevel = 0;
    	if (controller.getPOV() == 90) gearLevel = 1;
    	
    	SmartDashboard.putNumber("Gear Level", gearLevel);
    	//SmartDashboard.putBoolean("DB/LED 0", highGear);
    	
    	speedVal[0] *= (0.5 + (0.25 * gearLevel));
    	speedVal[1] *= (0.5 + (0.25 * gearLevel));
    		
    	Robot.drivetrainSubsystem.Drive(speedVal[0], speedVal[1]);
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
