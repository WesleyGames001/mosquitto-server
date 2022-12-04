package tk.wesleyramos.mosquittoserver.server.threads;

import tk.wesleyramos.mosquittoserver.server.SocketPacket;
import tk.wesleyramos.mosquittoserver.server.SocketPacketType;
import tk.wesleyramos.mosquittoserver.server.SocketServer;

public class SocketKeepAlive extends Thread {

    private final SocketServer service;

    public SocketKeepAlive(SocketServer service) {
        this.service = service;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException ignored) {
                continue;
            }

            SocketPacket packet = new SocketPacket(SocketPacketType.KEEP_ALIVE).set("currentTimeMillis", System.currentTimeMillis());

            service.getClients().removeIf(client -> {
                if ((client.getLastKeepAlive() + 15000) < System.currentTimeMillis()) {
                    client.disconnect();
                    return true;
                }

                try {
                    client.write(packet);
                    return false;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    client.disconnect();
                    return true;
                }
            });
        } while (service.getServer().isBound());
    }
}
