package tk.wesleyramos.mosquittoserver.transcriber;

import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.packets.SocketPacket;

public interface TranscriberType {
    void execute(SocketClient from, SocketPacket packet);
}
