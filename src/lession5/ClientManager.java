package lession5;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ClientManager extends Thread {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;
    private long id;
    private static List<ClientManager> clients = new ArrayList<>();

    public ClientManager(long id, Socket socket) {
        try {
            this.socket = socket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = bufferedReader.readLine();
            this.id = id;
            clients.add(this);
            for (ClientManager c : clients) {
                if (!c.name.equals(name)) {
                    c.send("Server:" + name + " с id " + id + " подключился к чату");
                }

            }
        } catch (IOException e) {
        }

    }


    public void closeEverything() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                bufferedWriter.close();
                bufferedReader.close();
                ClientManager.clients.remove(this);
                this.interrupt();

            }
        } catch (IOException ignore) {

        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (true) {
            try {
                messageFromClient = bufferedReader.readLine();
                if (messageFromClient.equals("stop")) {
                    for (ClientManager c : clients) {
                        if (!c.name.equals(name)) {
                            c.send("Server: " + name + " покинул чат");
                        }

                    }
                    System.out.println("Server: " + name + " покинул чат");
                    this.closeEverything();
                    break;

                } else if (messageFromClient.contains("@")) {
                    long myid = Long.parseLong(messageFromClient.split("@")[1].substring(0, 1));
                    ClientManager reciwer = clients.stream().filter(c -> c.id == myid).collect(Collectors.toList()).get(0);
                    reciwer.send(messageFromClient);

                } else if (messageFromClient.startsWith("admin") && messageFromClient.contains("kick")) {
                    long num = Long.parseLong(messageFromClient.substring(messageFromClient.length() - 1));
                    String cname = clients.stream().filter(n -> n.id == num).collect(Collectors.toList()).get(0).name;
                    for (ClientManager c : clients) {
                        if (!c.name.equals(name)) {
                            c.send("Server: " + cname + " удален из чата");
                            System.out.println("Server: " + cname + "удален из чата");
                        }
                    }
                    ClientManager reciwer = clients.stream().filter(n -> n.id == num).collect(Collectors.toList()).get(0);
                    reciwer.bufferedWriter.write("stop");
                    reciwer.bufferedWriter.flush();
                    reciwer.closeEverything();

                } else {
                    for (ClientManager c : clients) {
                        if (!c.name.equals(name)) {
                            c.send(messageFromClient);
                        }

                    }
                }


            } catch (IOException e) {
            }
        }

    }

    private void send(String msg) {
        try {
            bufferedWriter.write(msg + "\n");
            bufferedWriter.flush();
        } catch (IOException ignored) {
        }

    }


}
