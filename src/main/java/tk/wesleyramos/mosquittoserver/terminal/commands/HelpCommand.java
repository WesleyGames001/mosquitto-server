package tk.wesleyramos.mosquittoserver.terminal.commands;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.terminal.Command;

import java.util.Arrays;
import java.util.List;

public class HelpCommand extends Command {

    @Override
    public boolean execute(String[] args) {
        System.out.println(MosquittoColor.BLUE_BRIGHT + "[Ajuda]: " + MosquittoColor.WHITE + "ajuda, clientes, credenciais, desligar" + MosquittoColor.RESET);
        return true;
    }

    @Override
    public String getName() {
        return "ajuda";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("help", "?");
    }
}
