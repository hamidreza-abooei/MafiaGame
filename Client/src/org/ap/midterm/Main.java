package org.ap.midterm;

import org.ap.midterm.Chat.ChatClient;
import org.ap.midterm.Client.Client;
/**
 * @author Hamidreza Abooei
 */
public class Main {
    /**
     * main of clients
     * @param args input argument
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.startClient("127.0.0.1" , 8080);
//        ChatClient chatClient = new ChatClient("localhost" , 6050 , "Ali" , "GodFather");
//        ChatClient chatClient = new ChatClient("localhost" , 6050 , "Reza" , "DrLecter");
//        Thread chatClientThread = new Thread(chatClient);
//        chatClientThread.start();
    }
}
