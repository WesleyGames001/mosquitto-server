package tk.wesleyramos.mosquittoserver.server;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.threads.SocketKeepAlive;
import tk.wesleyramos.mosquittoserver.server.threads.SocketServerReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

    private final ServerSocket service;
    private final SocketKeepAlive keepAlive;
    private final SocketServerReader reader;
    private final List<SocketClient> clients;

    public SocketServer(String address, int port) throws IOException {
        this.clients = new ArrayList<>();
        this.service = new ServerSocket();
        this.service.bind(new InetSocketAddress(address, port));
        this.keepAlive = new SocketKeepAlive(this);
        this.keepAlive.start();
        this.reader = new SocketServerReader(this);
        this.reader.start();

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Server]: " + MosquittoColor.WHITE_BRIGHT + "o servidor está rodando na porta: " + MosquittoColor.YELLOW_BRIGHT + port);
    }

    public List<SocketClient> getClients() {
        return this.clients;
    }

    public ServerSocket getServer() {
        return this.service;
    }

    public SocketKeepAlive getKeepAlive() {
        return this.keepAlive;
    }

    public SocketServerReader getReader() {
        return reader;
    }
}
