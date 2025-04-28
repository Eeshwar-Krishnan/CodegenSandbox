//JINJA
package frc.robot.autogen.Gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.SwerveConstants;

//FILE START
import com.ctre.phoenix6.hardware.Pigeon2;

public class CTREPigeon2 implements GyroInterface {
    Pigeon2 gyro;

    @Override
    public void initialize() {
        gyro = new Pigeon2(SwerveConstants.gyroId);
    }

    @Override
    public Rotation2d getAngle() {
        return gyro.getRotation2d();
    }
    
}
