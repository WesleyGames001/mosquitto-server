package tk.wesleyramos.mosquittoserver.terminal.commands;

import tk.wesleyramos.mosquittoserver.terminal.Command;

import java.util.Arrays;
import java.util.List;

public class StopCommand extends Command {

    @Override
    public boolean execute(String[] args) {
        System.exit(0);
        return true;
    }

    @Override
    public String getName() {
        return "desligar";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("stop", "end", "finish", "finalizar");
    }
}
