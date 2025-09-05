package com.alexlab.pi.deviceintegrationapi.ventilator.infrastructure;

import com.alexlab.pi.deviceintegrationapi.common.infrastructure.TerminalInterface;
import com.alexlab.pi.deviceintegrationapi.ventilator.dtos.VentilatorTerminalConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
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
    public void start() {
        try {
            this.terminalInterface.execute(this.configuration.getStartCommand());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            this.terminalInterface.execute(this.configuration.getStopCommand());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setSpeed(int speed) {
        try {
            while (speed-- >= 0) {
                this.terminalInterface.execute(this.configuration.getStartCommand());
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
