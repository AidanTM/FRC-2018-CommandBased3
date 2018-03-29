package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.OI;
import org.usfirst.frc.team1803.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveCommand extends Command {

	XboxController controller = OI.controller;
	Joystick controller2 = OI.controller2;
	
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
    	
    	if (Robot.driveMode == 0)
    	{
    		speedVal[0] = -controller.getY(Hand.kLeft);
        	speedVal[1] = controller.getX(Hand.kLeft)*.5;
        	
        	if (controller.getPOV() == 315) gearLevel = -1;
        	if (controller.getPOV() == 0) gearLevel = 0;
        	if (controller.getPOV() == 90) gearLevel = 1;
    	}
    	else if (Robot.driveMode == 1)
    	{
    		speedVal[0] = -controller2.getY();
        	speedVal[1] = controller2.getZ() * 0.5;
        	
        	if (controller2.getPOV() == 0) gearLevel = 2;
        	else if (controller2.getPOV() == 45 || controller2.getPOV() == 315) gearLevel = 1;
        	else if (controller2.getPOV() == 90 || controller2.getPOV() == 270) gearLevel = 0;
        	else if (controller2.getPOV() == 225 || controller2.getPOV() == 135) gearLevel = -1;
        	else if (controller2.getPOV() == 180) gearLevel = -2;
        	else gearLevel = 0;
    	}
    	
    	speedVal[0] *= (0.5 + (0.16 * gearLevel));
    	speedVal[1] *= (0.5 + (0.16 * gearLevel));
    	
    	SmartDashboard.putNumber("Left Stick Y", speedVal[0]);
    	SmartDashboard.putNumber("Left Stick X", speedVal[1]);
    	SmartDashboard.putNumber("Gear Level", gearLevel);
    		
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
