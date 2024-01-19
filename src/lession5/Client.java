package lession5;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String name;

    public Client(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket.isConnected()) {
                socket.close();
                bufferedWriter.close();
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    bufferedWriter.write(name + "\n");
                    bufferedWriter.flush();
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        String message = scanner.nextLine();
                        if (message.equals("stop")) {
                            bufferedWriter.write(message + "\n");
                            bufferedWriter.flush();
                            closeEverything(socket, bufferedReader, bufferedWriter);
                            break;
                        }

                        bufferedWriter.write(name +": "+message + "\n");
                        bufferedWriter.flush();

                    }
                    bufferedWriter.flush();
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroup;
                try {
                    while (true) {
                        messageFromGroup = bufferedReader.readLine();
                        if(messageFromGroup.equals("stop")){
                            closeEverything(socket, bufferedReader, bufferedWriter);
                        }
                        System.out.println(messageFromGroup);
                    }
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }

            }
        }).start();
    }
}
