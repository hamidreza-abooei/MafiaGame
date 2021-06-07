package org.ap.midterm.Chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Struct;
import java.util.Scanner;

/**
 * @author Hamidreza Abooei
 */
public class WriteThread implements Runnable{
    private Socket connection;
    private Scanner scanner;
    private boolean running;
    private String rule;
    private String username;

    /**
     * constructor
     * @param connection socket to write massage in chat room
     * @param username username
     * @param rule rule
     */
    public WriteThread(Socket connection , String username , String rule){
        this.connection = connection;
        this.scanner = new Scanner(System.in);
        this.username = username;
        this.rule = rule;
        running = true;

    }
    /**
     * Run this thread
     */
    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())){
            out.writeUTF(username);
            out.writeUTF(rule);
            while (running){
                out.writeUTF( scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Error in I/O has been occurred");
        }

    }
    public void stopThisThread(){
        running = false;
        notifyAll();
    }
}
