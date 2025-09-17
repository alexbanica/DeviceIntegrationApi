package com.alexlab.pi.deviceintegrationapi.ventilator.services;

import com.alexlab.pi.deviceintegrationapi.ventilator.dtos.VentilatorState;

public interface VentilatorServiceInterface
{
    void toggle();
    void start();
    void stop();
    void setSpeed(int speed);
    void rotate();
    VentilatorState getState();
}
