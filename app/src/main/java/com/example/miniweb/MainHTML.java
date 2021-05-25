package com.example.miniweb;

import android.util.Log;

import java.io.File;

public class MainHTML {
    private String text;

    public MainHTML(String inputurl, String ldir, String absolutedir, File[] file){

        pre();
        for (File value : file) {
            String outurl = removeFirstChars(ldir, absolutedir.length()) + "/" + value.getName();
            if (inputurl.trim().equalsIgnoreCase("")) {
                outurl = value.getName();
            }
            text = text + "<p><a href=\"" + outurl + "\"> - " + value.getName() + "</a></p>" + "\n";
        }
        post();
    }

    private void pre(){
        text = "<!DOCTYPE html>\n" +
                "  <head>\n" +
                "<meta charset=\"UTF-8\" />\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n" +
                "    <title>Texting</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "<h1>Local Files</h1>\n" +
                "<hr>\n";
    }

    private void post(){
        text = text + " " +
                "<hr>\n" +
                "" +
                "  </body>\n" +
                "</html>\n";
    }

    public static String removeFirstChars(String str, int chars) {
        return str.substring(chars);
    }

    public String getHTML(){
        return text;
    }
}