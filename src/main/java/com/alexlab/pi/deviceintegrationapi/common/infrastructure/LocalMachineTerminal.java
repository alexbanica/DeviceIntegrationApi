package com.alexlab.pi.deviceintegrationapi.common.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
class LocalMachineTerminal implements TerminalInterface {

    public int execute(String command) throws IOException {
        log.info("Executing command: {}", command);
        Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitValue = process.waitFor();
            log.info("Command executed. Exit value: {}", exitValue);
            return exitValue;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error while executing command", e);
            return 1;
        }
    }
}
