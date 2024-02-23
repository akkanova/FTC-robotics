package org.firstinspires.ftc.teamcode.tests.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.DriveView;
import com.acmerobotics.roadrunner.ftc.DriveViewFactory;
import com.acmerobotics.roadrunner.ftc.TuningKt;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Similar to LateralPushTest, but determines the test through ticks without
 * having to manually push it
 * */
public class LateralDriveTest extends LinearOpMode {
    private final DriveViewFactory factory;
    public LateralDriveTest(DriveViewFactory factory) {
        this.factory = factory;
    }

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry,
                FtcDashboard.getInstance().getTelemetry());

        DriveView driveView = this.factory.make(hardwareMap);
        waitForStart();

        double initialLateralTicks = TuningKt.lateralSum(driveView);
        while (opModeIsActive()) {
            driveView.setDrivePowers(
                new PoseVelocity2d(new Vector2d(0, gamepad1.left_stick_x * -0.25), 0)
            );

            telemetry.addData("ticks traveled", TuningKt.lateralSum(driveView) - initialLateralTicks);
            telemetry.update();
        }
    }
}
