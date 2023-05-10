package org.academiadecodigo_powrangers;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread{

    private Socket socket;
    private ClientChat client;
    private PrintWriter writer;

    public WriteThread(Socket socket, ClientChat client) {
        this.socket = socket;
        this.client =  client;
    }


    OutputStream output;


    @Override
    public void run(){

        try {
            output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("What's your name?");
        String userName = scanner.nextLine();
        System.out.println("Your name is " + userName);

        client.setUserName(userName);
        writer.println(userName);

        String text;

        do {
            text = scanner.nextLine();
            writer.println("⁄/"+ userName +"// " + text);

        } while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }

}



