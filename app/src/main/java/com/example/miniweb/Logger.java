package com.example.miniweb;

import android.widget.TextView;

public class Logger {
    public static String textt ="";
    public static void setText(String text){
        textt = textt + "Server > " + text + " \n";
    }
    public static String getText(){
        return textt;
    }
}
