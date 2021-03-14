package com.miguel.webots.youbot;

import com.cyberbotics.webots.controller.Motor;
import com.miguel.webots.Controller;

import java.util.ArrayList;
import java.util.List;

public class Base {

    private Controller controller;

    private List<Motor> wheels;

    public Base(Controller controller) {
        this.controller = controller;

        this.wheels = new ArrayList<>();

        for (int i = 1; i < 5; ++i) {
            this.wheels.add((Motor) controller.getDeviceByName("wheel" + i));
        }

        for (Motor wheel : this.wheels) {
            wheel.setPosition(Double.POSITIVE_INFINITY);
            wheel.setVelocity(.0);
        }
    }

    void setWheelSpeed(int index, double velocity) {
        this.wheels.get(index).setVelocity(velocity);
    }

    void setWheelsSpeed(double[] speeds) {
        for (int i = 0; i < speeds.length; ++i) {
            this.setWheelSpeed(i, speeds[i]);
        }
    }
}
