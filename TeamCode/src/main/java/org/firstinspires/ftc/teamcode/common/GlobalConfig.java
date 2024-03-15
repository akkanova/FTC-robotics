package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MecanumKinematics;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.VelConstraint;

import org.firstinspires.ftc.teamcode.common.hardware.MecanumDrive;
import org.firstinspires.ftc.teamcode.common.hardware.localizers.ThreeWheelLocalizer;

import java.util.Arrays;

/**
 * One stop shop for all <b>dynamically</b> configurable
 * variables that exists in our codebase.
 */
public final class GlobalConfig {
    /** Name of that component in the Control Hub's Hardware Configuration */
    public static final class HardwareBindingNames {
        public static final String imu = "imu";

        public static final String frontLeftWheelMotor  = "FrontLeftM";
        public static final String frontRightWheelMotor = "FrontRightM";
        public static final String backLeftWheelMotor   = "BackLeftM";
        public static final String backRightWheelMotor  = "BackRightM";

        public static final String deadWheelLeftEncoder = frontLeftWheelMotor;
        public static final String deadWheelRightEncoder = frontRightWheelMotor;
        public static final String deadWheelPerpendicularEncoder = backLeftWheelMotor;

        public static final String leftClawServo = "ClawLeftS";
        public static final String rightClawServo = "ClawRightS";
        public static final String clawExtenderServo = "ArmExtenderS";
        public static final String clawWristServo = "ClawWristS";
        public static final String elbowMotor = "ElbowM";

        public static final String droneLauncherHookServo = "DroneLauncherHookS";

        public static final String liftMotor = "LiftM";
    }

    /**
     * Configuration specifically for {@link MecanumDrive}.
     * Fine tune variables located here using these instructions
     * <a href="https://rr.brott.dev/docs/v1-0/tuning">
     *      Road Runner v1.0.x Tuning Documentation
     * </a>
     * */
    @Config
    public static final class MecanumDriveConfig {
        /** How many poses it will store in it's history before it starts pruning old ones..*/
        public static int maxPoseHistory = 100;
        /** Only used by FTC Dashboard to represent the size of the robot */
        public static int robotRadius = 9;

        /** Determined through
         * <a href="https://rr.brott.dev/docs/v1-0/tuning#forwardpushtest">
         *     ForwardPushTest
         * </a>
         * or manually calculated.. */
        public static double inchesPerTick = Math.PI * 1.49606 / 2048;
        /** Determined through
         * <a href="https://rr.brott.dev/docs/v1-0/tuning#lateralpushtest-mecanum--drive-encoders-only">
         *     LateralPushTest
         * </a>. */
        public static double lateralInchesPerTick = 0.002089369300709049;
        /** Determined through
         * <a href="https://rr.brott.dev/docs/v1-0/tuning#angularramplogger">
         *  AngularRampLogger
         * </a>. */
        public static double trackWidthTicks = 5194.412125707776;

        //-------------------------------------------------------------------------------
        // Feed-Forward Parameters
        //-------------------------------------------------------------------------------
        // Tuned using the Manual feed-forward tuner
        // (https://rr.brott.dev/docs/v1-0/tuning#manualfeedforwardtuner)

        /** Feed forward parameter, In ticks unit */
        public static double kS = 1.3444260693859178;
        /** Feed forward parameter, In ticks unit */
        public static double kV = 0.0008067277775175678;
        /** Feed forward parameter, In ticks unit */
        public static double kA = 0.0002;

        //-------------------------------------------------------------------------------
        // Path Profile Parameters
        //-------------------------------------------------------------------------------

        /** In inches / second */
        public static double maxWheelVelocity = 50;
        /** In inches / second^2 */
        public static double minProfileAcceleration = -30;
        /** In inches / second^2 */
        public static double maxProfileAcceleration = 50;

        //-------------------------------------------------------------------------------
        // Turn Profile Parameters
        //-------------------------------------------------------------------------------

        /** In radians / second */
        public static double maxAngularVelocity = Math.PI;
        /** In radians / second^2 */
        public static double maxAngularAcceleration = Math.PI;

        //-------------------------------------------------------------------------------
        // Path Controller Gains
        //-------------------------------------------------------------------------------
        // For fine tuning the path follower

        public static double axialGain = 0;
        public static double lateralGain = 0;
        public static double headingGain = 0;

        public static double axialVelocityGain = 0;
        public static double lateralVelocityGain = 0;
        public static double headingVelocityGain = 0;
    }

    /** Configuration specifically for {@link ThreeWheelLocalizer}*/
    @Config
    public static final class ThreeWheelLocalizerConfig {
        public static double inchesPerTick = MecanumDriveConfig.inchesPerTick;

        /** y position of the left parallel encoder (in tick units) */
        public static double leftParallelYTicks = -2760.617679648084;
        /** y position of the right parallel encoder (in tick units) */
        public static double rightParallelYTicks = 2805.7699093416577;
        /** x position of the perpendicular encoder (in tick units) */
        public static double perpendicularXTicks = -3199.431500951285;
    }

    /** Configuration specifically for the elbow motor */
    @Config
    public static final class ElbowMotorConfig {
        public static double P = 0.031;
        public static double I = 0.5;
        public static double D = 1e-5;
        public static double F = 0;

        public static double initialAngle = 44.425;

        // Assumes elbow is a 60:1 Tetrix motor with a 2:1 gear ratio.
        public static final double TICK_PER_ANGLE = 1440.0 * 2 / 360.0;
        public static final double ANGLE_PER_TICK = 1 / TICK_PER_ANGLE;
    }


    // A: We register so much tele-ops that it's kinda confusing. So we have
    // this to be able to disable specific ones, and have some clarity.

    /** Determines whether Human-Operated TeleOps should not be registered */
    public static final boolean DISABLE_OPERATED_OP_MODES = false;

    /** Determines whether Road Runner tuning TeleOPs should not be registered */
    public static final boolean DISABLE_ROAD_RUNNER_TUNING = false;

    /** Determines whether Debug TeleOps should not be registered */
    public static final boolean DISABLE_DEBUG_OP_MODES = false;

    /** Determines whether Hardware tuning op modes should not be registered */
    public static final boolean DISABLE_HARDWARE_TUNING_OP_MODES = false;
}
