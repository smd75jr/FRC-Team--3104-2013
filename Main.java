/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;

public class Main extends IterativeRobot {

    Joystick leftStick = new Joystick(1);
    Joystick rightStick = new Joystick(2);
    Talon leftDrive = new Talon(1);
    Talon rightDrive = new Talon(2);
    Talon launchWheel1 = new Talon(3);
    Jaguar kicker = new Jaguar(4);
    Talon climbSim = new Talon(5);
    /*implement physically!*/Jaguar extender = new Jaguar(7);/*implement physically!*/
    /*implement physically!*/Jaguar lifter = new Jaguar(8);/*implement physically!*/
    Jaguar elevateTalon = new Jaguar(6);
    DigitalInput limitSwitchFront = new DigitalInput(1);
    DigitalInput limitSwitchBack = new DigitalInput(2);
    //Servo elevation = new Servo(7);
    RobotDrive drive = new RobotDrive(leftDrive, rightDrive);
    Print p = new Print();
    AxisCamera camera;
    String o = "[OLLIE]: ";
    double rotation = 0.00;
    double elevate = 0.00;
    double throttleVal = 0.00;
    double throttleVal2 = 0.00;
    boolean frontAllow = true;
    boolean backAllow = true;

    public void robotInit() {
        p.p(1, o + "Hello user :-)");
        camera = AxisCamera.getInstance();
        //cameras are not normal objects, you can only have one, so you
        //can't just do AxisCamera camera = new AxisCamera

        camera.writeResolution(AxisCamera.ResolutionT.k320x240);
        camera.writeBrightness(0);
        p.p(2, o + "Check the camera feed");
        getWatchdog().setEnabled(false);
        p.p(3, o + "Watchdog enabled");
        //according to the world, we need safety...
        //I disagree, I shall devide by zero and spill coffee in
        //data centers all I want.
    }

    public void autonomousPeriodic() {
        /*GET RID OF THIS BEFORE COMPETITION!!!*/
        launchWheel1.set(0.80);
        while (true && isAutonomous() && isEnabled()) {
            update();
            Timer.delay(2.00);
            kicker.set(-1);
            Timer.delay(0.80);
            kicker.set(0);
            //write this later.
        }
        launchWheel1.set(0.00); //safety off
    }

    public void teleopPeriodic() {
        p.clear(); //clear GUI
        p.p(1, o + "Teleop Initiated"); //tell the user its on
        while (true && isOperatorControl() && isEnabled()) {
            update();
            launcher();
            climb();
            //cam(); //we are not using a camera axis frame, leaving hte code for next year
            extend();
            kick();
            elevator();
            lift();
            drive.arcadeDrive(leftStick);
            Timer.delay(0.005);
            //the timer is so the code doesnt over calculate.
            //think of it like look sensitivity in a video game.
        }
    }

    public void testPeriodic() {
        //we dont run test code...
    }

    public void update() {
        DriverStationLCD.getInstance().updateLCD();
        //this function just keeps the code clean
        //(which is another word for being lazy and not wanting to type)
    }

    public void lift() {
        if (leftStick.getRawButton(11) == true) {//if the up button is pressed
            lifter.set(1.0);//run the motor at half speed
            p.p(5, "Lifter: down");//and tell the user hte motor is running
        }
        else if(leftStick.getRawButton(10) == true) {//if hte down button is pressed
            lifter.set(-1.0);//run the motor at half speed backwards
            p.p(5, "Lifter: up");//and tell the user that its running
        }
        else{lifter.set(0);p.p(5, "Lifter: off");}//if nothing is pressed, tell the user its off
    }

    public void extend() {
        if (rightStick.getRawButton(6) == true) {//if the up button is pressed
            extender.set(1.0);//run the motor at half speed
            p.p(3, "Extender: up");//tell the user that its running
        }
        else if(rightStick.getRawButton(7) == true) {//if the down button is pressed
            extender.set(-1.0);//run the motor backwards at half speed
            p.p(3, "Extender: down");//tell the user that its running backwards
        }
        else{extender.set(0);p.p(3, "Extender: off");} //if nothing's pressed, turn it off
    }

