package tk.wesleyramos.mosquittoserver;

import tk.wesleyramos.mosquittoserver.server.SocketServer;

import java.io.IOException;

// TODO: certificar que o servidor não vai fechar por inatividade
// TODO: criar um ambiente de interação com o prompt de comando
public class Mosquitto {

    public static MosquittoConfig config = new MosquittoConfig();
    public static SocketServer service = new SocketServer();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                config.stop();
                service.stop();
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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(MosquittoColor.RED + "[Server]: " + MosquittoColor.RED_BRIGHT + "não foi possível iniciar o servidor!");
        }
    }
}
