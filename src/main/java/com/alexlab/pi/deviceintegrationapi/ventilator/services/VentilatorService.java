package com.alexlab.pi.deviceintegrationapi.ventilator.services;

import com.alexlab.pi.deviceintegrationapi.ventilator.dtos.VentilatorState;
import com.alexlab.pi.deviceintegrationapi.ventilator.infrastructure.VentilatorTerminalInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class VentilatorService implements VentilatorServiceInterface {

    private final static int MAX_SPEED = 3;
    private final VentilatorTerminalInterface ventilatorTerminal;
    private final VentilatorState ventilatorState = new VentilatorState();

    @Override
    public void toggle() {
        log.debug("Ventilator toggled. Before state: {}", ventilatorState);

        if (ventilatorState.isOn()) {
            this.stop();
            return;
        }

        this.start();
    }

    @Override
    public void start() {
        if (this.ventilatorState.isOn()) {
            log.info("Ventilator already started. Ignoring request.");
            return;
        }
        if (ventilatorTerminal.start()) {
            ventilatorState.setOn(true);
            ventilatorState.setSpeed(1);
            ventilatorState.setRotating(false);
            log.info("Ventilator started. State: {}", ventilatorState);
        }
    }

    @Override
    public void stop() {
        if (!this.ventilatorState.isOn()) {
            log.info("Ventilator not started. Ignoring request.");
            return;
        }
        if (ventilatorTerminal.stop()) {
            ventilatorState.setOn(false);
            ventilatorState.setSpeed(0);
            ventilatorState.setRotating(false);
            log.info("Ventilator stopped. State: {}", ventilatorState);
        }
    }

    @Override
    public void setSpeed(int desiredSpeed) {

        if (desiredSpeed == 0) {
            this.stop();
            return;
        }

        int actualSpeed = ventilatorState.getSpeed();
        if (actualSpeed == desiredSpeed && !ventilatorState.isOn()) {
            return;
        }

        int increase = desiredSpeed > actualSpeed ? desiredSpeed - actualSpeed : MAX_SPEED - actualSpeed + desiredSpeed;
        int actualIncrease = ventilatorTerminal.setSpeed(increase);
        int newSpeed = (actualSpeed + actualIncrease);

        ventilatorState.setSpeed(newSpeed <= MAX_SPEED ? newSpeed : newSpeed % MAX_SPEED);
        log.info("Ventilator speed set to {}. Actual speed: {}. Actual increase: {}. State: {}", desiredSpeed, actualSpeed, actualIncrease, ventilatorState);
    }

    @Override
    public void rotate() {
        if (ventilatorState.isOn() && ventilatorTerminal.rotate()) {
            ventilatorState.setRotating(true);
            log.info("Ventilator rotated. State: {}", ventilatorState);
            return;
        }
        ventilatorState.setRotating(false);
    }

    @Override
    public VentilatorState getState() {
        return ventilatorState;
    }
}
