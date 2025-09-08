package com.alexlab.pi.deviceintegrationapi.ventilator.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VentilatorState
{
    private boolean isOn;
    private int speed;
}
