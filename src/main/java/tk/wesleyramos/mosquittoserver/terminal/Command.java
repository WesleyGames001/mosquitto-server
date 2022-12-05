package tk.wesleyramos.mosquittoserver.terminal;

import java.util.List;

public abstract class Command {

    public abstract boolean execute(String[] args);

    public String getName() {
        return null;
    }

    public List<String> getAliases() {
        return null;
    }
}
