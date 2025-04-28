package frc.robot.autogen.Encoder;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

public interface EncoderInterface {
    void initialize(SwerveModuleConstants constants);
    Rotation2d getRotations();
}
