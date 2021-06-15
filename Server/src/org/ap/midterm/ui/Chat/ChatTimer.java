package org.ap.midterm.ui.Chat;

import org.ap.midterm.ui.Chat.ChatServer;

/**
 * @author Hamidreza Abooei
 */
public class ChatTimer implements Runnable{
    private ChatServer server;
    private int length;

    /**
     * constructor
     * @param server chat server
     * @param length length second
     */
    public ChatTimer(ChatServer server , int length){
        this.server = server;
        this.length = length * 1000;
        System.out.println("timer added");
    }

    /**
     * run timer
     */
    @Override
    public void run() {
        try {
//            System.out.println("timer initiated");
            Thread.sleep(length);
            System.out.println("Chat timer ended");
            server.closeServer();
        } catch (InterruptedException e) {
            System.err.println("Timer interrupted");
        }

    }
}
