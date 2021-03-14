package com.miguel.neural;

import com.miguel.utils.Numbers;

public class Neuron {

    private double value;

    private double activatedValue;
    private double derivedValue;

    public Neuron(double value) {
        this.value = value;

        this.activatedValue = .0;
        this.derivedValue = .0;
    }

    public void activate() {
        this.activatedValue = Numbers.fastSigmoid(this.value);
    }

    public void derive() {
        this.derivedValue = Numbers.fastSigmoidDerivative(this.activatedValue);
    }

    public double getValue() {
        return value;
    }

    public double getActivatedValue() {
        return activatedValue;
    }

    public double getDerivedValue() {
        return derivedValue;
    }

    public void setValue(double value) {
        this.value = value;

        this.activate();
        this.derive();
    }
}
