package com.example.miniweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import static com.example.miniweb.GetIpAddress.getIPAddress;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private boolean running = true;
    private String ldir = android.os.Environment.getExternalStorageDirectory() + "";

    boolean off = false;
    Context context = this;
    Main main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Activity activity = (Activity) context;
        final TextView textView = findViewById(R.id.TextPanel);
        final TextView textInfo = findViewById(R.id.textinfo);

        final Button button = findViewById(R.id.button);

        final Spinner spinner = findViewById(R.id.spinner);
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            textView.setText(Lang.TRY_AGAIN);
        }

        main = new Main(this,spinner);
        final TextView textView1 = findViewById(R.id.port);
        textView.setText(Lang.WELCOME);
        main.setDirForAdapter(ldir);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view,
                                                                         int position, long id) {
                                                  Object item = adapterView.getItemAtPosition(position);
                                                  if (item != null) {
                                                      ldir = item.toString();
                                                  }
                                              }
                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          });



            View.OnClickListener oclBtnOk = new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                        if(!off) {
                            main.on();
                            System.out.println("1");
                            button.setText("Стоп");


                            spinner.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                            main.setdir(ldir);
                            textInfo.setText("");
                            running = false;

                            textView.setText("test");
                            int port = 8080;
                            try {
                                port = Integer.parseInt(getText(textView1));
                            } catch (NumberFormatException e) {
                                textView.setText(e.getLocalizedMessage());
                            }

                            main.setPort(port);
                            main.start();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            textView.setText(main.getOut());
                            Logger logger = new Logger(textView);
                            logger.execute();
                            off = true;
                            System.out.println(off);
                            qr("http://" + getIPAddress(true) + ":" + port);
                        }else {
                            off = false;
                            System.out.println("2");
                            spinner.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.VISIBLE);
                            Logger.setText(Lang.STOP);
                            main.off();
                            button.setText("Пуск!");
                            main = new Main(context,spinner);
                        }

                    }


            };

            button.setOnClickListener(oclBtnOk);




    }

    private String getText(TextView textView) {
        return textView.getText().toString();
    }

    public void qr(String in){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(in, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}