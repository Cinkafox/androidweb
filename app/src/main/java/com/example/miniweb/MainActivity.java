package com.example.miniweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;

        final TextView textView = findViewById(R.id.TextPanel);
        Button button = findViewById(R.id.button);
        final Main main = new Main();
        final TextView textView1 = findViewById(R.id.port);
        textView.setText("1");
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("test");
                int port = 8080;
                try {
                    port = Integer.parseInt(getText(textView1));
                } catch (NumberFormatException e) {
                    textView.setText(e.getLocalizedMessage());
                }
                Toast.makeText(context, port+"", Toast.LENGTH_SHORT).show();
                main.setPort(port);
                main.start();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    textView.setText(main.getOut());
            }
        };
        button.setOnClickListener(oclBtnOk);

    }

    private String getText(TextView textView) {
        return textView.getText().toString();
    }

    private void setText(String in) {

    }
}
