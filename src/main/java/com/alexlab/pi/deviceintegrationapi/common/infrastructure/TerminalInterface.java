package com.alexlab.pi.deviceintegrationapi.common.infrastructure;

import java.io.IOException;

public interface TerminalInterface {
    int execute(String command, String workingDirectory) throws IOException;
}
