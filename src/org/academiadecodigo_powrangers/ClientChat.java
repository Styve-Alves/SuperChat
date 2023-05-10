package org.academiadecodigo_powrangers;

import java.io.IOException;
import java.net.Socket;

public class ClientChat {



    public static void main(String[] args) {

        ClientChat client = new ClientChat("localhost", 1111);
        client.execute();

    }
    private String hostname;
    private int port;
    private String userName;

    public ClientChat(String hostname, int port) {
        this.hostname=hostname;
        this.port= port;
    }


    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);

            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (IOException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        }

    }


    public void setUserName(String userName){
        this.userName= userName;
    }

    public String getUserName() {
        return userName;
    }





}
