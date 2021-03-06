package org.ap.midterm.Client;

import org.ap.midterm.Chat.ChatClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * @author Hamidreza Abooei
 */
public class Client {
    private String rule;
    /**
     * constructor
     */
    public Client(){

    }

    /**
     * start the client
     * @param ipAddress ip address of server that is localhost in this program
     * @param port port that server is running on it
     */
    public void startClient(String ipAddress, int port){
        try (Socket socket = new Socket(ipAddress,port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to server.");

            while (true){
                String read = in.readUTF();
//                if (read.equalsIgnoreCase("readPlusUsername"))
//
                if (read.equalsIgnoreCase( "read")){
                    System.out.println("Waiting for enter");
                    String entered = scanner.nextLine();
//                    System.out.println("Entered message" + entered);
                    out.writeUTF(entered);
                }else if (read.equalsIgnoreCase("startChat")){
                    String username = in.readUTF();
                    String chatPort = in.readUTF();

                    ChatClient chatClient = new ChatClient(ipAddress,Integer.parseInt(chatPort) , username , rule);
                    Thread chatClientThread = new Thread(chatClient);
                    chatClientThread.start();
                }else if(read.equalsIgnoreCase("clientRule")){
                    rule = in.readUTF();
                    System.out.println("Your rule is :" + rule);
                }else{
                    System.out.println(read);
                    if (read.equalsIgnoreCase("end")){
                        out.writeUTF("end");
                        break;
                    }
                }
            }
            System.out.println("end client");
        } catch (UnknownHostException e) {
            System.err.println("Something wrong in Host Known ");
        } catch (ConnectException e){
            System.err.println("Couldn't connect to Server");
        } catch (SocketException e) {
            System.err.println ("Server Not Responding");
        } catch (IOException e) {
            System.err.println("Some went Wrong in I/O");
        } catch (IllegalArgumentException e){
            System.err.println("Some went Wrong in Client starting");
        } catch (SecurityException e){
            System.err.println(e.toString());
        }

    }
}
