package frc.robot.autogen.DriveMotor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

public interface DriveMotorInterface {
    void initialize(SwerveModuleConstants constants);
    void setSpeed(SwerveModuleState state, boolean useOpenLoop);
    double getWheelPosition();
    double getWheelVelocity();
}
