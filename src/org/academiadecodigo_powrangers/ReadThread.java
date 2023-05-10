package org.academiadecodigo_powrangers;

import java.io.*;
import java.net.Socket;

public class ReadThread extends Thread {


    private Socket socket;
    private ClientChat client;

    private BufferedReader reader;
    InputStream input;
    public ReadThread(Socket socket, ClientChat client) {
        this.socket= socket;
        this.client= client;
    }




@Override
    public void run(){


    try {
        input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        System.out.println(reader.readLine());
    } catch (IOException ex) {
        System.out.println("Error getting input stream: " + ex.getMessage());
        ex.printStackTrace();
    }


    while (true) {
        try {
            String response = reader.readLine();
            System.out.println("\n" + response);

            // prints the username after displaying the server's message
            if (client.getUserName() != null) {
                System.out.print("[" + client.getUserName() + "]: ");
            }
        } catch (IOException ex) {
            System.out.println("Error reading from server: " + ex.getMessage());
            ex.printStackTrace();
            break;
        }
    }



}




}
