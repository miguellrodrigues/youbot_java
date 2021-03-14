package com.miguel.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Numbers {

    public static double randomDouble(double min, double max) {
        return (new Random().nextDouble() * (max - min)) + min;
    }

    public static int randomInt(int min, int max) {
        return (int) randomDouble(min, max);
    }

    public static double sigmoid(double d) {
        return 1 / (1 + Math.exp(-d));
    }

    public static double sigmoidDerivative(double d) {
        return d * (1 - d);
    }

    public static double fastSigmoid(double d) {
        return (d / (1 + Math.abs(d)));
    }

    public static double fastSigmoidDerivative(double d) {
        return (1 / (Math.pow(1 + Math.abs(d), 2.0)));
    }

    public static double normalize(double r) { return Math.atan2(Math.sin(r), Math.cos(r)); }
}
