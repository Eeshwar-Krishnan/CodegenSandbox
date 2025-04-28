//JINJA
package frc.robot.autogen.TurnMotor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

//FILE START
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class CTRETalonFXTurnMotor implements TurnMotorInterface {
    TalonFX turnMotor;
    private final PositionVoltage anglePosition = new PositionVoltage(0);

    @Override
    public void initialize(SwerveModuleConstants constants) {
        turnMotor = new TalonFX(constants.turnMotorCanID);

        TalonFXConfiguration swerveAngleFXConfig = new TalonFXConfiguration();

        swerveAngleFXConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        swerveAngleFXConfig.Feedback.SensorToMechanismRatio = Constants.SwerveConstants.turnRatio;
        swerveAngleFXConfig.ClosedLoopGeneral.ContinuousWrap = true;
        
        swerveAngleFXConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        swerveAngleFXConfig.CurrentLimits.SupplyCurrentLimit = Constants.SwerveConstants.turnCurrentLimit;
        swerveAngleFXConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        swerveAngleFXConfig.CurrentLimits.StatorCurrentLimit = Constants.SwerveConstants.turnCurrentLimit;

        swerveAngleFXConfig.Slot0.kP = Constants.SwerveConstants.turnkP;
        swerveAngleFXConfig.Slot0.kD = Constants.SwerveConstants.turnkD;

        turnMotor.getConfigurator().apply(swerveAngleFXConfig);
    }

    @Override
    public void setRotation(SwerveModuleState state) {
        turnMotor.setControl(anglePosition.withPosition(state.angle.getRotations()));
    }

    @Override
    public Rotation2d getRotation() {
        return Rotation2d.fromRotations(turnMotor.getPosition().getValueAsDouble());
    }

    @Override
    public void resynchronizeTurnEncoder(Rotation2d angle) {
        turnMotor.setPosition(angle.getRotations());
    }
    
}
