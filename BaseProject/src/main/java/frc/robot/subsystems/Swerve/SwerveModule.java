package frc.robot.subsystems.Swerve;
//{{ drive_motor_imports }}
//{{ turn_motor_imports }}
//{% if angle_encoder_imports is defined %}
//{{ angle_encoder_imports }}
//{% endif %}

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.SwerveConstants.SwerveModuleConstants;

public class SwerveModule {
    //{{ drive_motor_variables }}
    //{{ turn_motor_variables }}
    //{% if angle_encoder_variables is defined %}
    //  {{ angle_encoder_variables }}
    //{% endif %}
    
    public SwerveModule(SwerveModuleConstants constants){
        //{{ drive_motor_initialize }}
        //{{ turn_motor_initialize }}
        //{% if angle_encoder_initialize is defined %}
        //  {{ angle_encoder_initialize }}
        //{% endif %}
        //{% if not attached_encoder %}
        resynchronizeTurnEncoder();
        //{% endif %}
    }

    public void setDesiredState(SwerveModuleState state, boolean useOpenLoop) {
        state.optimize(getModuleAngle());
        //{{ turn_motor_set_rotation }}
        //{{ drive_motor_set_speed }}
    }

    public Rotation2d getModuleAngle() {
        //{% if angle_encoder_get_rotation is defined %}
        //{{angle_encoder_get_rotation }}
        //{% else %}
        //{{ turn_motor_get_rotation }}
        //{% endif %}

        //this is just to kill the compiler warning
        return Rotation2d.kZero;
    }

    private double getWheelVelocity(){
        // {{ drive_motor_get_wheel_velocity }}
        return 0;
    }

    private double getWheelPosition(){
        // {{ drive_motor_get_wheel_position }}
        return 0;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            getWheelVelocity(),
            getModuleAngle()
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            getWheelPosition(),
            getModuleAngle()
        );
    }

    //{% if not attached_encoder %}
        public void resynchronizeTurnEncoder() {
            Rotation2d angle = getModuleAngle();
            //{{ resynchronize_turn_encoder }}
        }
    //{% endif %}
}
