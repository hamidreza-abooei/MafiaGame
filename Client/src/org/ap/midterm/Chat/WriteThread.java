package org.ap.midterm.Chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Hamidreza Abooei
 */
public class WriteThread implements Runnable{
    private Socket connection;
    private Scanner scanner;
    private String username;

    /**
     * constructor
     * @param connection socket to write massage in chat room
     * @param username username of client
     */
    public WriteThread(Socket connection , String username){
        this.connection = connection;
        this.scanner = new Scanner(System.in);
        this.username = username;
    }
    /**
     * Run this thread
     */
    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())){
            while (true){
                out.writeUTF("[" + username + "]: "+ scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Error in I/O has been occurred");
        }

    }
}
