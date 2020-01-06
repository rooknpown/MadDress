package com.example.facebooklogin03;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Bitmap bitmap;
    int x, y, Red, Blue, Green;
    TextView colorView;
    TextView colorView2;
    int i=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        colorView = findViewById(R.id.color);
        colorView2 = findViewById(R.id.colorView2);

        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = (int) event.getX();
                y = (int) event.getY();
                Log.d("x와 y 좌표", "x: "+x+" y: "+y);
                int width = v.getWidth();
                int height = v.getHeight();
                bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                Bitmap resize_bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
//                int pixel = bitmap.getPixel(x,y);
                int pixel = resize_bitmap.getPixel(x,y);


                Red = Color.red(pixel);
                Blue = Color.blue(pixel);
                Green = Color.blue(pixel);
                int rgb = Color.rgb(Red, Green, Blue);
                if(i == 0) {
                    colorView.setBackgroundColor(rgb);
                    i++;
                }else{
                    colorView2.setBackgroundColor(rgb);
                    i = 0;
                }

                textView.setText("R: "+ Red + " B: "+ Blue + " G: "+ Green);



                return false;
            }
        });






    }
}
