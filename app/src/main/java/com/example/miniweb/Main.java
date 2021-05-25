package com.example.miniweb;

import android.content.Context;
import android.net.Network;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

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
    public String ip =getIPAddress(true);
    public void run(){
        try {
            String index = "";
            ServerSocket serverSocket = new ServerSocket(port);
            out = "Работает по адрессу http://" + ip + ":" + port ;

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
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }

}


