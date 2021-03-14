package com.miguel.webots.youbot;

import com.cyberbotics.webots.controller.Motor;
import com.miguel.webots.Controller;

import java.util.ArrayList;
import java.util.List;

public class Gripper {

    private int LEFT = 0;
    private int RIGHT = 1;

    private double MIN_POS = .0;
    private double MAX_POS = .025;

    private Controller controller;

    private List<Motor> fingers;

    public Gripper(Controller controller) {
        this.controller = controller;

        this.fingers = new ArrayList<>();

        this.fingers.add((Motor) controller.getDeviceByName("finger1"));
        this.fingers.add((Motor) controller.getDeviceByName("finger2"));

        this.fingers.get(LEFT).setVelocity(.06);
        this.fingers.get(RIGHT).setVelocity(.06);
    }

    void grip() {
        this.fingers.get(LEFT).setPosition(MIN_POS);
        this.fingers.get(RIGHT).setPosition(MIN_POS);
    }

    void release() {
        this.fingers.get(LEFT).setPosition(MAX_POS);
        this.fingers.get(RIGHT).setPosition(MAX_POS);
    }
}
