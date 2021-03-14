package com.miguel.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private final int rows;
    private final int cols;

//    private double data[][];

    private final List<List<Double>> data;

    public Matrix(int rows, int cols, boolean isRandom) {
        this.rows = rows;
        this.cols = cols;

        this.data = new ArrayList<>();

//        this.data = new double[rows][cols];

        if (isRandom) {
            for (int i = 0; i < rows; ++i) {
                List<Double> data = new ArrayList<>();

                for (int j = 0; j < cols; ++j) {
//                    this.data[i][j] = .0;
                    data.add(Numbers.randomDouble(-1.0, 1.0));
                }

                this.data.add(data);
            }
        } else {
            for (int i = 0; i < rows; ++i) {
                List<Double> data = new ArrayList<>();
                for (int j = 0; j < cols; ++j) {
                    data.add(.0);
//                    this.data[i][j] = Numbers.randomDouble(-.501, .501);
                }
                this.data.add(data);
            }
        }
    }

    void clear() {
        this.data.clear();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setValue(int row, int col, double value) {
        this.data.get(row).set(col, value);
    }

    public double getValue(int row, int col) {
        return this.data.get(row).get(col);
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

    public Matrix copy() {
        Matrix mat = new Matrix(this.rows, this.cols, false);

        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                mat.setValue(i, j, this.getValue(i, j));
            }
        }

        return mat;
    }

    @NotNull
    public static Matrix listToMatrix(@NotNull List<Double> list) {
        Matrix mat = new Matrix(list.size(), 1, false);

        for (int i = 0; i < list.size(); ++i) {
            mat.setValue(i, 0, list.get(i));
        }

        return mat;
    }

    @NotNull
    public static List<Double> matrixToList(@NotNull Matrix mat) {
        List<Double> list = new ArrayList<>();

        for (int i = 0; i < mat.getRows(); ++i) {
            for (int j = 0; j < mat.getCols(); ++j) {
                list.add(mat.getValue(i, j));
            }
        }

        return list;
    }
}
