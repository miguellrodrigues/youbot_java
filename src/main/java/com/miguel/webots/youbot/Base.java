package com.miguel.webots.youbot;

import com.cyberbotics.webots.controller.Motor;
import com.miguel.webots.Controller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Base {

    private Controller controller;

    private List<Motor> wheels;

    public Base(Controller controller) {
        this.controller = controller;

        this.wheels = new ArrayList<>();

        for (int i = 1; i < 5; ++i) {
            this.wheels.add((Motor) this.controller.getDeviceByName("wheel" + i));
        }

        for (Motor wheel : this.wheels) {
            wheel.setPosition(Double.POSITIVE_INFINITY);
            wheel.setVelocity(.0);
        }
    }

    public void setWheelSpeed(int index, double velocity) {
        this.wheels.get(index).setVelocity(velocity);
    }

    public void setWheelsSpeed(@NotNull double[] speeds) {
        for (int i = 0; i < speeds.length; ++i) {
            this.setWheelSpeed(i, speeds[i]);
        }
    }
}
