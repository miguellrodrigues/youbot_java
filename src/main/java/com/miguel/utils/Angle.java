package com.miguel.utils;

public class Angle {

    private Matrix rotation_x_matrix;

    public Angle() {
        this.rotation_x_matrix = new Matrix(3, 3, false);

        double phi = -(Math.PI / 2);
        double[] rotation_x = {1, 0, 0, 0, Math.cos(phi), -Math.sin(phi), 0, Math.sin(phi), Math.cos(phi)};

        this.rotation_x_matrix.assignArray(rotation_x);

        this.rotation_x_matrix = this.rotation_x_matrix.transpose();
    }

    public double calculateAngle(Matrix rotationMatrix) {
        Matrix r = this.rotation_x_matrix.multiply(rotationMatrix);

        double angle = Math.atan2(r.getValue(1, 0), r.getValue(0, 0));;

        r.clear();

        r = null;

        return angle;
    }
}
