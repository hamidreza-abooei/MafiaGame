package org.ap.midterm.Chat;

import javax.xml.crypto.Data;
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
        MessageWriter messageWriter = new MessageWriter(this);
        Thread messageWriterThread = new Thread(messageWriter);
        messageWriterThread.start();
        try (Socket socket = new Socket(host , port)){
//            conn = socket;
            System.out.println(socket);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output , true);
            writer.println(username);
            writer.println(rule);
            String readString;
            while (true){
                readString ="";
                int read ;
                do {
//                    System.out.println("here");
                    read = input.read();
//                    System.out.println(read);
//                    System.out.println("char: " + (char) read);
                    readString += (char) read;
//                    System.out.println(readString);
//                    System.out.println("sth red");

                } while ((char) read != '\n');
//                System.out.println("new string");
                System.out.print(readString);
//                System.out.println("that was the new string ``");
//                System.out.println(read);
//    `           writer.println();
//                writer.println("hi");
//                System.out.println("hi sent to server");
                if (readString.equalsIgnoreCase("endChat")){
                    messageWriter.stopWriting();
                    break;
                }



            }
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
//        System.out.println(message + "putMessage");
        writer.println(message);
//        System.out.println("Message has been sent.");
    }

}
