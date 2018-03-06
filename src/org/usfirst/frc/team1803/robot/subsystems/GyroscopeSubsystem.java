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
    
    public double getAngle()
    {
    	return gyro.getAngle();
    }
    
    public void resetAngle()
    {
    	gyro.reset();
    }
    
    public void calibrate()
    {
    	gyro.calibrate();
    }
    
    public void turnDegrees(double degrees) //Range of Gyro values: (???-???) TODO: FIND THIS VALUE
    {
    	if (isTurning) return; //To prevent multiple turn degree commands from being run at once.
    	
    	isTurning = true;
    	turnGoal = getAngle() + degrees;
    	while (Math.abs(turnGoal - getAngle()) > 1.0)
    	{
    		Robot.drivetrainSubsystem.Drive(0, 0.5);
    		Timer.delay(0.005);
    	}
    	isTurning = false;
    }
}

