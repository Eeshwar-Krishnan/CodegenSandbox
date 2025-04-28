// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class SwerveConstants {
    public static final double trackWidth = 0; //{ trackWidth }
    public static final double wheelBase = 0; //{ wheelBase }

    public static final double maxSpeed = 0; //{ maxSpeed }

    public static double wheelCircumference = 0; //{ wheelCircumference }

    public static double driveRatio = 0.0; //{ driveRatio }

    public static double turnRatio = 0.0; //{ turnRatio }

    public static double turnCurrentLimit = 20.0;
    public static double driveCurrentLimit = 60.0;

    public static int gyroId = 0;

    //TODO: Tune this to your robot!
    public static double turnkP = 1.0;
    //IN THIS HOUSE WE DON'T USE INTEGRALS
      //TODO: Tune this to your robot!
    public static double turnkD = 0;

    //TODO: Tune this to your robot!
    public static double drivekP = 0.15;
    //TODO: Tune this to your robot!
    public static double drivekD = 0.0;

    //TODO: Replace with values from SysID!
    public static double drivekV = 1.0 / 0.0; // ({ motorFreeSpeedRps } * wheelCircumference) / driveRatio
    //TODO: Replace with values from SysID!
    public static double drivekA = 0.2;
    //TODO: Replace with values from SysID!
    public static double drivekS = 0.3;

    //TODO: Tune to your robot
    public static double maxChassisSpeed = 0.0; //({ motorFreeSpeedRps } * wheelCircumference) / driveRatio

    //TODO: Tune to your robot
    public static double maxChassisRotation = 10.0;


    public static class SwerveModuleConstants {
      public int driveMotorCanID;
      public int turnMotorCanID;
      //{ % if not encoderOverCAN % }
      public int encoderCanID;
      //{ % endif % }

      public SwerveModuleConstants(
        int driveMotorCanID,
        int turnMotorCanID//{ % if encoderOverCAN % },{ % endif % }
        //{ % if encoderOverCAN % }
        //int encoderCanID
        //{ % endif % }
      ) {
        this.driveMotorCanID = driveMotorCanID;
        this.turnMotorCanID = turnMotorCanID;
        //{ % if encoderOverCAN % }
        this.encoderCanID = encoderCanID;
        //{ % endif % }
      }
    }

    public static final SwerveModuleConstants[] swerveModuleConstants = new SwerveModuleConstants[]{
      new SwerveModuleConstants(0, 0), //{ drive_motor_0_can_id }, { turn_motor_0_can_id }{ % if encoderOverCAN % }, { encoder_0_can_id }{ % endif % }
      new SwerveModuleConstants(0, 0), //{ drive_motor_1_can_id }, { turn_motor_1_can_id }{ % if encoderOverCAN % }, { encoder_1_can_id }{ % endif % }
      new SwerveModuleConstants(0, 0), //{ drive_motor_2_can_id }, { turn_motor_2_can_id }{ % if encoderOverCAN % }, { encoder_2_can_id }{ % endif % }
      new SwerveModuleConstants(0, 0)  //{ drive_motor_3_can_id }, { turn_motor_3_can_id }{ % if encoderOverCAN % }, { encoder_3_can_id }{ % endif % }
    };
  }
}
