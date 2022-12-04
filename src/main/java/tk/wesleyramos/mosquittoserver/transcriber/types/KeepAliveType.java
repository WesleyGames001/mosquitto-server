package tk.wesleyramos.mosquittoserver.transcriber.types;

import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.SocketPacket;
import tk.wesleyramos.mosquittoserver.transcriber.TranscriberType;

public class KeepAliveType implements TranscriberType {

    @Override
    public void execute(SocketClient from, SocketPacket packet) {
        from.setLastKeepAlive(packet.getLong("currentTimeMillis"));
    }
}
