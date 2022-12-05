package tk.wesleyramos.mosquittoserver.terminal.commands;

import tk.wesleyramos.mosquittoserver.Mosquitto;
import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.terminal.Command;

import java.util.Arrays;
import java.util.List;

public class ClientsCommand extends Command {

    @Override
    public boolean execute(String[] args) {
        System.out.println();
        System.out.println(MosquittoColor.BLUE_BRIGHT + "[Clientes]: " + MosquittoColor.WHITE + "segue a lista dos clientes conectados:");
        System.out.println();

        if (Mosquitto.service.getClients().isEmpty()) {
            System.out.println(MosquittoColor.YELLOW_BRIGHT + "  nenhum cliente conectado.");
            System.out.println();
            return true;
        }

        for (SocketClient client : Mosquitto.service.getClients()) {
            System.out.print(MosquittoColor.BLUE_BRIGHT + "  [" + client.getDisplayName() + "]: ");

            if (client.getName() == null) {
                System.out.println(MosquittoColor.YELLOW_BRIGHT + "autenticando..");
            } else {
                System.out.println(MosquittoColor.YELLOW_BRIGHT + "conectado");
            }
        }

        System.out.println(MosquittoColor.RESET);

        return true;
    }

    @Override
    public String getName() {
        return "clientes";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("clients", "list", "listar");
    }
}
