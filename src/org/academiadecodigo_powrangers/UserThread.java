package org.academiadecodigo_powrangers;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {

    private Socket socket;
    private ServerChat server;
    private PrintWriter writer;

    public UserThread(Socket socket, ServerChat server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run(){

        InputStream input = null;
        try {
            input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            printUsers();
            String userName = reader.readLine();
            server.addUserName(userName);
            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);


            String clientMessage;
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

            } while (!clientMessage.equals("bye"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    // Sends a list of online users to the newly connected user.
    public void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    // Sends a message to the client.
    public void sendMessage(String message) {
        writer.println(message);
    }

}
