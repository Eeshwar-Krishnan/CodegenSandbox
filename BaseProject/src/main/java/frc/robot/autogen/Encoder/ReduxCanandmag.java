//JINJA
package frc.robot.autogen.Encoder;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

//FILE START
import com.reduxrobotics.sensors.canandmag.Canandmag;

public class ReduxCanandmag implements EncoderInterface{
    Canandmag angleEncoder;

    @Override
    public void initialize(SwerveModuleConstants constants) {
        angleEncoder = new Canandmag(constants.encoderCanID);
    }

    @Override
    public Rotation2d getRotations() {
        return Rotation2d.fromRotations(angleEncoder.getAbsPosition());
    }
    
}
