package com.alexlab.pi.deviceintegrationapi.common.infrastructure;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
class LocalMachineTerminal implements TerminalInterface {

    public int execute(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            return process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 1;
        }
    }
}
