package org.ap.midterm;

import org.ap.midterm.ui.Server;

/**
 * @author Hamidreza Abooei
 */
public class Main{
    /**
     * main of Game
     * @param args input arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(8080);

//        ChatServer chatServer = new ChatServer(6050 , true);
//        Timer timer = new Timer(chatServer);
//        Thread timerThread = new Thread(timer);
//        timerThread.start();
//        Thread chatServerThread = new Thread(chatServer);
//        chatServerThread.start();
    }
}
