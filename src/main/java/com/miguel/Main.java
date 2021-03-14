package com.miguel;

import com.cyberbotics.webots.controller.Robot;

public class Main {

    public static void main(String[] args) {
        Robot robot = new Robot();

        while (robot.step(14) != -1) {

        }

        robot.delete();
    }
}
