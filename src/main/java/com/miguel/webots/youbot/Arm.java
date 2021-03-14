package com.miguel.webots.youbot;

import com.cyberbotics.webots.controller.Motor;
import com.miguel.webots.Controller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Arm {

    private Controller controller;

    private List<Motor> elements;

    public enum Height {
        ARM_FRONT_FLOOR(0),
        ARM_FRONT_PLATE(1),
        ARM_HANOI_PREPARE(2),
        ARM_FRONT_CARDBOARD_BOX(3),
        ARM_RESET(4),
        ARM_BACK_PLATE_HIGH(5),
        ARM_BACK_PLATE_LOW(6),
        ARM_FRONT_TABLE_BOX(7),
        ARM_PREPARE_LAUNCH(8),
        ARM_LAUNCH(9),
        ARM_MAX_HEIGHT(10);

        public final int i;

        Height(int i){
            this.i = i;
        }
    }

    public enum Orientation {
        ARM_BACK_LEFT(0),
        ARM_LEFT(1),
        ARM_FRONT_LEFT(2),
        ARM_FRONT(3),
        ARM_FRONT_RIGHT(4),
        ARM_RIGHT(5),
        ARM_BACK_RIGHT(6),
        ARM_MAX_SIDE(7);

        public final int i;

        Orientation(int i) {
            this.i = i;
        }
    }

    public enum Arms {
        ARM1(0),
        ARM2(1),
        ARM3(2),
        ARM4(3),
        ARM5(4);

        public final int i;

        Arms(int i) {
            this.i = i;
        }
    }

    public Arm(Controller controller) {
        this.elements = new ArrayList<>();

        this.controller = controller;

        for (int i = 1; i < 6; ++i) {
            this.elements.add((Motor) controller.getDeviceByName("arm" + i));
        }

        controller.setMotorVelocity(Arms.ARM2.i, 3.1415f);
    }

    void setArmsPosition(@NotNull List<Arms> arms, double[] positions) {
        for (int i = 0; i < arms.size(); ++i) {
            this.elements.get(i).setPosition(positions[i]);
        }
    }

    void change(ArrayList<Double> positions) {
        double[] pos = {positions.get(0), positions.get(1), positions.get(2)};

        this.setArmsPosition(Collections.singletonList(Arms.ARM1), pos);
    }

    void reset() {
        double[] positions = {.0, 1.57, -2.635, 1.78, .0};

        this.setArmsPosition(List.of(Arms.ARM1, Arms.ARM2, Arms.ARM3, Arms.ARM4, Arms.ARM5), positions);
    }

    void setSubRotation(Arms arm, double r) {
        this.elements.get(arm.i).setPosition(r);
    }
}
