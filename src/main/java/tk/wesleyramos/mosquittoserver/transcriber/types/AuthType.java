package tk.wesleyramos.mosquittoserver.transcriber.types;

import tk.wesleyramos.mosquittoserver.Mosquitto;
import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.packets.SocketPacket;
import tk.wesleyramos.mosquittoserver.server.packets.SocketPacketType;
import tk.wesleyramos.mosquittoserver.transcriber.TranscriberType;

public class AuthType implements TranscriberType {

    @Override
    public void execute(SocketClient from, SocketPacket packet) {
        if (!packet.isSet("name") || !packet.isSet("credentials")) {
            System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoServer] [Transcriber] [AUTH] (" + from.getDisplayName() + "): as informações enviadas estão incompletas, autenticação negada!" + MosquittoColor.RESET);

            from.write(new SocketPacket(SocketPacketType.AUTH).set("status", 400).set("message", "request body invalid"));
            from.disconnect();

            Mosquitto.service.getClients().remove(from);
            return;
        }

        if (!Mosquitto.config.testCredentials(packet.getString("credentials"))) {
            System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoServer] [Transcriber] [AUTH] (" + from.getDisplayName() + "): as credenciais enviadas são inválidas, autenticação negada!" + MosquittoColor.RESET);

            from.write(new SocketPacket(SocketPacketType.AUTH).set("status", 401).set("message", "credentials invalid"));
            from.disconnect();

            Mosquitto.service.getClients().remove(from);
            return;
        }

        from.setName(packet.getString("name"));
        from.write(new SocketPacket(SocketPacketType.AUTH).set("status", 200).set("message", "successfully authenticated"));

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Transcriber] [AUTH] (" + from.getDisplayName() + "): " + MosquittoColor.WHITE_BRIGHT + "as credenciais enviadas são válidas, bem-vindo " + MosquittoColor.YELLOW_BRIGHT + from.getName() + MosquittoColor.RESET);
    }
}
