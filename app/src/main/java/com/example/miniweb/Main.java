package com.example.miniweb;

import android.content.Context;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {
    public String out;
    private Context context;
    public String ldir;

    public Main(Context context) {
        this.context = context;
    }

    public void setPort(int port,String ldir) {
        this.port = port;this.ldir=ldir;
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
            out = "Работает по адрессу http://" + NetworkInterface.getNetworkInterfaces() + ":" + port ;

            while (true) {
                Socket socket = serverSocket.accept();
                Handle handle = new Handle(socket, port, index,context,ldir);
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
