package com.alexlab.pi.deviceintegrationapi.ventilator.infrastructure;

import com.alexlab.pi.deviceintegrationapi.common.infrastructure.TerminalInterface;
import com.alexlab.pi.deviceintegrationapi.ventilator.dtos.VentilatorTerminalConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
class VentilatorTerminal implements VentilatorTerminalInterface {

    private final TerminalInterface terminalInterface;
    private final VentilatorTerminalConfiguration configuration;

    public VentilatorTerminal(
            @Qualifier("localMachineTerminal")
            TerminalInterface terminalInterface,
            VentilatorTerminalConfiguration configuration
    ) {
        this.terminalInterface = terminalInterface;
        this.configuration = configuration;
    }

    @Override
    public boolean start() {
        try {
            return 0 == this.terminalInterface.execute(this.configuration.getStartCommand());
        } catch (IOException e) {
            log.error("Error while starting ventilator", e);
            return false;
        }
    }

    @Override
    public boolean stop() {
        try {
            return 0 == this.terminalInterface.execute(this.configuration.getStopCommand());
        } catch (IOException e) {
            log.error("Error while stopping ventilator", e);
            return false;
        }
    }

    @Override
    public int setSpeed(int speed) {
        int actualSpeed = 0;
        while (speed-- >= 0) {
            try {
                this.terminalInterface.execute(this.configuration.getStartCommand());
                Thread.sleep(1000);
                actualSpeed++;
            } catch (IOException | InterruptedException e) {
                log.error("Error while setting ventilator speed", e);
            }
        }
        return actualSpeed;
    }

    @Override
    public boolean rotate() {
        try {
            return 0 == this.terminalInterface.execute(this.configuration.getRotateCommand());
        } catch (IOException e) {
            log.error("Error while rotating ventilator", e);
            return false;
        }
    }
}
