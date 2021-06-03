package com.example.miniweb;

import android.os.AsyncTask;
import android.widget.TextView;

public class Logger extends AsyncTask<Void,String,Void> {
    public static String textt ="";
    TextView textView;
    public Logger (TextView textView){
        this.textView = textView;
    }
    public static void setText(String text){
        textt = textt + "Server > " + text + " \n";
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(true){
            try {
                Thread.sleep(1000);
                publishProgress(textt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    protected void onProgressUpdate(String... values) {
        textView.setText(values[0]);
    }
}
