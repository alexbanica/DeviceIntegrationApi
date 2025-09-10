package com.alexlab.pi.deviceintegrationapi.ventilator.configurations;

import com.alexlab.pi.deviceintegrationapi.ventilator.dtos.VentilatorTerminalConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VentilatorIntegrationConfiguration {

    @Bean
    protected VentilatorTerminalConfiguration extractionTerminalConfiguration(
            @Value("${integration.ventilator.terminal.start_script}") String startScriptPath,
            @Value("${integration.ventilator.terminal.start_script}") String stopScriptPath,
            @Value("${integration.ventilator.terminal.start_script}") String setSpeedScriptPath,
            @Value("${integration.ventilator.terminal.rotate_script}") String rotateScriptPath,
            @Value("${integration.ventilator.terminal.dir}") String workingDirectory
    ) {
        return new VentilatorTerminalConfiguration(startScriptPath, stopScriptPath, setSpeedScriptPath, rotateScriptPath, workingDirectory);
    }
}
