package org.ap.midterm.ui;

import java.awt.desktop.SystemEventListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public Client(){

    }

    public void startClient(String ipAddress, int port){
        try (Socket socket = new Socket(ipAddress,port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
//            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to server.");
            while (true){
                System.out.println("entered sth");
//                String entered = scanner.nextLine();
                String read = in.readUTF();
                if (read.equalsIgnoreCase("end")){
                    out.writeUTF("end");
                    break;
                }
                System.out.println("end client");
            }
        } catch (UnknownHostException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (IllegalArgumentException e){
            System.err.println(e.toString());
        } catch (SecurityException e){
            System.err.println(e.toString());
        }

    }
}
