package tk.wesleyramos.mosquittoserver.terminal;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.terminal.commands.ClientsCommand;
import tk.wesleyramos.mosquittoserver.terminal.commands.CredentialsCommand;
import tk.wesleyramos.mosquittoserver.terminal.commands.HelpCommand;
import tk.wesleyramos.mosquittoserver.terminal.commands.StopCommand;
import tk.wesleyramos.mosquittoserver.terminal.threads.TerminalReader;

import java.util.ArrayList;
import java.util.List;

public class Terminal {

    private final List<Command> commands = new ArrayList<>();

    private boolean running = false;
    private TerminalReader reader;

    public void start() {
        this.running = true;
        this.reader = new TerminalReader(this);
        this.reader.start();

        this.commands.clear();
        this.commands.add(new ClientsCommand());
        this.commands.add(new CredentialsCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new StopCommand());
    }

    public void stop() {
        this.running = false;
        this.reader.interrupt();
    }

    public boolean read(String line) {
        String name = line.split(" ")[0].toLowerCase();
        String[] args = new String[line.split(" ").length - 1];

        System.arraycopy(line.split(" "), 1, args, 0, args.length);

        for (Command command : commands) {
            if (!command.getName().equals(name) && !command.getAliases().contains(name)) {
                continue;
            }
            if (!command.execute(args)) {
                continue;
            }
            return true;
        }

        System.out.println(MosquittoColor.RED_BRIGHT + "O comando não foi encontrado..." + MosquittoColor.RESET);
        return false;
    }

    public boolean isRunning() {
        return running;
    }
}
