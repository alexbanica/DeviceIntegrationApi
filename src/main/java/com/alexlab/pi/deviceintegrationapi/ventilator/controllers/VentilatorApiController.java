package com.alexlab.pi.deviceintegrationapi.ventilator.controllers;

import com.alexlab.pi.deviceintegrationapi.common.controllers.AbstractApiController;
import com.alexlab.pi.deviceintegrationapi.ventilator.infrastructure.VentilatorTerminalInterface;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController("/ventilator")
@AllArgsConstructor
public class VentilatorApiController extends AbstractApiController {

    private final VentilatorTerminalInterface ventilatorTerminal;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void start() {
        this.ventilatorTerminal.start();
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void stop() {
        this.ventilatorTerminal.stop();
    }

    @PutMapping("/speed/{speed}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setSpeed(
            @Min(1)
            @Max(3)
            @PathVariable int speed
    ) {
        this.ventilatorTerminal.setSpeed(speed);
    }
}
