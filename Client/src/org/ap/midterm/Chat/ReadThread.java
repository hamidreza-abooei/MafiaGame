package org.ap.midterm.Chat;

import java.io.*;
import java.net.Socket;

/**
 * @author Hamidreza Abooei
 */
public class ReadThread implements Runnable{

    private Socket connection ;
    private ChatClient server;
//    private BufferedReader reader;

    /**
     * constructor
     * @param connection for read other clients messages
     */
    public ReadThread(Socket connection , ChatClient server){
        this.connection = connection;
        this.server = server;
        System.out.println(connection);
//        try {
//            InputStream input = connection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(input));
//            System.out.println(reader);
//        } catch (IOException ex) {
//            System.out.println("Error getting input stream: " + ex.getMessage());
//            ex.printStackTrace();
//        }

    }



    private synchronized void startReadThread() {
        System.out.println("run this thread");
        try(InputStream in = (connection.getInputStream())) {
            System.out.println("Hello");
            System.out.println(in.read());
        } catch (IOException e) {
            System.err.println("Error getting input stream: " + e.getMessage());
        }
    }

    /**
     * Run this thread
     */
    @Override
    public synchronized void run() {
        startReadThread();
//        while (true) {
//            try {
//                String read = reader.readLine();
//                System.out.println(read);
////                if(read.equalsIgnoreCase("stopChatClients")){
////                    server.stopWriting();
////                    break;
////                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }

    }
}
