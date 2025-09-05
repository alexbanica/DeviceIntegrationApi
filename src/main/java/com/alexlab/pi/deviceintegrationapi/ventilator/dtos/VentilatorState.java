package com.alexlab.pi.deviceintegrationapi.ventilator.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentilatorState
{
    private boolean isOn;
    private int speed;
}
