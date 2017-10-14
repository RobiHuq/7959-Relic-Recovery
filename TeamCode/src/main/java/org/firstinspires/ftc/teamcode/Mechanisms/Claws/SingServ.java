package org.firstinspires.ftc.teamcode.Mechanisms.Claws;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Mechanisms.Claws.ClawInterface;

/**
 * Created by Robi on 10/14/2017.
 */

public class SingServ implements ClawInterface {
    Servo servo;
    double open;
    double close;
    SingServ(HardwareMap hw, String name, double open, double close){
        servo = hw.servo.get(name);
        this.open = open;
        this.close = close;
    }
    public void close(){
        servo.setPosition(close);
    }
    public void open(){
        servo.setPosition(open);
    }
    public double getPos(){
        return servo.getPosition();
    }
}
