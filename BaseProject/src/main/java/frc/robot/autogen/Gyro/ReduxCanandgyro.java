//JINJA
package frc.robot.autogen.Gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.SwerveConstants;

//FILE START
import com.reduxrobotics.sensors.canandgyro.Canandgyro;

public class ReduxCanandgyro implements GyroInterface {
    Canandgyro gyro;

    @Override
    public void initialize() {
        gyro = new Canandgyro(SwerveConstants.gyroId);
    }

    @Override
    public Rotation2d getAngle() {
        return gyro.getRotation2d();
    }
    
}