    public void launcher() {
        if (leftStick.getRawButton(3) == true) {  //if the top button is pressed
            if (throttleVal <= 1.0000000) { //and is less than or equal to 1
                throttleVal = throttleVal + 0.0100000; //add 0.01 to the throttle
            } else {
            }
        } else if (leftStick.getRawButton(2) == true) { //if the bottom button is pressed
            if (throttleVal >= -1.0000000) {//and it is lmore than or equal to -1
                throttleVal = throttleVal - 0.0100000; //decrease my 0.01 to the throttle
            } else {
            }
        } else if (leftStick.getRawButton(4) == true) {//if they press the trigger
            throttleVal = 0.0000000; //set the throttle to 0/nutral
        } else {
        }
        p.p(1, "Launcher: " + throttleVal); //print out the throttle value
        launchWheel1.set(throttleVal);//set the speed in the wheel
    }

    public void climb() {
        if (rightStick.getRawButton(3) == true) {  //if the top button is pressed
            if (throttleVal2 <= 1.0000000) { //and is less than or equal to 1
                throttleVal2 = throttleVal2 + 0.0100000; //add 0.01 to the throttle
            } else {
            }
        } else if (rightStick.getRawButton(2) == true) { //if the bottom button is pressed
            if (throttleVal2 >= -1.0000000) {//and it is lmore than or equal to -1
                throttleVal2 = throttleVal2 - 0.0100000; //decrease my 0.01 to the throttle
            } else {
            }
        } else if (rightStick.getRawButton(4) == true) {//if they press the trigger
            throttleVal2 = 0.0000000; //set the throttle to 0/nutral
        } else {
        }
        p.p(2, "ClimberWinch: "+throttleVal2); //print out the throttle value
        climbSim.set(throttleVal2);//set the speed in the wheel
    }

   /* public void cam() {
        rotation = rightStick.getX(GenericHID.Hand.kLeft);
        elevate = rightStick.getY(GenericHID.Hand.kLeft);
        p.p(3, "Elevation Servo: " + elevate);
        p.p(4, "Rotation Servo: " + rotation);
        //rotate.set(1.0);
        //elevation.set(-1.0);
    }*/

    public void kick(){
        if(leftStick.getRawButton(1) == true){//if the left trigger is pressed
            kicker.set(-1);//kick the kicker
        }
        else{//if its not
            kicker.set(0);//stop it
        }
    }

    public void elevator(){
        if(limitSwitchFront.get() == false){//if the layer is overextended forward
            frontAllow = false; //dont let the user move forward (up)
            backAllow = true; //and allow them back (down)
        }
        else if(limitSwitchBack.get() == false){//if hte layer is overextended backward
            backAllow = false;//dont let hte user move back (down)
            frontAllow = true;//and allow them to go forward (up)
        }
        else{//if neither switches are tripped
            frontAllow = true;//let it move both ways
            backAllow = true;//let it move both ways
        }
        if(leftStick.getRawButton(6) == true && backAllow){//if the up button is pressed and backAllow is true
            elevateTalon.set(1.0);//let the motor move
            p.p(4, "Elevator: on");//print to the display that its moving
        }
        else if(leftStick.getRawButton(7) == true && frontAllow){//if they down button is pressed and frontAllow is true
            elevateTalon.set(-1.0);//let the motor move backwards
            p.p(4, "Elevator: reverse");//print to the display that its moving backwards
        }
        else if(backAllow == false){//if backAllow is false
            elevateTalon.set(0.0);//stop the motor
            p.p(4, "Elevator: Over");//tell the user that the layer is overextended
        }
        else if(frontAllow == false){//if the frontAllow is false
            elevateTalon.set(0.0);//stop the motor
            p.p(4, "Elevator: Over");//tell the user that hte layer is overextended
        }
        else{//if its anything else (aka button not pressed)
            elevateTalon.set(0.0); //stop the motor
            p.p(4, "Elevator: off");//and tell the user that the motor is off
        }
    }
}
