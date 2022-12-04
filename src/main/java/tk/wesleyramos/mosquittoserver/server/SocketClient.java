package tk.wesleyramos.mosquittoserver.server;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.threads.SocketClientReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient {

    private static long CURRENT_ID = 0;

    private final DataOutputStream writer;
    private final Socket socket;
    private final SocketClientReader reader;

    private String display, name;
    private long lastKeepAlive;

    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new SocketClientReader(this);
        this.reader.start();
        this.writer = new DataOutputStream(this.socket.getOutputStream());

        this.lastKeepAlive = System.currentTimeMillis();
        this.display = socket.getInetAddress().getHostAddress() + " #" + CURRENT_ID++;
    }

    public void disconnect() {
        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Server] (" + getDisplayName() + "): " + MosquittoColor.WHITE_BRIGHT + "o cliente teve sua conexão fechada com o servidor");

        try {
            this.name = null;
            this.reader.interrupt();
            this.socket.close();
        } catch (IOException ignored) {
        }
    }

    public void write(SocketPacket packet) throws NullPointerException {
        try {
            this.writer.writeUTF(packet.target(getDisplayName()).toString());
            this.writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.display;
    }

    public long getLastKeepAlive() {
        return lastKeepAlive;
    }

    public void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    public Socket getSocket() {
        return socket;
    }

    public SocketClientReader getReader() {
        return reader;
    }

    public DataOutputStream getWriter() {
        return writer;
    }
}
