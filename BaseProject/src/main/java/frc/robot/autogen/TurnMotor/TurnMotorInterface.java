package frc.robot.autogen.TurnMotor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

public interface TurnMotorInterface {
    void initialize(SwerveModuleConstants constants);
    void setRotation(SwerveModuleState state);
    Rotation2d getRotation();
    void resynchronizeTurnEncoder(Rotation2d angle);
}
