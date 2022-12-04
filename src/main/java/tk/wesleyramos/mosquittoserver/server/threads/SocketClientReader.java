package tk.wesleyramos.mosquittoserver.server.threads;

import org.json.JSONException;
import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.SocketPacket;
import tk.wesleyramos.mosquittoserver.transcriber.Transcriber;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class SocketClientReader extends Thread {

    private final SocketClient client;

    public SocketClientReader(SocketClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            DataInputStream reader = new DataInputStream(new BufferedInputStream(this.client.getSocket().getInputStream()));

            while (this.client.getSocket().getInputStream() != null) {
                Transcriber.read(client, new SocketPacket(reader.readUTF()));
            }
        } catch (IOException | JSONException ignored) {
        }
    }
}
