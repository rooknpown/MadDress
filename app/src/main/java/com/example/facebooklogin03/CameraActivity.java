package com.example.facebooklogin03;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    Button cameraButton;
    ImageView imageView;
    final int CAMERA_REQUEST_CODE = 1;
    int x, y, Red, Blue, Green;
    TextView colorView;
    TextView colorView2;
    int i=0;
    Bitmap bitmap;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraButton = findViewById(R.id.cameraButton);
        imageView = findViewById(R.id.imageView);
        colorView = findViewById(R.id.color);
        colorView2 = findViewById(R.id.color2);


        cameraButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(IsCameraAvailable()){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }

            }
        });

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

                return false;
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap) bundle.get("data");
            bitmap = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    //카메라 유무
    public boolean IsCameraAvailable(){
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }



}
