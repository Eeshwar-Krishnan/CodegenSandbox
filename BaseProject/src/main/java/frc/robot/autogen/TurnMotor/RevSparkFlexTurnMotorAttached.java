//JINJA
package frc.robot.autogen.TurnMotor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

//FILE START
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class RevSparkFlexTurnMotorAttached implements TurnMotorInterface {
    SparkFlex turnMotor;

    @Override
    public void initialize(SwerveModuleConstants constants) {
        turnMotor = new SparkFlex(constants.turnMotorCanID, MotorType.kBrushless);

        SparkFlexConfig turnConfig = new SparkFlexConfig();
        turnConfig.idleMode(IdleMode.kBrake);
        turnConfig.smartCurrentLimit((int)Constants.SwerveConstants.turnCurrentLimit);

        turnConfig.encoder.positionConversionFactor(1.0);
        turnConfig.encoder.velocityConversionFactor(1.0);

        turnConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder);
        turnConfig.closedLoop.pid(Constants.SwerveConstants.turnkP, 0, Constants.SwerveConstants.turnkD);
        turnConfig.closedLoop.outputRange(-1.0, 1.0);
        turnConfig.closedLoop.positionWrappingEnabled(true);
        turnConfig.closedLoop.positionWrappingInputRange(0, 1.0);

        turnMotor.configure(turnConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void setRotation(SwerveModuleState state) {
        turnMotor.getClosedLoopController().setReference(state.angle.getRotations(), ControlType.kPosition);
    }

    @Override
    public Rotation2d getRotation() {
        return Rotation2d.fromRotations(turnMotor.getAbsoluteEncoder().getPosition());
    }

    @Override
    public void resynchronizeTurnEncoder(Rotation2d angle) {
        turnMotor.getEncoder().setPosition(angle.getRotations());
    }
    
}
