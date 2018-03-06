package org.usfirst.frc.team1803.robot.subsystems;

import org.usfirst.frc.team1803.robot.RobotMap;
import org.usfirst.frc.team1803.robot.commands.BucketCommand;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BucketSubsystem extends Subsystem {

    Talon bucketMotor;
    
    /**
     * Runs when the robot is powered on. Initializes the
     * bucket motor talon and disables its safety.
     * Sets up the default command.
     */
    public void initDefaultCommand() {
    	bucketMotor = RobotMap.bucketMotor;
    	bucketMotor.setSafetyEnabled(false);
        setDefaultCommand(new BucketCommand());
    }
    /**
     * Sets the speed of the bucket.
     * Make sure to keep this low since the bucket can smash things :(
     * @param speed
     */
    public void setBucketSpeed(double speed)
    {
    	bucketMotor.set(-speed);
    }
}

