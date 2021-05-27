package org.ap.midterm;

import org.ap.midterm.ui.Client;

public class Main {

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient("127.0.0.1" , 8080);

    }
}
