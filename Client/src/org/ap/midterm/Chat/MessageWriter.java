package org.ap.midterm.Chat;

import java.util.Scanner;
/**
 * @author Hamidreza Abooei
 */
public class MessageWriter implements Runnable{
    // fields
    private boolean running;
    private ChatClient chatClient;

    /**
     * Constructor
     * @param chatClient related chat client
     */
    public MessageWriter(ChatClient chatClient){
        this.chatClient = chatClient;
        running = true;
    }

    /**
     * run this thread
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in) ;
        while (running){
            String message = scanner.nextLine();
            chatClient.putMessage(message);
        }
    }

    /**
     * stop writing
     */
    public void stopWriting(){
        running = false;
    }
}
