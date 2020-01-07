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
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    ImageButton cameraButton;
    ImageView imageView;
    ImageButton realButton;
    final int CAMERA_REQUEST_CODE = 1;
    int x, y, Redt, Bluet, Greent, Redb, Blueb, Greenb;
    TextView colorView;
    TextView colorView2;
    int i=0;
    Bitmap bitmap;
    ImageButton SendButton;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraButton = findViewById(R.id.cameraButton);
        imageView = findViewById(R.id.imageView);
        colorView = findViewById(R.id.color);
        colorView2 = findViewById(R.id.color2);
        SendButton = findViewById(R.id.btnsend);
        spinner = findViewById(R.id.spinner);
        realButton = findViewById(R.id.realButton);

        SendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(),PostActivity.class);
                if(text.equals("이름 선택")){
                    Toast.makeText(CameraActivity.this, "이름을 선택하세요", Toast.LENGTH_SHORT).show();
                }else{
//                    intent.putExtra("colortop",String.format("%03d",Redt)+ String.format("%03d",Bluet) + String.format("%03d",Greent));
//                    intent.putExtra("colorbot",String.format("%03d",Redb)+ String.format("%03d",Blueb) + String.format("%03d",Greenb));
//                    intent.putExtra("id", text);
//                    startActivity(intent);
                    String top = String.format("%03d",Redt)+ String.format("%03d",Greent) + String.format("%03d",Bluet);
                    String bottom = String.format("%03d",Redb)+ String.format("%03d",Greenb) + String.format("%03d",Blueb);
                    insertDress(text, top, bottom);
                    Toast.makeText(CameraActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
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

            } else {
                //권한 요청을 해야할 필요가 있는 경우(사용자가 DONT ASK ME AGIAN CHECK + DENY 선택)

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE);
            }
        }


        cameraButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(IsCameraAvailable()){
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    switch(v.getId()){
                        case R.id.cameraButton:
                            dispatchTakePictureIntent();
                            break;
                    }
                }

            }
        });

        realButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ClassActivity.class);
                startActivity(intent);
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
                    Greent = Color.green(pixel);
                    int rgb = Color.rgb(Redt, Greent, Bluet);
                    colorView.setBackgroundColor(rgb);
                    i++;
                }else{
                    Redb = Color.red(pixel);
                    Blueb = Color.blue(pixel);
                    Greenb = Color.green(pixel);
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

    //카메라로 촬영한 이미지를 파일로 저장
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //카메라 인텐트 실행
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.facebooklogin03.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }

        }
    }
    //사진 회전
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE) {
//            Bundle bundle = data.getExtras();
////            Bitmap bitmap = (Bitmap) bundle.get("data");
//            bitmap = (Bitmap) bundle.get("data");
//            imageView.setImageBitmap(bitmap);
//
//        }
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            Bitmap rotatedBitmap = null;
                            switch(orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }

                            imageView.setImageBitmap(rotatedBitmap);
                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }

    }

    //카메라 유무
    public boolean IsCameraAvailable(){
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void insertDress(String name, String top, String bottom){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        new JSONTask().execute("http://192.249.19.252:2380/post?func=image&id="  + name + "&date=" + currentDateandTime + "&top=" + top + "&bot=" + bottom);

    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("idName", "parkchaelin");
                //               jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    //URL url = new URL("http://192.249.19.252:2380/ucontacts");
                    URL url = new URL(urls[0]);
                    //연결을 함s
                    con = (HttpURLConnection) url.openConnection();
//                    Log.d("message", "어디까지1");
//
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Content-Type", "application/x-www-form-url-urlencoded");
//                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setRequestProperty("Accept-Charset", "UTF-8");
//                    con.setUseCaches(false);
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    Log.d("message", "어디까지2");

//                    서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    Log.d("message", "어디까지3");
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();
                    Log.d("message", "어디까지4");

                    reader = new BufferedReader(new InputStreamReader(stream));


                    StringBuffer buffer = new StringBuffer();
                    Log.d("message", "어디까지5");

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                        Log.d("message", "어디까지6");
                    }
                    Log.d("buffer", buffer.toString());
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            foodlistjsonParser(result);
//            textView.setText(result);//서버로 부터 받은 값을 출력해주는 부
        }
    }


}