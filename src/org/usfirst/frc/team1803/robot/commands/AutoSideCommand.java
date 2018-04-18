package org.usfirst.frc.team1803.robot.commands;

import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSideCommand extends CommandGroup {

    public AutoSideCommand() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	if (Robot.m_station == 1 && DriverStation.getInstance().getGameSpecificMessage().substring(0, 1).equals("L")
    			&& Robot.m_autoswitch) 
    	{
    		addSequential(new DriveAutoCommand(2.0, 0.5));
    		addSequential(new AutoSetBucketCommand(3.8));
    	}
    	else if (Robot.m_station == 3 && DriverStation.getInstance().getGameSpecificMessage().substring(0, 1).equals("R")
    			&& Robot.m_autoswitch) 
    	{
    		addSequential(new DriveAutoCommand(2.0, 0.5));
    		addSequential(new AutoSetBucketCommand(3.8));
    	}
    	else
    	{
        	addSequential(new DriveAutoCommand(3.0, 0.5));
    	}
    	// To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
