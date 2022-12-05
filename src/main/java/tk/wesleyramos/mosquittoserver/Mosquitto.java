package tk.wesleyramos.mosquittoserver;

import tk.wesleyramos.mosquittoserver.server.SocketConfig;
import tk.wesleyramos.mosquittoserver.server.SocketServer;
import tk.wesleyramos.mosquittoserver.terminal.Terminal;

import java.io.IOException;

// TODO: adicionar criptografia pont-a-ponta
public class Mosquitto {

    public static SocketConfig config = new SocketConfig();
    public static SocketServer service = new SocketServer();
    public static Terminal terminal = new Terminal();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                terminal.stop();
                service.stop();
                config.stop();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(MosquittoColor.RED + "[Server]: " + MosquittoColor.RED_BRIGHT + "não foi possível encerrar o servidor!");
            }
        }));
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println(MosquittoColor.CYAN_BRIGHT + "  Essa aplicação serve para interliga vários servidores");
        System.out.println("                   " + MosquittoColor.CYAN_UNDERLINED + "Mosquitto 1.0");
        System.out.println(MosquittoColor.YELLOW_BRIGHT + "                  @WesleyGames001");
        System.out.println();

        try {
            config.start();
            service.start(config.getAddress(), config.getPort());
            terminal.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(MosquittoColor.RED + "[Server]: " + MosquittoColor.RED_BRIGHT + "não foi possível iniciar o servidor!");
        }
    }
}
