package com.alexlab.pi.deviceintegrationapi.ventilator.services;

public interface VentilatorServiceInterface
{
    void toggle();
    void start();
    void stop();
    void setSpeed(int speed);
    void rotate();
}
