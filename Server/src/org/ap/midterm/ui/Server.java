package org.ap.midterm.ui;

import org.ap.midterm.Models.GameManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    GameManager gameManager;
    public Server(int port){
        try (ServerSocket serverSocket = new ServerSocket(8080);){
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            

        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
