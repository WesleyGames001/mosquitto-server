package tk.wesleyramos.mosquittoserver.server.threads;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.SocketServer;

import java.io.IOException;
import java.net.SocketException;

public class SocketServerReader extends Thread {

    private final SocketServer server;

    public SocketServerReader(SocketServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        do {
            try {
                SocketClient client = new SocketClient(server.getServer().accept());

                server.getClients().add(client);

                System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Server] [Reader]: " + MosquittoColor.WHITE_BRIGHT + "uma nova conexão foi aceita de: " + MosquittoColor.YELLOW_BRIGHT + client.getDisplayName());
            } catch (SocketException ignored) {
            } catch (IOException e) {
                System.out.println(MosquittoColor.RED + "[MosquittoServer] [Server] [Reader]: " + MosquittoColor.RED_BRIGHT + "ocorreu um erro ao aceitar a conexão de um novo cliente!");
            }
        } while (!server.getServer().isClosed());
    }

    public SocketServer getServer() {
        return server;
    }
}
