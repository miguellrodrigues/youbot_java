package com.miguel.neural;

import com.miguel.utils.Matrix;
import com.miguel.utils.Numbers;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Network {

    private List<Integer> topology;
    private int topologySize;

    private List<Layer> layers;
    private List<Matrix> weightMatrices;

    private List<Double> errors;
    private List<Double> derivedErrors;

    private double bias = 0.02;
    private double fitness;

    public Network(@NotNull List<Integer> topology) {
        this.topology = topology;
        this.topologySize = topology.size();

        this.layers = new ArrayList<>();
        this.weightMatrices = new ArrayList<>();

        this.errors = new ArrayList<>();
        this.derivedErrors = new ArrayList<>();

        for (int i = 0; i < topologySize; ++i) {
            this.layers.add(new Layer(topology.get(i)));
        }

        for (int i = 0; i < (topologySize - 1); ++i) {
            Matrix weightMatrix = new Matrix(topology.get(i + 1), topology.get(i), true);

            this.weightMatrices.add(weightMatrix);

            errors.add(.0);
            derivedErrors.add(.0);
        }
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setCurrentInput(@NotNull Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); ++i) {
            this.layers.get(0).setNeuronValue(i, matrix.getValue(i, 0));
        }
    }

    public void feedForward() {
        Matrix left, right, r;

        for (int i = 0; i < (this.topologySize - 1); ++i) {
            if (i != 0) {
                left = this.layers.get(i).convertActivatedValues();
            } else {
                left = this.layers.get(i).convertValues();
            }

            right = this.weightMatrices.get(i).copy();

            r = right.multiply(left);

            for (int j = 0; j < r.getRows(); ++j) {
                this.layers.get(i + 1).setNeuronValue(j, r.getValue(j, 0) + this.bias);
            }
        }
    }

    public List<Double> predict(List<Double> input) {
        this.setCurrentInput(Matrix.listToMatrix(input));
        this.feedForward();

        Matrix outputMatrix = this.layers.get(this.topologySize - 1).convertActivatedValues();

        return Matrix.matrixToList(outputMatrix);
    }

    public void mutate(double rate) {
        for (Matrix weightMatrix : this.weightMatrices) {
            double count = rate * weightMatrix.getCols();

            int randomRow = Numbers.randomInt(0, weightMatrix.getRows() - 1);

            for (int i = 0; i < count; ++i) {
                int randomCol = Numbers.randomInt(0, weightMatrix.getCols() - 1);

                double value = weightMatrix.getValue(randomRow, randomCol);

                weightMatrix.setValue(randomRow, randomCol, value + Numbers.randomDouble(-1.0, 1.0));
            }
        }
    }

    public Network copy() {
        Network net = new Network(this.topology);

        for (int i = 0; i < this.weightMatrices.size(); ++i) {
            net.weightMatrices.set(i, this.weightMatrices.get(i));
        }

        net.setFitness(this.getFitness());
        net.setBias(this.getBias());

        return net;
    }

    public static void crossOver(@NotNull Network child, Network father, Network mother) {
        for (int i = 0; i < child.weightMatrices.size(); ++i) {
            Matrix fatherWeights = father.weightMatrices.get(i);
            Matrix motherWeights = mother.weightMatrices.get(i);

            for (int j = 0; j < fatherWeights.getRows(); ++j) {
                for (int k = 0; k < fatherWeights.getCols(); ++k) {
                    if (Numbers.randomDouble(.0, 1.0) < .5) {
                        child.weightMatrices.get(i).setValue(j, k, fatherWeights.getValue(j, k));
                    } else {
                        child.weightMatrices.get(i).setValue(j, k, motherWeights.getValue(j, k));
                    }
                }
            }
        }
    }
}
