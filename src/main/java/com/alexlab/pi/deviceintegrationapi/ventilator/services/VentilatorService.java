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

        log.info("Ventilator toggled. Before state: {}", ventilatorState);
        if (ventilatorState.isOn()) {
            this.stop();
            return;
        }

        this.start();
    }

    @Override
    public void start() {
        if (ventilatorTerminal.start()) {
            log.info("Ventilator started. State: {}", ventilatorState);
            ventilatorState.setOn(true);
        }
    }

    @Override
    public void stop() {
        if (ventilatorTerminal.stop()) {
            log.info("Ventilator stopped. State: {}", ventilatorState);
            ventilatorState.setOn(false);
        }
    }

    @Override
    public void setSpeed(int desiredSpeed) {

        int actualSpeed = ventilatorState.getSpeed();
        if (actualSpeed == desiredSpeed) {
            return;
        }

        int increase = desiredSpeed > actualSpeed ? desiredSpeed - actualSpeed : MAX_SPEED - actualSpeed + desiredSpeed;
        int actualIncrease = ventilatorTerminal.setSpeed(increase);
        ventilatorState.setSpeed((actualSpeed + actualIncrease) % MAX_SPEED);
        log.info("Ventilator speed set to {}. Actual speed: {}. Actual increase: {}. State: {}", desiredSpeed, actualSpeed, actualIncrease, ventilatorState);
    }
}
