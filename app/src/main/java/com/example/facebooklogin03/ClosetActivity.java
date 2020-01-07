package com.example.facebooklogin03;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ClosetActivity extends Activity {
    ArrayList<Contact> contactList;
    Boolean[] exist =  {false,false,false,false,false, false,false};
    String[] Topstr;
    String[] Botstr;
    Spinner spinner;
    ImageButton SendButton;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        spinner = findViewById(R.id.spinner);
        SendButton = findViewById(R.id.btnsend);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.name_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        SendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();
                if(text.equals("이름 선택")){
                    Toast.makeText(ClosetActivity.this, "이름을 선택하세요", Toast.LENGTH_SHORT).show();
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    String currentDateandTime = sdf.format(new Date());
                    new ClosetActivity.JSONTask().execute("http://192.249.19.252:2380/contacts?func=my&id=" + text + "&date=" + currentDateandTime);
                    Toast.makeText(ClosetActivity.this, "불러오기 성공", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }



    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");//POST방식으로 보냄
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));


                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
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
        }
    }

    public void foodlistjsonParser(String jsonString) {
        Topstr = new String[7];
        Botstr = new String[7];
        int j = 7;
        int length = 7;
        contactList = new ArrayList<>();
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("pastadress");
            length = Math.min(7, jarray.length());
            for (int i = jarray.length()-1; i >-1; i--) {
                if(j==0) break;
                j--;
                JSONObject jObject = jarray.getJSONObject(i);

                Topstr[j] = jObject.optString("top");
                Botstr[j] = jObject.optString("bot");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(new ClosetView(this, length, Topstr, Botstr));

    }

}

