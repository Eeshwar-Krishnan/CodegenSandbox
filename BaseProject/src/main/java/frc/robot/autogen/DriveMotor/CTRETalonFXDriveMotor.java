//JINJA
package frc.robot.autogen.DriveMotor;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

//FILE START
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class CTRETalonFXDriveMotor implements DriveMotorInterface {
    TalonFX driveMotor;
    private final SimpleMotorFeedforward driveFeedForward = new SimpleMotorFeedforward(Constants.SwerveConstants.drivekS, Constants.SwerveConstants.drivekV, Constants.SwerveConstants.drivekA);
    private final DutyCycleOut driveDutyCycle = new DutyCycleOut(0);
    private final VelocityVoltage driveVelocity = new VelocityVoltage(0);


    @Override
    public void initialize(SwerveModuleConstants constants) {
        driveMotor = new TalonFX(constants.driveMotorCanID);

        TalonFXConfiguration swerveDriveFXConfig = new TalonFXConfiguration();

        swerveDriveFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        swerveDriveFXConfig.Feedback.SensorToMechanismRatio = Constants.SwerveConstants.driveRatio;

        swerveDriveFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        swerveDriveFXConfig.CurrentLimits.SupplyCurrentLimit = Constants.SwerveConstants.driveCurrentLimit;
        swerveDriveFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        swerveDriveFXConfig.CurrentLimits.StatorCurrentLimit = Constants.SwerveConstants.driveCurrentLimit;

        swerveDriveFXConfig.Slot0.kP = Constants.SwerveConstants.drivekP;
        swerveDriveFXConfig.Slot0.kD = Constants.SwerveConstants.drivekD;

        driveMotor.getConfigurator().apply(swerveDriveFXConfig);
    }

    @Override
    public void setSpeed(SwerveModuleState state, boolean useOpenLoop) {
        if(useOpenLoop){
            driveDutyCycle.Output = state.speedMetersPerSecond / Constants.SwerveConstants.maxSpeed;
            driveMotor.setControl(driveDutyCycle);
        }
        else {
            driveVelocity.Velocity = state.speedMetersPerSecond / Constants.SwerveConstants.wheelCircumference;
            driveVelocity.FeedForward = driveFeedForward.calculate(state.speedMetersPerSecond);
            driveMotor.setControl(driveVelocity);
        }
    }

    @Override
    public double getWheelPosition() {
        return driveMotor.getPosition().getValueAsDouble() * Constants.SwerveConstants.wheelCircumference;
    }

    @Override
    public double getWheelVelocity() {
        return driveMotor.getVelocity().getValueAsDouble() * Constants.SwerveConstants.wheelCircumference;
    }
    
}
