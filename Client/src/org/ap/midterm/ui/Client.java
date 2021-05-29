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
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to server.");
            while (true){
                String read = in.readUTF();
                System.out.println(read);
                String entered = scanner.nextLine();
                out.writeUTF(entered);
                if (read.equalsIgnoreCase("end")){
                    out.writeUTF("end");
                    break;
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
