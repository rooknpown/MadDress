package com.example.facebooklogin03;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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
import java.util.HashMap;
import java.util.Locale;

public class ClassActivity extends Activity {
    ArrayList<Contact> contactList;
    Boolean[] exist =  {false,false,false,false,false, false,false,false,false,false, false,false,false,false,false, false,false,false,false,false};
    String[] Topstr;
    String[] Botstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        new ClassActivity.JSONTask().execute("http://192.249.19.252:2380/contacts?"  + "&date=" + currentDateandTime);
    }
    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("idName", "parkchaelin");
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
                    con.setRequestMethod("GET");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
////                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
//                    con.setRequestProperty("Content-Type", "application/x-www-form-url-urlencoded");
////                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
//                    con.setRequestProperty("Accept-Charset", "UTF-8");
////                    con.setUseCaches(false);
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    Log.d("message", "어디까지2");

                    //서버로 보내기위해서 스트림 만듬
//                    OutputStream outStream = con.getOutputStream();
//                    Log.d("message", "어디까지3");
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌

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
        }
    }

    public void foodlistjsonParser(String jsonString) {
        Topstr = new String[20];
        Botstr = new String[20];
        HashMap<String, Integer> tablemap = new HashMap();
        tablemap.put("박유찬", 0);
        tablemap.put("김영웅", 1);
        tablemap.put("기태헌", 2);
        tablemap.put("정슬인", 3);
        tablemap.put("유희승", 4);
        tablemap.put("김효일", 5);
        tablemap.put("손현준", 6);
        tablemap.put("윤찬우", 7);
        tablemap.put("김강산", 8);
        tablemap.put("임시현", 9);
        tablemap.put("김우림", 10);
        tablemap.put("김소연", 11);
        tablemap.put("이현구", 12);
        tablemap.put("김병현", 13);
        tablemap.put("김태영", 14);
        tablemap.put("정예준", 15);
        tablemap.put("이재윤", 16);
        tablemap.put("박채린", 17);
        tablemap.put("권형근", 18);
        tablemap.put("이현재", 19);
        String name = null;
        Integer loc = null;
        contactList = new ArrayList<>();
        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("pastdress");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);
//
                name = jObject.optString("name");
                loc = tablemap.get(name);
                exist[loc] = true;
                Topstr[loc] = jObject.optString("top");
                Botstr[loc] = jObject.optString("bot");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(new ClassView(this, exist, Topstr, Botstr));
    }

}


