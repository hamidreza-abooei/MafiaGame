package org.ap.midterm.Chat;

import java.util.Scanner;
/**
 * @author Hamidreza Abooei
 */
public class MessageWriter implements Runnable{
    // fields
    private boolean running;
    private ChatClient chatClient;
    private Scanner scanner;
    private volatile boolean run = true;

    /**
     * Constructor
     * @param chatClient related chat client
     */
    public MessageWriter(ChatClient chatClient){
        this.chatClient = chatClient;
        running = true;
        scanner = new Scanner(System.in);
    }

    /**
     * run this thread
     */
    @Override
    public synchronized void run() {
        while (running){

            String message = scanner.nextLine();
            chatClient.putMessage(message);

        }
        System.out.println("out of runing");
    }

    /**
     * stop writing
     */
    public void stopWriting(){
        running = false;
//        System.out.println("I am here");
//        run = false;
//        scanner.notify();

//        scanner.close();
//        System.out.println("out");
    }
}
