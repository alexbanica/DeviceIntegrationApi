package com.alexlab.pi.deviceintegrationapi.ventilator.controllers;

import com.alexlab.pi.deviceintegrationapi.common.controllers.AbstractApiController;
import com.alexlab.pi.deviceintegrationapi.ventilator.services.VentilatorServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/api/ventilator")
@AllArgsConstructor
public class VentilatorApiController extends AbstractApiController {

    private final VentilatorServiceInterface ventilatorService;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void start() {
        this.ventilatorService.toggle();
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void stop() {
        this.ventilatorService.toggle();
    }

    @PutMapping("/speed/{speed}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setSpeed(
            @Min(1)
            @Max(3)
            @PathVariable int speed
    ) {
        this.ventilatorService.setSpeed(speed);
    }
}
