package com.miguel.utils;

import org.jetbrains.annotations.NotNull;

public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(@NotNull double[] values) {
        new Vector(values[0], values[1], values[2]);
    }

    void add(@NotNull Vector other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    void subtract(@NotNull Vector other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    void multiply(@NotNull Vector other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
    }

    void divide(@NotNull Vector other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
    }

    double lengthSquared() {
        return (Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0) + Math.pow(this.z, 2.0));
    }

    double length() {
        return Math.sqrt(this.lengthSquared());
    }

    double dot(Vector other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    double angle(Vector other) {
        double d = this.dot(other) / (this.length() * other.length());
        return Math.acos(d);
    }

    double diffAngle(@NotNull Vector other) {
        return Math.atan2(other.z - this.z, other.x - this.x);
    }

    double distance(@NotNull Vector other) {
        return Math.hypot(this.x - other.x, this.z - other.z);
    }

    double distanceSquared(Vector other) {
        return Math.pow(this.distance(other), 2.0);
    }

    void scalar(double x) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }

    Vector midPoint(@NotNull Vector other) {
        double x = (this.x + other.x) / 2.0;
        double y = (this.y + other.y) / 2.0;
        double z = (this.z + other.z) / 2.0;

        return new Vector(x, y, z);
    }

    Vector crossProduct(Vector other) {
        double x = this.y * other.z - other.y * this.z;
        double y = this.z * other.x - other.z * this.x;
        double z = this.x - other.y - other.x * this.y;

        return new Vector(x, y, z);
    }

    Vector normalize() {
        double length = this.length();

        this.x /= length;
        this.y /= length;
        this.z /= length;

        return this;
    }

    void zero() {
        this.x = .0;
        this.y = .0;
        this.z = .0;
    }
}
