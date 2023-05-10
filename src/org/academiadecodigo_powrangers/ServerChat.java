package org.academiadecodigo_powrangers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServerChat {

    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public ServerChat(int port) {
        this.port = port;
    }

    public void execute() {

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Chat Server is listening on port " + port);

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("New mother fucker connected");


                UserThread userclient = new UserThread(socket,this);
                userThreads.add(userclient);
                userclient.start();
            }
        } catch (IOException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {

        ServerChat server = new ServerChat(1111);
        server.execute();

    }
// Delivers a message from one user to others (broadcasting)
    public void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    public void addUserName(String userName) {
        userNames.add(userName);
    }

// When a client is disconneted, removes the associated username and UserThread
    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    // Returns true if there are other users connected (not count the currently connected user)
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }



}
