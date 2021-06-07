package org.ap.midterm.ui;

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
    }

    /**
     * run timer
     */
    @Override
    public void run() {
        try {
            Thread.sleep(18000);
            server.closeServer();
        } catch (InterruptedException e) {
            System.err.println("Timer interrupted");
        }

    }
}
