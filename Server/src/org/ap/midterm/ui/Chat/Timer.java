package org.ap.midterm.ui.Chat;

import org.ap.midterm.ui.Chat.ChatServer;

/**
 * @author Hamidreza Abooei
 */
public class Timer implements Runnable{
    private ChatServer server;

    /**
     * constructor
     * @param server chat server
     */
    public Timer(ChatServer server){
        this.server = server;
        System.out.println("timer added");
    }

    /**
     * run timer
     */
    @Override
    public void run() {
        try {
            System.out.println("timer initiated");
            Thread.sleep(18000);
            System.out.println("timer ended");
            server.closeServer();
        } catch (InterruptedException e) {
            System.err.println("Timer interrupted");
        }

    }
}
