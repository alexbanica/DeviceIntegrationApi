package com.alexlab.pi.deviceintegrationapi.ventilator.infrastructure;

public interface VentilatorTerminalInterface
{
    void start();
    void stop();
    void setSpeed(int speed);
}
