//JINJA
package frc.robot.autogen.Encoder;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

//FILE START
import com.ctre.phoenix6.hardware.CANcoder;

public class CTRECANCoder implements EncoderInterface {
    CANcoder angleEncoder;

    @Override
    public void initialize(SwerveModuleConstants constants) {
        angleEncoder = new CANcoder(constants.encoderCanID);
    }

    @Override
    public Rotation2d getRotations() {
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().getValueAsDouble());
    }
    
}
