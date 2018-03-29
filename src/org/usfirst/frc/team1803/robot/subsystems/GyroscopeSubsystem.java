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
	    SmartDashboard.putNumber("Gyro", getAngle());
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
     * Gets if the robot is turning.
     * @return Boolean if the robot is turning.
     */
    public boolean isTurning()
    {
    	return isTurning;
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
    	if (Math.abs(degrees - 0.00001) < 3 || isTurning) return;
    	
    	turnGoal = getAngle() + degrees;
    	//double initalAngle = getAngle();
    	
    	double turnValue = 0.3; //Set the turn direction depending on if the degrees value is negative or not.
    	if (getAngle() < turnGoal) turnValue *= 1;
    	else if (getAngle() > turnGoal) turnValue *= -1;
    	else return;
    	
    	isTurning = true; //Initiate some values.
    	//(turnValue < 0 && getAngle() > turnGoal) || (turnValue > 0 && getAngle() < turnGoal)
    	SmartDashboard.putNumber("Final Angle", turnGoal);
    	DriverStation.reportWarning("InitAngle: " + getAngle() + " Final Angle: " + turnGoal, false);
    	if (turnValue > 0)
    	{
    		while (getAngle() < turnGoal) //Turning loop.
        	{
    			SmartDashboard.putNumber("Final Angle", turnGoal);
    			SmartDashboard.putNumber("Gyro", gyro.getAngle());
        		Robot.drivetrainSubsystem.Drive(0, turnValue);
        		Timer.delay(0.005);
        	}
    	}
    	else// if (turnValue < 0)
    	{
    		while (getAngle() > turnGoal) //Turning loop.
        	{
    			SmartDashboard.putNumber("Final Angle", turnGoal);
    			SmartDashboard.putNumber("Gyro", gyro.getAngle());
        		Robot.drivetrainSubsystem.Drive(0, turnValue);
        		Timer.delay(0.005);
        	}
    	}
    	Robot.drivetrainSubsystem.Drive(0, 0);
    	DriverStation.reportWarning("DONE InitAngle: " + getAngle() + " Final Angle: " + turnGoal, false);
    	
    	isTurning = false;
    }
}

