package com.example.facebooklogin03;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    Button cameraButton;
    ImageView imageView;
    final int CAMERA_REQUEST_CODE = 1;
    int x, y, Redt, Bluet, Greent, Redb, Blueb, Greenb;
    TextView colorView;
    TextView colorView2;
    int i=0;
    Bitmap bitmap;
    Button SendButton;
    Spinner spinner;
    ArrayAdapter arrayAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraButton = findViewById(R.id.cameraButton);
        imageView = findViewById(R.id.imageView);
        colorView = findViewById(R.id.color);
        colorView2 = findViewById(R.id.color2);
        SendButton = findViewById(R.id.btnsend);
        spinner = findViewById(R.id.spinner);

        SendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(),PostActivity.class);
                if(text.equals("이름 선택")){
                    Toast.makeText(CameraActivity.this, "이름을 선택하세요", Toast.LENGTH_SHORT).show();
                }else{
                    intent.putExtra("colortop",String.format("%03d",Redt)+ String.format("%03d",Bluet) + String.format("%03d",Greent));
                    intent.putExtra("colorbot",String.format("%03d",Redb)+ String.format("%03d",Blueb) + String.format("%03d",Greenb));
                    intent.putExtra("id", text);
                    startActivity(intent);
                }



            }
        });
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //권한이 부여되면 PERMISSION_GRANTED 거부되면 PERMISSION_DENIED 리턴

//권한 요청 할 필요가 있는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                //권한 요청을 해야할 필요가 있는 경우(사용자가 DONT ASK ME AGIAN CHECK + DENY 선택)

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE);

                //requestPermissions 메소드는 비동기적으로 동작한다. 왜냐면 이 권한 검사 및 요청 메소드는
                //메인 액티비티에서 동작하기떄문에(메인쓰레드) 사용자 반응성이 굉장히 중요한 파트이다. 여기서 시간을
                //오래 끌어버리면 사람들이 답답함을 느끼게 된다. requestPermissions의 결과로 콜백 메소드인
                //onRequestPermissionsResult()가 호출된다. 오버라이딩 메소드이다. Ctrl+O

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }




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



                if(i == 0) {
                    Redt = Color.red(pixel);
                    Bluet = Color.blue(pixel);
                    Greent = Color.blue(pixel);
                    int rgb = Color.rgb(Redt, Greent, Bluet);
                    colorView.setBackgroundColor(rgb);
                    i++;
                }else{
                    Redb = Color.red(pixel);
                    Blueb = Color.blue(pixel);
                    Greenb = Color.blue(pixel);
                    int rgb = Color.rgb(Redb, Greenb, Blueb);
                    colorView2.setBackgroundColor(rgb);
                    i = 0;
                }

                return false;
            }
        });

        //스피너로 이름 선택
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.name_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


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