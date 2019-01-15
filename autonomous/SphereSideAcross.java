package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class SphereSideAcross extends BaseAutonomous {
    int turn360 = 14000;
    int start;

    public void runOpMode() {
        initialize();

        holdMarker();

        waitForStart();

        start = winchMotor.getCurrentPosition();

        // Lower bot
        setHeight(start + 15500, 1);
        while (winchMotor.isBusy() && !isStopRequested()) {}

        // Move to minerals
        moveEncoderVertical(800, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        turnBotEncoders(turn360 / -8, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(-3500, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        turnBotEncoders(turn360 / 8, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        int startingEncoder = frontLeft.getCurrentPosition();
        telemetry.addData("Starting Pos", frontLeft.getCurrentPosition());
        telemetry.update();

        // Visual (Mineral recognition)

        moveEncoderVertical(5500, 1);
        while (frontLeft.isBusy()) {
            if (seesGold()) {
                telemetry.addData("End", frontLeft.getCurrentPosition());
                telemetry.update();
                break;
            }
        }

        int endEncoder = frontLeft.getCurrentPosition();

        // Keeps track of where the mineral is
        int dir = 0;

        if (endEncoder - startingEncoder >= 1650) {
            telemetry.addLine("Left");
            telemetry.update();

            dir = 1;
            moveEncoderVertical(900, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {}
        } else if (endEncoder - startingEncoder < 1250) {
            telemetry.addLine("Right");
            telemetry.update();

            dir = 3;
        } else {
            moveEncoderVertical(1000, 1);
            while (frontLeft.isBusy() && !isStopRequested()) {}

            dir = 2;
            telemetry.addLine("Center");
            telemetry.update();
        }
        moveEncoderVertical(-1000, 1);
        while (!frontLeft.isBusy() && !isStopRequested()) {}

        // Crash into wall
        turnBotEncoders(-1 * turn360 / 4, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(1000, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        turnBotEncoders(turn360 / 4, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(5000, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        // Go to depot and deposit marker
        turnBotEncoders(turn360 / -8, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        moveEncoderVertical(6000, 1);
        while (frontLeft.isBusy() && !isStopRequested()) {}

        dropMarker();

        // Go back to crater
        moveEncoderVertical(-5000, 1); {}
        while (frontLeft.isBusy() && !isStopRequested()) {}
    }
}