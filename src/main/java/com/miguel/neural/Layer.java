package com.miguel.neural;

import com.miguel.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private final int neuronsSize;

    private final List<Neuron> neurons;

    public Layer(int neuronsSize) {
        this.neuronsSize = neuronsSize;

        this.neurons = new ArrayList<>();

        for (int i = 0; i < this.neuronsSize; ++i) {
            this.neurons.add(new Neuron(.0));
        }
    }

    public void setNeuronValue(int index, double value) {
        this.neurons.get(index).setValue(value);
    }

    public Matrix convertValues() {
        Matrix mat = new Matrix(this.neuronsSize, 1, false);

        for (int i = 0; i < this.neuronsSize; ++i) {
            mat.setValue(i, 0, this.neurons.get(i).getValue());
        }

        return mat;
    }

    public Matrix convertActivatedValues() {
        Matrix mat = new Matrix(this.neuronsSize, 1, false);

        for (int i = 0; i < this.neuronsSize; ++i) {
            mat.setValue(i, 0, this.neurons.get(i).getActivatedValue());
        }

        return mat;
    }

    public Matrix convertDerivedValues() {
        Matrix mat = new Matrix(this.neuronsSize, 1, false);

        for (int i = 0; i < this.neuronsSize; ++i) {
            mat.setValue(i, 0, this.neurons.get(i).getDerivedValue());
        }

        return mat;
    }
}
