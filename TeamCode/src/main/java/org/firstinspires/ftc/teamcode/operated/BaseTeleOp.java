package org.firstinspires.ftc.teamcode.operated;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.HardwareManager;
import org.firstinspires.ftc.teamcode.common.hardware.GamepadEx;
import org.firstinspires.ftc.teamcode.common.misc.Lazy;
import org.firstinspires.ftc.teamcode.common.misc.Misc;
import org.firstinspires.ftc.teamcode.common.road_runner.drive.MecanumDriveImpl;

/** Base class implemented by all Tele-Op */
public abstract class BaseTeleOp extends OpMode {
    protected HardwareManager hardwareManager;
    protected MecanumDriveImpl drive;

    protected  Lazy<GamepadEx> lazyGamepadEx1;
    protected  Lazy<GamepadEx> lazyGamepadEx2;

    //-----------------------------------------------------------------------------------
    // Initialization
    //-----------------------------------------------------------------------------------

    /** Inherited class should not override this method */
    @Override
    public void init() {
        hardwareManager = new HardwareManager(hardwareMap);
        drive = new MecanumDriveImpl(hardwareMap);

        lazyGamepadEx1 = new Lazy<>(() -> new GamepadEx(gamepad1));
        lazyGamepadEx2 = new Lazy<>(() -> new GamepadEx(gamepad2));

        postInit();
    }

    /** Called by init() last. */
    protected abstract void postInit();

    //-----------------------------------------------------------------------------------
    // Op-mode loop
    //-----------------------------------------------------------------------------------

    /** Inherited class should not override this loop */
    @Override
    public void loop() {
        if (lazyGamepadEx1.isInitialized())
            lazyGamepadEx1.get().sync();
        if (lazyGamepadEx2.isInitialized())
            lazyGamepadEx2.get().sync();

        drive.update();
        drive.setWeightedDrivePower(getDrivePowers());

        hardwareManager.elbowMotor.setPower(getElbowPower());
        runPeriodic();
    }

    /**
     * @return the initial position of this op-mode,
     * called by init.
     * */
    protected Pose2d getInitialPose() {
        return new Pose2d(0, 0, 0);
    }

    /**
     * @return target heading vector used by {@link MecanumDriveImpl}.
     * By default, it uses gamepad-1 left stick side for strafe-mode and
     * right stick for tank-mode.
     * */
    protected Pose2d getDrivePowers() {
        // If the left one doesn't have any forward motion,
        // use the right one instead..
        double forwardVelocity = gamepad1.left_stick_y != 0
            ? gamepad1.left_stick_y
            : gamepad1.right_stick_y;

        return new Pose2d(
            -Misc.easeWithSquare(forwardVelocity),
            -Misc.easeWithSquare(gamepad1.left_stick_x)
            -Misc.easeWithSquare(gamepad1.right_stick_x)
        );
    }

    /** @return the target position of the arm */
    protected abstract double getElbowPower();

    /** called by loop() after it runs. */
    protected abstract void  runPeriodic();

}
