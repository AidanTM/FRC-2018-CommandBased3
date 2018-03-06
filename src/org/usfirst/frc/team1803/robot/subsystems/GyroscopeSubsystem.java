package org.usfirst.frc.team1803.robot.subsystems;

import org.usfirst.frc.team1803.robot.Robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroscopeSubsystem extends Subsystem {

	//Identify the gyro variable and other values.
    ADXRS450_Gyro gyro;
    
    double turnGoal;
    boolean isTurning;

    
    public void initDefaultCommand() {
    	
    	gyro = new ADXRS450_Gyro(Port.kOnboardCS0);

    	if (gyro == null) return;
    	//gyro = new AnalogGyro(0);
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
	    gyro.calibrate();
	    SmartDashboard.putNumber("gyro", getAngle());
	    DriverStation.reportWarning("Gyro calibrated.", false);
    }	
    
    /**
     * Gets the value of the gyro's angle
     * @return The angle of the gyro as a value from ???-???
     */
    public double getAngle()
    {
    	return gyro.getAngle();
    }
    
    /**
     * Sets the gyro's value to zero.
     */
    public void resetAngle()
    {
    	gyro.reset();
    }
    
    /**
     * Sets up the gyro.
     */
    public void calibrate()
    {
    	gyro.calibrate();
    }
    
    /**
     * Turns the robot a specified amount of degrees.
     * @param degrees Amount of degrees to turn clockwise (When looking from above)
     */
    public void turnDegrees(double degrees) //Range of Gyro values: (???-???) TODO: FIND THIS VALUE
    {
    	if (isTurning) return; //To prevent multiple turn degree commands from being run at once.
    	
    	double turnValue; //Set the turn direction depending on if the degrees value is negative or not.
    	if (degrees < 0.0) turnValue = -0.5;
    	else if (degrees > 0.0) turnValue = 0.5;
    	else return;
    	
    	isTurning = true; //Initiate some values.
    	turnGoal = getAngle() + degrees;
    	
    	while (Math.abs(turnGoal - getAngle()) > 1.0) //Turning loop.
    	{ // TODO: Maybe we can use a periodic loop to do this rather than using the timer?
    		Robot.drivetrainSubsystem.Drive(0, turnValue);
    		Timer.delay(0.005);
    	}
    	isTurning = false;
    }
}

