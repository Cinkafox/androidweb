package com.example.miniweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Console extends AppCompatActivity {
    public TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        textView = findViewById(R.id.console);
        Thread thread = new Thread(){ public void run(){while (true){textView.setText(new Logger().getText());}}};
        thread.start();
        Log.i("Console","open");
        Button back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Console.this,MainActivity.class));
            }
        });
    }

}
