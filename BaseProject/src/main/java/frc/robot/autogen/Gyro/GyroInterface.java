package frc.robot.autogen.Gyro;

import edu.wpi.first.math.geometry.Rotation2d;

public interface GyroInterface {
    void initialize();
    Rotation2d getAngle();
}
