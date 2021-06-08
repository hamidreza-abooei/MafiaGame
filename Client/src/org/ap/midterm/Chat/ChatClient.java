package org.ap.midterm.Chat;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author Hamidreza Abooei
 */
public class ChatClient implements Runnable{

    private final String host;
    private final int port;
//    private WriteThread writeThread;
    private String username;
    private String rule;
//    private Socket conn;
    private BufferedReader reader;
    private String message;
    private PrintWriter writer;

    /**
     * constructor
     * @param host host ip address
     * @param port port
     * @param rule rule
     * @param username username
     */
    public ChatClient(String host, int port  , String username , String rule){
        this.host = host;
        this.port = port;
        this.username = username;
        this.rule = rule;
    }

    /**
     * run this thread
     */
    @Override
    public synchronized void run() {
        try (Socket socket = new Socket(host , port)){
//            conn = socket;
            System.out.println(socket);
            InputStream input = socket.getInputStream();
            writer = new PrintWriter(socket.getOutputStream());

//            reader = new BufferedReader(new InputStreamReader(input));
//
//            String response = reader.readLine();
//            System.out.println(response);



//            new SocketTest(conn).start();
//            SocketTest socketTest = new SocketTest(conn).start();
//            socketTest.run();
//            System.out.println("Connected to the chat server");
//            new SocketTest(socket).start();

//            DataInputStream in = new DataInputStream(socket.getInputStream());
//            System.out.println(in.readUTF());
//            Thread readThread = new Thread(new ReadThread(socket , this));
//            writeThread = new WriteThread(socket , username , rule);
//            Thread write = new Thread(writeThread);
//            readThread.start();
//            write.start();
        }catch (IOException e){
            System.err.println("Error has been occurred in chat server I/O.");
        }
    }
//    public void stopWriting(){
//        writeThread.stopThisThread();
//    }
    public void putMessage(String message){
//        this.message = message;
        writer.println(message);
    }

}
