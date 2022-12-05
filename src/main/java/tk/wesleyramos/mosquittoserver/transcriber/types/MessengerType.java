package tk.wesleyramos.mosquittoserver.transcriber.types;

import tk.wesleyramos.mosquittoserver.server.SocketClient;
import tk.wesleyramos.mosquittoserver.server.packets.SocketPacket;
import tk.wesleyramos.mosquittoserver.transcriber.Transcriber;
import tk.wesleyramos.mosquittoserver.transcriber.TranscriberRequest;
import tk.wesleyramos.mosquittoserver.transcriber.TranscriberType;

public class MessengerType implements TranscriberType {

    @Override
    public void execute(SocketClient from, SocketPacket packet) {
        TranscriberRequest request = Transcriber.getRequests().get(packet.getId());

        if (request != null) {
            request.callback(from, packet);
            return;
        }

        packet.getTargetClient().forEach(target -> target.write(packet));
    }
}
