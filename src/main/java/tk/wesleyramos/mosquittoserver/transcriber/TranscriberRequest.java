package tk.wesleyramos.mosquittoserver.transcriber;

import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.packets.SocketPacket;

public abstract class TranscriberRequest {

    private final SocketPacket packet;

    public TranscriberRequest(SocketPacket packet) {
        this.packet = packet;
    }

    public abstract void callback(SocketClient from, SocketPacket packet);

    public SocketPacket getPacket() {
        return packet;
    }
}
