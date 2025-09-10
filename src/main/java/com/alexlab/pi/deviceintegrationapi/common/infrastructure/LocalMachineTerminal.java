package com.alexlab.pi.deviceintegrationapi.common.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
class LocalMachineTerminal implements TerminalInterface {

    public int execute(String command, String workingDirectory) throws IOException {
        log.info("Executing command: {}", command);

        String[] cmdArray = {"/bin/bash", "-c", command};

        ProcessBuilder builder = new ProcessBuilder(cmdArray);
        File directory = new File(workingDirectory);
        builder.directory(directory);

        try {
            Process process = builder.start();

            // Log process output
            try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                String line;
                while ((line = outputReader.readLine()) != null) {
                    log.info(line);
                }
                while ((line = errorReader.readLine()) != null) {
                    log.error(line);
                }
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
