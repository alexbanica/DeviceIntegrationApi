package com.alexlab.pi.deviceintegrationapi.ventilator.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VentilatorTerminalConfiguration
{
    private String startCommand;
    private String stopCommand;
    private String setSpeedCommand;
    private String rotateCommand;
}
