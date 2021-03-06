package org.firstinspires.ftc.teamcode.WheelControl;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.ExpansionHubMotorControllerPositionParams;
import com.qualcomm.robotcore.hardware.configuration.ModernRoboticsMotorControllerParams;

import org.firstinspires.ftc.teamcode.UtilitiesandMic.RobotUtilities;

/**
 * Created by Robi on 10/9/2017.
 */
public class BasicWheels implements Wheels {
    /**
     * This class is the most basic function of drive.
     * Setting power here is not based off of anything.
     * This class will most likely be overwritten in a subclass
     * but can also be used as a last resort.
     */

    
    /***
     * Set the directions in this class. Will pass on to subclasses
     */
    protected final DcMotor.Direction frontLeftDir = DcMotorSimple.Direction.REVERSE;
    protected final DcMotor.Direction frontRightDir = DcMotorSimple.Direction.REVERSE;
    protected final DcMotor.Direction backLeftDir = DcMotorSimple.Direction.REVERSE;
    protected final DcMotor.Direction backRightDir = DcMotorSimple.Direction.REVERSE;


    /**
     * Angle translations for the wheel input and outputs
     * Driving methods translates a simple input of x Velocity and y Velocity
     * and then the program with translate them to work with the omni wheels
     * by transforming the vector 45 degrees
     */



    /**
     * HardwareMap Names
     */


    public DcMotor[][] MotorWheels = new DcMotor[2][2];
    protected double powerFactor = 1;
    protected double turnFactor = 1;
    private HardwareMap hmap;

    /**
     * Default Constructor, won't see much use.
     */
    protected BasicWheels(){

    }

    /**
     * Constructor sets up the direction, which motors to use, Zero power behavior, and Hardwaremap
    */
    public BasicWheels(HardwareMap HwMap) {
        this.hmap = HwMap;
        MotorWheels[0][0] = hmap.dcMotor.get("Back Left");
        MotorWheels[1][0] = hmap.dcMotor.get("Back Right");
        MotorWheels[0][1] = hmap.dcMotor.get("Front Left");
        MotorWheels[1][1] = hmap.dcMotor.get("Front Right");

        setDirection();
        setZeroPowerBehavior();
    }
    /**
     * Sets the DCMotor's Zero Power behavior to brake
     */
    protected void setZeroPowerBehavior(){
        for(DcMotor[] m :MotorWheels){
            for(DcMotor w: m){
                w.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }
    }


    /**
     * Sets up encoders
     * Will be not be overwritten in subclasses
     */
    protected void setEncoders(DcMotor.RunMode mode){


        /**
         * If the encoders are in use, this will reset the encoders prior to encoder usage
         */
        if(mode == DcMotor.RunMode.RUN_USING_ENCODER){
            for(int i = 0;i < 2;i++){
                for(int j = 0; j < 2 ;j++){
                    MotorWheels[i][j].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                }
            }
        }


        /**
         * Sets the motor encoders behavior
         */
        for(DcMotor[] motorar: MotorWheels)
            for (DcMotor motor: motorar)
                motor.setMode(mode);

    }

    /**
     * sets direction based off of the previously defined directions
     * Will not be overwritten in subclasses
     */
    protected void setDirection(){
        MotorWheels[0][0].setDirection(backLeftDir);
        MotorWheels[0][1].setDirection(backRightDir);
        MotorWheels[1][0].setDirection(frontLeftDir);
        MotorWheels[1][1].setDirection(frontRightDir);
    }


    /**movebyCart()
     * Powers the motors to match x and y velocity in a Cartesian plane
     * Transfers vector to a Polar point, translates the angle, then transfers it
     * back to add power to the motors
     * WheelsEncoded class will inherite this class. The rest will overwrite it.
     *
     *
     */

    public void movebyCart(double Velvector[], double AngularVelocity){
        AngularVelocity *= turnFactor;
        double x = Velvector[0];
        double y = Velvector[1];
        //double mag = Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1]);
      //  double theta = Math.atan2(vector[1], vector[0]);

        MotorWheels[0][0].setPower(powerFactor*(-y - x +AngularVelocity));
        MotorWheels[1][1].setPower(powerFactor*(y + x +AngularVelocity));
        MotorWheels[0][1].setPower(powerFactor*(-y + x +AngularVelocity));
        MotorWheels[1][0].setPower(powerFactor*(y - x +AngularVelocity));
    }



    //There is no need for an overrideDrive in this class, so it runs the movebyCart method
    public void overrideDrive(double[] Velvector, double AngularVelocity) {
        movebyCart(Velvector, AngularVelocity);
    }

    @Deprecated
    public void movebyPolar(double Velvector[], double AngularVelocity){
        double inputVector[];
        AngularVelocity *= turnFactor;
        Velvector[1] += angletranslationR;
        inputVector = RobotUtilities.PolartoCart(Velvector);


        for(int i = 0; i < inputVector.length; i++){
            inputVector[i]*= powerFactor;
        }
        MotorWheels[0][0].setPower(-inputVector[0] + AngularVelocity);
        //MotorWheels[1][1].setPower(-inputVector[0] + AngularVelocity);
        //MotorWheels[0][1].setPower(-inputVector[1] + AngularVelocity);
        //MotorWheels[1][0].setPower(inputVector[1] + AngularVelocity);
    }

    /**
     * Directly puts power into motor, testing use only
     */
    private void directPower(int x, int y, double power){
        if((x == 0 || x == 1) && (y == 0 || y == 1)){
            MotorWheels[x][y].setPower(power);
        }
    }


    public void setPowerRatio(double ratio) {
        powerFactor = ratio;
    }


    public void setTurnRatio(double ratio) {
        turnFactor = ratio;
    }

    /**
     * if this ever needs to be addressed as a string, this method will be there for it.
     //May be used to added drive type to telemetry, and will be overwritten in subclasses
     */
    public String toString() {
        return "Basic drive";
    }
}
