package tk.wesleyramos.mosquittoserver.transcriber;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.packets.SocketPacket;
import tk.wesleyramos.mosquittoserver.server.packets.SocketPacketType;
import tk.wesleyramos.mosquittoserver.transcriber.types.AuthType;
import tk.wesleyramos.mosquittoserver.transcriber.types.KeepAliveType;
import tk.wesleyramos.mosquittoserver.transcriber.types.MessengerType;

import java.util.HashMap;
import java.util.Map;

public class Transcriber {

    private static final Map<SocketPacketType, TranscriberType> types = new HashMap<>();
    private static final Map<String, TranscriberRequest> requests = new HashMap<>();

    static {
        types.put(SocketPacketType.AUTH, new AuthType());
        types.put(SocketPacketType.KEEP_ALIVE, new KeepAliveType());
        types.put(SocketPacketType.MESSENGER, new MessengerType());
    }

    public static void read(SocketClient from, SocketPacket packet) {
        if (!packet.isValid()) {
            System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoServer] [Transcriber] (" + from.getDisplayName() + "): " + MosquittoColor.RED_BRIGHT + "uma nova mensagem desconhecida foi recebida... packet=" + packet + MosquittoColor.RESET);
            return;
        }

        if (from.getName() == null) {
            types.get(SocketPacketType.AUTH).execute(from, packet);
            return;
        }

        if (packet.getType() != null) {
            types.get(packet.getType()).execute(from, packet);
            return;
        }

        System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoServer] [Transcriber] (" + from.getDisplayName() + "): " + MosquittoColor.RED_BRIGHT + "uma nova mensagem desconhecida foi recebida... type=" + packet.getType().name() + "; body=" + packet.getBody() + MosquittoColor.RESET);
    }

    public static void write(SocketClient target, TranscriberRequest request) {
        if (request.getPacket().getFrom().equals("mosquittoserver")) {
            requests.put(request.getPacket().getId(), request);
        }

        try {
            target.write(request.getPacket());
        } catch (NullPointerException ignored) {
        }
    }

    public static Map<SocketPacketType, TranscriberType> getTypes() {
        return types;
    }

    public static Map<String, TranscriberRequest> getRequests() {
        return requests;
    }
}
