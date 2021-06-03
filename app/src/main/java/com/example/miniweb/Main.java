package com.example.miniweb;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Thread {
    private String out;
    private Context context;
    private String ldir;
    private Spinner spinner;
    private boolean on = true;





    Main(Context context, Spinner spinner) {
        this.context = context;
        this.spinner = spinner;



    }
    void setdir(String ldir){
        this.ldir = ldir;
    }

    void setPort(int port) {
        this.port = port;
    }
    void on(){on=true;}
    void off(){on=false;}
    boolean getOn(){return on;}




    private int port;
    private String ip =GetIpAddress.getIPAddress(true);
    public void run(){
        try {
            String index = "";
            ServerSocket serverSocket = new ServerSocket(port);
            out = Lang.WORKING + ip + ":" + port ;
            Logger.setText(out);

            while (on) {
                Socket socket = serverSocket.accept();
                Handle handle = new Handle(socket, port, index,context,ldir);

                handle.start();
            }
        }catch (IOException e){
            out = e.getMessage();
            e.printStackTrace();
        }
    }

    String getOut(){
        return out;
    }

    private File[] localFiles(String dir){
        File file = new File(dir);
        return file.listFiles();
    }
    private ArrayAdapter adapter(File[] file,String ldir){
        String[] temp = new String[file.length];
        int i = 0;
        for(File st:file){
            temp[i] = st.getPath().substring(ldir.length());
            i++;
        }
        return new ArrayAdapter<String>(context,R.layout.list,temp);
    }

    void setDirForAdapter(String ldir) {
        ArrayAdapter adapter = adapter(localFiles(ldir),ldir);
        adapter.setDropDownViewResource(R.layout.list);
        spinner.setAdapter(adapter);
    }
}


