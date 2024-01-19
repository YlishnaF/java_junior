package lession5;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свое имя: ");
        String name = scanner.nextLine();
        Socket socket = new Socket("localhost", 1500);
        Client client = new Client(socket, name);
        client.listenForMessage();
        client.sendMsg();
    }
}
