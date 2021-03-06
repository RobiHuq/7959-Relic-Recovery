package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Robi on 10/27/2017.
 *
 * Program to pull the Chassis Pinions back outside of the game for convenience
 */
@TeleOp(name = "Reverse Pinions")
public class ReversePinions extends LinearOpMode {
    @Override
    public void runOpMode(){

        //Initialize Servos
        CRServo pinion1 = hardwareMap.crservo.get("Right Pinion");
        CRServo pinion2 = hardwareMap.crservo.get("Left Pinion");
        pinion2.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();

        //Pull them back
        while (opModeIsActive()){
            pinion1.setPower(-1);
            pinion2.setPower(-1);
        }
    }
}
