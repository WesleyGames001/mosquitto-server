package tk.wesleyramos.mosquittoserver.transcriber.types;

import tk.wesleyramos.mosquittoserver.MosquittoColor;
import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.SocketPacket;
import tk.wesleyramos.mosquittoserver.server.SocketPacketType;
import tk.wesleyramos.mosquittoserver.transcriber.TranscriberType;

public class AuthType implements TranscriberType {

    @Override
    public void execute(SocketClient from, SocketPacket packet) {
        if (!packet.isSet("name") || !packet.isSet("credentials")) {
            System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoServer] [Transcriber] [AUTH] (" + from.getDisplayName() + "): as informações enviadas estão incompletas, autenticação negada!");

            from.write(new SocketPacket(SocketPacketType.AUTH).set("status", 400).set("message", "request body invalid"));
            from.disconnect();
            return;
        }

        if (!packet.getString("credentials").equals("KAMILLE LINDA")) { // TODO: conectar ao banco de dados
            System.out.println(MosquittoColor.RED_BRIGHT + "[MosquittoServer] [Transcriber] [AUTH] (" + from.getDisplayName() + "): as credenciais enviadas são inválidas, autenticação negada!");

            from.write(new SocketPacket(SocketPacketType.AUTH).set("status", 401).set("message", "credentials invalid"));
            from.disconnect();
            return;
        }

        from.setName(packet.getString("name"));
        from.write(new SocketPacket(SocketPacketType.AUTH).set("status", 200).set("message", "successfully authenticated"));

        System.out.println(MosquittoColor.BLUE_BRIGHT + "[MosquittoServer] [Transcriber] [AUTH] (" + from.getDisplayName() + "): " + MosquittoColor.WHITE_BRIGHT + "as credenciais enviadas são válidas, bem-vindo " + MosquittoColor.YELLOW_BRIGHT + from.getName());
    }
}
