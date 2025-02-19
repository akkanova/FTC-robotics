package org.firstinspires.ftc.teamcode.all_purpose;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Consumer;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//import org.firstinspires.ftc.teamcode.all_purpose.DcMotorImplEx;
//
/**
 * A specific class designed for setting up all of the robot's
 * hardware binding classes, and presiding over groups of them to
 * be able to do synchronized commands.
 */
public class HardwareManager {
    //------------------------------------------------------------------------------------------------
    // Wheels
    //------------------------------------------------------------------------------------------------
    public final DcMotorImplEx[] wheels;

    public DcMotorImplEx getFrontLeftWheel()  { return wheels[0]; }
    public DcMotorImplEx getFrontRightWheel() { return wheels[1]; }
    public DcMotorImplEx getBackLeftWheel()   { return wheels[2]; }
    public DcMotorImplEx getBackRightWheel()  { return wheels[3]; }

    public void doForAllWheels(Consumer<DcMotorImpl> callback) {
        for (DcMotorImpl wheel : wheels)
            callback.accept(wheel);
    }

    public void resetWheelEncoders() {
        doForAllWheels(this::resetDcMotorEncoder);
    }

    public int getAverageWheelCounts() {
        int total = 0;
        for (DcMotorImpl wheel : wheels)
            total += wheel.getCurrentPosition();

        return total / wheels.length;
    }

    //------------------------------------------------------------------------------------------------
    // Arm
    //------------------------------------------------------------------------------------------------
    public final Servo clawServoLeft;
    public final Servo clawServoRight;
    public final DcMotorImplEx elbowArmMotor;
    public final CRServo clawExtenderServo;


    //------------------------------------------------------------------------------------------------
    // Drone Launcher
    //------------------------------------------------------------------------------------------------
    //public final ServoImplEx droneLauncherBase;
    //public final ServoImplEx droneLauncherHook;


    //------------------------------------------------------------------------------------------------
    // Lift
    //------------------------------------------------------------------------------------------------
    public final DcMotorImplEx liftMotor;

    //------------------------------------------------------------------------------------------------
    // Sensors
    //------------------------------------------------------------------------------------------------
    public final VoltageSensor voltageSensor;
    public final IMU imu;

    /**
     * Returns a normalized robot yaw orientation in Degrees (°)
     *
     *               <- FORWARD ->
     *                     0
     * LEFT  -90           +         90 RIGHT
     *                 -180/180
     *                 BACKWARD
     */
    public double getCurrentDegreeHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }


    public HardwareManager(HardwareMap hardwareMap) {
        // Wheels
        wheels = new DcMotorImplEx[] {
                hardwareMap.get(DcMotorImplEx.class, "FrontLeftM"),
                hardwareMap.get(DcMotorImplEx.class, "FrontRightM"),
                hardwareMap.get(DcMotorImplEx.class, "BackLeftM"),
                hardwareMap.get(DcMotorImplEx.class, "BackRightM")
        };

        getFrontLeftWheel().setDirection(DcMotorSimple.Direction.REVERSE);
        getFrontRightWheel().setDirection(DcMotorSimple.Direction.FORWARD);
        getBackLeftWheel().setDirection(DcMotorSimple.Direction.REVERSE);
        getBackRightWheel().setDirection(DcMotorSimple.Direction.FORWARD);

        doForAllWheels(wheel ->
                wheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE));

        // Arm
        clawServoLeft = hardwareMap.get(Servo.class, "ClawLeftS");
        clawServoRight = hardwareMap.get(Servo.class, "ClawRightS");
        elbowArmMotor = hardwareMap.get(DcMotorImplEx.class, "ElbowArmM");
        clawExtenderServo = hardwareMap.get(CRServo.class, "clawExtenderS");

        clawServoLeft.setDirection(Servo.Direction.REVERSE);
        clawServoRight.setDirection(Servo.Direction.FORWARD);

        elbowArmMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        elbowArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        clawExtenderServo.setDirection(DcMotorSimple.Direction.REVERSE);

        // Drone Launcher
        //droneLauncherBase = hardwareMap.get(ServoImplEx.class, "LauncherBaseS");
        //droneLauncherHook = hardwareMap.get(ServoImplEx.class, "LauncherHookS");

        //droneLauncherBase.setDirection(Servo.Direction.FORWARD);
        //droneLauncherHook.setDirection(Servo.Direction.REVERSE);

        // Lift
        liftMotor = hardwareMap.get(DcMotorImplEx.class, "LiftM");

        // Sensors
        voltageSensor = hardwareMap.voltageSensor.iterator().next();
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                )
        );

        imu.initialize(parameters);
        imu.resetYaw();
    }

    public void resetDcMotorEncoder(DcMotorImpl motor) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
