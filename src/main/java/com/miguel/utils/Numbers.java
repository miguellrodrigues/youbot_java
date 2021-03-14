package com.miguel.utils;

public class Numbers {

    public static double randomDouble(double min, double max) {
        return (Math.random() * ((max - min) + 1)) + min;
    }

    public static int randomInt(int min, int max) {
        return (int) randomDouble(min, max);
    }
}
