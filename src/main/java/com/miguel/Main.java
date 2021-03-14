package com.miguel;

import com.miguel.webots.Controller;
import com.miguel.webots.youbot.YouBot;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller(14, true);
        YouBot youBot = new YouBot(controller);

        while (controller.step() != -1) {
            System.out.println(youBot.getRotationAngle());
        }

        controller.getSupervisor().delete();
    }
}
