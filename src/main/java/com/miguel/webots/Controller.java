package com.miguel.webots;

import com.cyberbotics.webots.controller.*;
import com.miguel.utils.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    private final Supervisor supervisor;

    private final int samplingRate;

    public Controller(int samplingRate, boolean setupSensors) {
        this.supervisor = new Supervisor();
        this.samplingRate = samplingRate;

        if (setupSensors) {
            this.setupSensors();
        }
    }

    int step() {
        return this.supervisor.step(this.samplingRate);
    }

    Supervisor getSupervisor() {
        return this.supervisor;
    }

    List<Device> getAllDevices() {
        List<Device> devices = new ArrayList<>();

        for (int i = 0; i < getSupervisor().getNumberOfDevices(); ++i) {
            Device device = getSupervisor().getDeviceByIndex(i);

            devices.add(device);
        }

        return devices;
    }

    List<Device> getDevicesByTypeList(List<Integer> types) {
        return getAllDevices().stream().filter(device -> types.contains(device.getNodeType())).collect(Collectors.toList());
    }

    double[] getObjectOrientation(String nodeDef) {
        return getSupervisor().getFromDef(nodeDef).getOrientation();
    }

    double[] getObjectPosition(String nodeDef) {
        return getSupervisor().getFromDef(nodeDef).getPosition();
    }

    double[] getObjectRotation(String nodeDef) {
        Field rotationField = supervisor.getFromDef(nodeDef).getField("rotation");

        return rotationField.getSFVec3f();
    }

    void setObjectPosition(String nodeDef, double[] position) {
        Field translationField = supervisor.getFromDef(nodeDef).getField("rotation");

        translationField.setSFVec3f(position);
    }

    void setObjectRotation(String nodeDef, double[] rotation) {
        Field rotationField = supervisor.getFromDef(nodeDef).getField("rotation");

        rotationField.setSFRotation(rotation);
    }

    List<PositionSensor> getPositionSensors() {
        return getDevicesByTypeList(Collections.singletonList(Node.POSITION_SENSOR)).stream().map(device -> (PositionSensor) device).collect(Collectors.toList());
    }

    List<DistanceSensor> getDistanceSensors() {
        return getDevicesByTypeList(Collections.singletonList(Node.DISTANCE_SENSOR)).stream().map(device -> (DistanceSensor) device).collect(Collectors.toList());
    }

    List<LightSensor> getLightSensors() {
        return getDevicesByTypeList(Collections.singletonList(Node.LIGHT_SENSOR)).stream().map(device -> (LightSensor) device).collect(Collectors.toList());
    }

    List<TouchSensor> getTouchSensors() {
        return getDevicesByTypeList(Collections.singletonList(Node.TOUCH_SENSOR)).stream().map(device -> (TouchSensor) device).collect(Collectors.toList());
    }

    List<Motor> getMotors() {
        return getDevicesByTypeList(Collections.singletonList(Node.ROTATIONAL_MOTOR)).stream().map(device -> (Motor) device).collect(Collectors.toList());
    }

    void setupSensors() {
        var positionSensors = getPositionSensors();
        var distanceSensors = getDistanceSensors();
        var lightSensors = getLightSensors();
        var touchSensors = getTouchSensors();

        positionSensors.forEach(positionSensor -> positionSensor.enable(this.samplingRate));
        distanceSensors.forEach(distanceSensor -> distanceSensor.enable(this.samplingRate));
        lightSensors.forEach(lightSensor -> lightSensor.enable(this.samplingRate));
        touchSensors.forEach(touchSensor -> touchSensor.enable(this.samplingRate));
    }

    void setupMotors(int[] indexes, float position) {
        var motors = getMotors();

        motors.forEach(motor -> {
            motor.setPosition(position);
            motor.setVelocity(.0);
        });
    }

    void setMotorVelocity(int index, float velocity) {
        getMotors().get(index).setVelocity(velocity);
    }
}
