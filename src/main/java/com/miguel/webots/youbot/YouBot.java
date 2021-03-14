package com.miguel.webots.youbot;

import com.miguel.utils.Angle;
import com.miguel.utils.Matrix;
import com.miguel.utils.Vector;
import com.miguel.webots.Controller;

import java.util.List;

public class YouBot {

    private Controller controller;

    private Arm arm;
    private Base base;
    private Gripper gripper;
    private Angle angle;
    private String def;

    public YouBot(Controller controller) {
        this.controller = controller;

        this.arm = new Arm(controller);
        this.base = new Base(controller);
        this.gripper = new Gripper(controller);
        this.angle = new Angle();

        this.def = "youBot";
    }

    public void passiveWait(double seconds) {
        double startTime = this.controller.getSupervisor().getTime();

        while (startTime + seconds > this.controller.getSupervisor().getTime()) {
            this.controller.step();
        }
    }

    public double getRotationAngle() {
        Matrix rotation = new Matrix(3, 3, false);

        rotation.assignArray(controller.getObjectOrientation(this.def));

        return this.angle.calculateAngle(rotation);
    }

    public Vector getPosition() {
        return new Vector(controller.getObjectPosition(this.def));
    }

    public void setArmsPosition(List<Arm.Arms> arms, double[] positions) {
        this.arm.setArmsPosition(arms, positions);
    }

    public void armReset() {
        this.arm.reset();
    }

    public void grip() {
        this.gripper.grip();
    }

    public void release() {
        this.gripper.release();
    }

    public void setWheelsSpeed(double[] speeds) {
        this.base.setWheelsSpeed(speeds);
    }

    public void setGripperOrientation(double r) {
        this.arm.setSubRotation(Arm.Arms.ARM5, r);
    }
}
