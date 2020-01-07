package com.example.facebooklogin03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassView extends View{
    private Paint[] toppt = new Paint[20];
    private Paint[] botpt = new Paint[20];
    private Paint pt = new Paint();
    private Paint pt2 = new Paint();
    Boolean[] mexist;
    String[] mtopstr;
    String[] mbotstr;
    HashMap<Integer, String> tablemap;
    Boolean[] mateexist = new Boolean[20];

    public ClassView(Context context, Boolean[] exist, String[] topstr, String[] botstr) {
        super(context);
        mexist = exist;
        mtopstr = topstr;
        mbotstr = botstr;
        HashMap<Integer, Integer> tablemap = new HashMap();
        tablemap.put(0, 0);
        tablemap.put(1, 5);
        tablemap.put(2, 3);
        tablemap.put(3, 2);
        tablemap.put(4, 4);
        tablemap.put(5, 1);
        tablemap.put(6, 10);
        tablemap.put(7, 11);
        tablemap.put(8, 8);
        tablemap.put(9, 9);
        tablemap.put(10, 6);
        tablemap.put(11, 7);
        tablemap.put(12, 16);
        tablemap.put(13, 17);
        tablemap.put(14, 18);
        tablemap.put(15, 19);
        tablemap.put(16, 12);
        tablemap.put(17, 13);
        tablemap.put(18, 14);
        tablemap.put(19, 15);
        for(int i=0;i<20;i++){
            if(mexist[tablemap.get(i)]){
                mateexist[i] = true;
            }
            else{
                mateexist[i] = false;
            }
        }
        if(!(mexist[0]&&mexist[4]&&mexist[8])){
            mateexist[0] = false;
            mateexist[4] = false;
            mateexist[8] = false;
        }

        // create the Paint and set its color
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        tablemap = new HashMap<>();
        tablemap.put(0, "박유찬");
        tablemap.put(1, "김영웅");
        tablemap.put(2, "기태헌");
        tablemap.put(3, "정슬인");
        tablemap.put(4, "유희승");
        tablemap.put(5, "김효일");
        tablemap.put(6, "손현준");
        tablemap.put(7, "윤찬우");
        tablemap.put(8, "김강산");
        tablemap.put(9, "임시현");
        tablemap.put(10, "김우림");
        tablemap.put(11, "김소연");
        tablemap.put(12, "이현구");
        tablemap.put(13, "김병현");
        tablemap.put(14, "김태영");
        tablemap.put(15, "정예준");
        tablemap.put(16, "이재윤");
        tablemap.put(17, "박채린");
        tablemap.put(18, "권형근");
        tablemap.put(19, "이현재");
        int k =0;
        pt2.setStrokeWidth(2);
        pt2.setStyle(Paint.Style.STROKE);
        pt2.setColor(Color.BLACK);
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++){
                if(mexist[k]) {
                    pt.setColor(Color.rgb( Integer.parseInt(mbotstr[k].substring(0,3)), Integer.parseInt(mbotstr[k].substring(3,6)), Integer.parseInt(mbotstr[k].substring(6,9))));
                    canvas.drawCircle(100 + 250 * j, 150 + 310 * i, 70, pt);
                    int yiq = (Integer.parseInt(mbotstr[k].substring(0,3)) * 299 + Integer.parseInt(mbotstr[k].substring(3,6)) * 587 + Integer.parseInt(mbotstr[k].substring(6,9)) * 114) / 1000;
                    if(yiq >= 128)
                        pt.setColor(Color.BLACK);
                    else
                        pt.setColor(Color.WHITE);
                    pt.setTextSize(35);
                    //이름출력
                    canvas.rotate(90, 100 + 250 * j, 150 + 310 * i-45);
                    canvas.drawText(tablemap.get(k), 100 + 250 * j, 150 + 310 * i-45, pt);
                    canvas.rotate(-90, 100 + 250 * j, 150 + 310 * i-45);

                    //원 테두리 출력
                    canvas.drawCircle(100 + 250 * j, 150 + 310 * i, 70, pt2);

                    pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
                    canvas.drawCircle(190 + 250 * j, 150 + 310 * i, 50, pt);
                    yiq = (Integer.parseInt(mtopstr[k].substring(0,3)) * 299 + Integer.parseInt(mtopstr[k].substring(3,6)) * 587 + Integer.parseInt(mtopstr[k].substring(6,9)) * 114) / 1000;
                    if(yiq >= 128)
                        pt.setColor(Color.BLACK);
                    else
                        pt.setColor(Color.WHITE);
//                    pt.setTextSize(40);
                    if(mateexist[k]){
                        canvas.rotate(90, 190 + 250 * j, 150 + 310 * i-23);
                        canvas.drawText("^_^", 190 + 250 * j, 150 + 310 * i-23, pt);
                        canvas.rotate(-90, 190 + 250 * j, 150 + 310 * i-23);
                    }else{
                        canvas.rotate(90, 190 + 250 * j, 150 + 310 * i-30);
                        canvas.drawText("ㅠㅠ", 190 + 250 * j, 150 + 310 * i-30, pt);
                        canvas.rotate(-90, 190 + 250 * j, 150 + 310 * i-30);
                    }



                    canvas.drawCircle(190 + 250 * j, 150 + 310 * i, 50, pt2);


                }
                k++;
            }
        }

        for(int i=0;i<2;i++) {
            for(int j=0;j<4;j++){
                if(mexist[k]) {
                    pt.setColor(Color.rgb( Integer.parseInt(mbotstr[k].substring(0,3)), Integer.parseInt(mbotstr[k].substring(3,6)), Integer.parseInt(mbotstr[k].substring(6,9))));
                    canvas.drawCircle(100 + 250 * j, 1400 + 310 * i, 70, pt);

                    int yiq = (Integer.parseInt(mbotstr[k].substring(0,3)) * 299 + Integer.parseInt(mbotstr[k].substring(3,6)) * 587 + Integer.parseInt(mbotstr[k].substring(6,9)) * 114) / 1000;
                    if(yiq >= 128)
                        pt.setColor(Color.BLACK);
                    else
                        pt.setColor(Color.WHITE);
                    pt.setTextSize(35);
                    canvas.rotate(90, 100 + 250 * j, 1400 + 310 * i-45);
                    canvas.drawText(tablemap.get(k), 100 + 250 * j, 1400 + 310 * i-45, pt);
                    canvas.rotate(-90, 100 + 250 * j, 1400 + 310 * i-45);

                    canvas.drawCircle(100 + 250 * j, 1400 + 310 * i, 70, pt2);

                    pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
                    canvas.drawCircle(190 + 250 * j, 1400 + 310 * i, 50, pt);

                    yiq = (Integer.parseInt(mtopstr[k].substring(0,3)) * 299 + Integer.parseInt(mtopstr[k].substring(3,6)) * 587 + Integer.parseInt(mtopstr[k].substring(6,9)) * 114) / 1000;
                    if(yiq >= 128)
                        pt.setColor(Color.BLACK);
                    else
                        pt.setColor(Color.WHITE);

                    if(mateexist[k]){
                        canvas.rotate(90, 190 + 250 * j, 1400 + 310 * i-23);
                        canvas.drawText("^_^", 190 + 250 * j, 1400 + 310 * i-23, pt);
                        canvas.rotate(-90, 190 + 250 * j, 1400 + 310 * i-23);
                    } else{
                        canvas.rotate(90, 190 + 250 * j, 1400 + 310 * i-30);
                        canvas.drawText("ㅠㅠ", 190 + 250 * j, 1400 + 310 * i-30, pt);
                        canvas.rotate(-90, 190 + 250 * j, 1400 + 310 * i-30);
                    }



                    canvas.drawCircle(190 + 250 * j, 1400 + 310 * i, 50, pt2);

                }
                k++;
            }
        }


    }
}