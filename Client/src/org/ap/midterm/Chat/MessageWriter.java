package org.ap.midterm.Chat;

import java.util.Scanner;

public class MessageWriter implements Runnable{
    private boolean running;
    private ChatClient chatClient;
    public MessageWriter(ChatClient chatClient){
        this.chatClient = chatClient;
        running = true;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in) ;
        while (running){
            String message = scanner.nextLine();
//            System.out.println(message);
//            System.out.println(message);
            chatClient.putMessage(message);
        }
    }
    public void stopWriting(){
        running = false;
//        notifyAll();
    }
}
