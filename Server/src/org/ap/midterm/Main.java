package org.ap.midterm;

import org.ap.midterm.ui.Chat.ChatServer;

/**
 * @author Hamidreza Abooei
 */
public class Main{
    /**
     * main of Game
     * @param args input arguments
     */
    public static void main(String[] args) {
//        Server server = new Server();
//        server.startServer(8080);
        ChatServer chatServer = new ChatServer(6050);
        Thread chatServerThread = new Thread(chatServer);
        chatServerThread.start();
    }
}
