package org.ap.midterm.Chat;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketTest extends Thread{
    private Socket connection;
    public SocketTest (Socket connection){
        this.connection = connection;
    }

    @Override
    public synchronized void run (){
        try(DataInputStream in = new DataInputStream(connection.getInputStream())){
            System.out.println(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
