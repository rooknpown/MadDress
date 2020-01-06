package com.example.facebooklogin03;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.HashMap;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    TextView textView;
    RecyclerView recyclerView;
    ArrayList<Contact> contactList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        String topcolor = intent.getStringExtra("colortop");
        String botcolor = intent.getStringExtra("colorbot");
//        if (android.os.Build.VERSION.SDK_INT > 9) { //oncreate 에서 바로 쓰레드돌릴려고 임시방편으로 넣어둔소스
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        new JSONTask().execute("http://192.249.19.252:2380/post?id=parkchaelin&date=" + currentDateandTime + "&top=" + topcolor + "&bot=" + botcolor);



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
            foodlistjsonParser(result);
            textView.setText(result);//서버로 부터 받은 값을 출력해주는 부
        }
    }

    public void foodlistjsonParser(String jsonString) {
        String name = null;
        String phone = null;
        contactList = new ArrayList<>();
//        String[] arraysum = new String[2];
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("contact");
            for (int i = 0; i < jarray.length(); i++) {
////                HashMap map = new HashMap<>();
//                JSONObject jObject = jarray.getJSONObject(i);
//
//                name = jObject.optString("name");
//                Log.d("name", name);
//
//                phone = jObject.optString("phone");
//                Log.d("phone", phone);
//
//                arraysum[0] = name;
//                arraysum[1] = phone;
                JSONObject jObject = jarray.getJSONObject(i);
//                Contact contact = new Contact();
//
//                contact.setName(jObject.getString("name"));
//                contact.setPhone(jObject.getString("phone"));
                contactList.add(new Contact(jObject.getString("name"),jObject.getString("phone")));
                Log.d("name", jObject.getString("name"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showList();

    }
    public void showList(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new RVAdapter(contactList));  // Adapter 등록
    }

}
