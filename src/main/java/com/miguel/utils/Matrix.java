package com.miguel.utils;

import org.jetbrains.annotations.NotNull;

public class Matrix {

    private int rows;
    private int cols;

    private double data[][];

    public Matrix(int rows, int cols, boolean isRandom) {
        this.rows = rows;
        this.cols = cols;

        this.data = new double[rows][cols];

        if (isRandom) {
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    this.data[i][j] = .0;
                }
            }
        } else {
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    this.data[i][j] = Numbers.randomDouble(-.501, .501);
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setValue(int row, int col, double value) {
        this.data[row][col] = value;
    }

    public double getValue(int row, int col) {
        return this.data[row][col];
    }

    public Matrix transpose() {
        Matrix mat = new Matrix(this.cols, this.rows, false);

        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                mat.setValue(j, i, this.getValue(i, j));
            }
        }

        return mat;
    }

    public Matrix hadamard(Matrix other) {
        Matrix mat = new Matrix(this.rows, this.cols, false);

        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                mat.setValue(i, j, this.getValue(i, j) * other.getValue(i, j));
            }
        }

        return mat;
    }

    public Matrix multiply(@NotNull Matrix other) {
        Matrix mat = new Matrix(this.rows, other.cols, false);

        double aux;

        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < other.cols; ++j) {
                aux = .0;

                for (int k = 0; k < other.rows; ++k) {
                    aux += this.getValue(i, k) * other.getValue(k, j);
                }

                mat.setValue(i, j, aux);
            }
        }

        return mat;
    }

    public void assignArray(double[] array) {
        if (this.rows * this.cols != array.length) {
            throw new Error("Assign array bad arguments");
        }

        for (int i = 0, count = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                this.setValue(i, j, array[count++]);
            }
        }
    }

    public void add(Matrix other) {
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                this.setValue(i, j, this.getValue(i, j) + other.getValue(i, j));
            }
        }
    }

    public void subtract(Matrix other) {
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                this.setValue(i, j, this.getValue(i, j) - other.getValue(i, j));
            }
        }
    }

    public void scalar(double x) {
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                this.setValue(i, j, this.getValue(i, j) * x);
            }
        }
    }
}
