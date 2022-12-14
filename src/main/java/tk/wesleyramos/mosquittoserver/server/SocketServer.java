package tk.wesleyramos.mosquittoserver.server;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.threads.SocketServerKeepAlive;
import tk.wesleyramos.mosquittoserver.server.threads.SocketServerReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

    private ServerSocket service;
    private SocketServerKeepAlive keepAlive;
    private SocketServerReader reader;
    private List<SocketClient> clients;

    public void start(String address, int port) throws IOException {
        this.clients = new ArrayList<>();
        this.service = new ServerSocket();
        this.service.bind(new InetSocketAddress(address, port));
        this.keepAlive = new SocketServerKeepAlive(this);
        this.keepAlive.start();
        this.reader = new SocketServerReader(this);
        this.reader.start();

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Server]: " + MosquittoColor.WHITE_BRIGHT + "o servidor está rodando na porta: " + MosquittoColor.YELLOW_BRIGHT + port + MosquittoColor.RESET);
    }

    public void stop() throws IOException {
        this.clients.forEach(SocketClient::disconnect);
        this.clients.clear();

        this.service.close();

        this.keepAlive.interrupt();
        this.reader.interrupt();

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Server]: " + MosquittoColor.WHITE_BRIGHT + "o servidor foi totalmente desligado." + MosquittoColor.RESET);
    }

    public List<SocketClient> getClients() {
        return this.clients;
    }

    public ServerSocket getServer() {
        return this.service;
    }

    public SocketServerKeepAlive getKeepAlive() {
        return this.keepAlive;
    }

    public SocketServerReader getReader() {
        return reader;
    }
}
