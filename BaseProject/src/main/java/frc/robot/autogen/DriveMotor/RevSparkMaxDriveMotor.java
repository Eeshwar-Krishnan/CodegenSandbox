//JINJA
package frc.robot.autogen.DriveMotor;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

//FILE START
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class RevSparkMaxDriveMotor implements DriveMotorInterface {
    SparkMax driveMotor;

    @Override
    public void initialize(SwerveModuleConstants constants) {
        driveMotor = new SparkMax(constants.driveMotorCanID, MotorType.kBrushless);

        SparkMaxConfig driveConfig = new SparkMaxConfig();
        driveConfig.idleMode(IdleMode.kBrake);
        driveConfig.smartCurrentLimit((int)Constants.SwerveConstants.driveCurrentLimit);

        driveConfig.encoder.positionConversionFactor((1.0 / Constants.SwerveConstants.driveRatio) * Constants.SwerveConstants.wheelCircumference);
        driveConfig.encoder.velocityConversionFactor((1.0 / Constants.SwerveConstants.driveRatio) * Constants.SwerveConstants.wheelCircumference);

        driveConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        driveConfig.closedLoop.pid(Constants.SwerveConstants.drivekP, 0, Constants.SwerveConstants.drivekD);
        driveConfig.closedLoop.velocityFF(Constants.SwerveConstants.drivekV);
        driveConfig.closedLoop.outputRange(-1.0, 1.0);

        driveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void setSpeed(SwerveModuleState state, boolean useOpenLoop) {
        if(useOpenLoop) {
            driveMotor.set(state.speedMetersPerSecond / Constants.SwerveConstants.maxChassisSpeed);
        }else {
            driveMotor.getClosedLoopController().setReference(state.speedMetersPerSecond, ControlType.kVelocity);
        }
    }

    @Override
    public double getWheelPosition() {
        return driveMotor.getEncoder().getPosition();
    }

    @Override
    public double getWheelVelocity() {
        return driveMotor.getEncoder().getVelocity();
    }
    
}
