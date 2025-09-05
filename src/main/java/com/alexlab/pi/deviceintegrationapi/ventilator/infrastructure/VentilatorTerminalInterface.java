package com.alexlab.pi.deviceintegrationapi.ventilator.infrastructure;

public interface VentilatorTerminalInterface
{
    boolean start();
    boolean stop();
    int setSpeed(int speed);
}
