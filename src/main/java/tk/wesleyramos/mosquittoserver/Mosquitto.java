package tk.wesleyramos.mosquittoserver;

import tk.wesleyramos.mosquittoserver.server.SocketServer;

import java.io.IOException;

// TODO: criar um ambiente de interação com o prompt de comando
public class Mosquitto {

    public static SocketServer service;

    public static void main(String[] args) {
        System.out.println();
        System.out.println(MosquittoColor.CYAN_BRIGHT + "  Essa aplicação serve para interliga vários servidores");
        System.out.println("                   " + MosquittoColor.CYAN_UNDERLINED + "Mosquitto 1.0");
        System.out.println(MosquittoColor.YELLOW_BRIGHT + "                  @WesleyGames001");
        System.out.println();

        try {
            service = new SocketServer("127.0.0.1", 26906);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(MosquittoColor.RED + "[Server]: " + MosquittoColor.RED_BRIGHT + "não foi possível iniciar o servidor!");
        }
    }
}
