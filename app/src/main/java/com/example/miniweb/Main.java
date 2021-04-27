package com.example.miniweb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {
    public String out;

    public void setPort(int port) {
        this.port = port;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int port;
    public String index = "";
    public void run(){
        try {
            String index = "";
            ServerSocket serverSocket = new ServerSocket(port);
            out = "done!";
            while (true) {
                Socket socket = serverSocket.accept();
                Handle handle = new Handle(socket, port, index);
                handle.start();
            }
        }catch (IOException e){
            out = e.getMessage();
            e.printStackTrace();
        }
    }

    public String getOut(){
        return out;
    }

}
