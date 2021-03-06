package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotMain;

/**
 * Created by Robi on 10/12/2017.
 */

public class ColorDistanceSensor {
    final int RThreshold = 25;// Test and change later
    final int BThreshold = 16;
    public ColorSensor colorSensor;
    public DistanceSensor OptDistance;
    HardwareMap hmap;



    /**
     * REMINDER:
     * Name must match optical distance colorSensor.
     *
     */
    public ColorDistanceSensor(HardwareMap hwmap, String name){
        colorSensor = hwmap.colorSensor.get(name);
        OptDistance = hwmap.get(DistanceSensor.class, name);
    }


    public boolean isRed(){
        if(colorSensor.red() > colorSensor.blue())
            return true;
        else return false;
    }
    public boolean isBlue(){
        if(colorSensor.blue() >= colorSensor.red())
            return true;
        else return false;
    }

    public double getDistance(){
        return OptDistance.getDistance(RobotMain.distanceUnit);
    }

}
