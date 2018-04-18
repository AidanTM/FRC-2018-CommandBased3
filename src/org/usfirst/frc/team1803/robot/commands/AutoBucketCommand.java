package org.usfirst.frc.team1803.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBucketCommand extends CommandGroup {

    public AutoBucketCommand() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	addSequential(new DelayCommand(2.0));
    	addSequential(new DriveAutoCommand(0.25, 0.5));
    	addSequential(new TurnDegreesCommand(15));
    	addSequential(new DriveAutoCommand(0.25, 0.5));
    	addSequential(new TurnDegreesCommand(-15));
    	addSequential(new DriveAutoCommand(1.3, 0.4));
    	addSequential(new AutoSetBucketCommand(3.25));
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
