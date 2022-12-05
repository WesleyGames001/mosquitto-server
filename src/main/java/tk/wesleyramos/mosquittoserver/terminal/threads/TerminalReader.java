package tk.wesleyramos.mosquittoserver.terminal.threads;

import tk.wesleyramos.mosquittoserver.terminal.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalReader extends Thread {

    private final Terminal terminal;

    public TerminalReader(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (terminal.isRunning()) {
                this.terminal.read(reader.readLine());
            }
        } catch (IOException ignored) {
        }
    }
}
