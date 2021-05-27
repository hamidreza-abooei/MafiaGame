package org.ap.midterm;

import org.ap.midterm.ui.Server;

public class Main{

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(8080);

    }
}
