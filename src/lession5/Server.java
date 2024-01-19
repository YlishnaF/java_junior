package lession5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private static long clientIdCounter = 1L;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("Подключен новый клиент");
                ClientManager client = new ClientManager(clientIdCounter++, socket);
                client.start();
            }
        } catch (IOException e){
            closeSocket();
        }
    }

    public void closeSocket(){
        try {
            if(serverSocket!=null) serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket1 = new ServerSocket(1500);
        Server server = new Server(serverSocket1);
        server.runServer();
    }
}
