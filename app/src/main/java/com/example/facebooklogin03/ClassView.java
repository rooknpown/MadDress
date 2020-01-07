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

    public ClassView(Context context, Boolean[] exist, String[] topstr, String[] botstr) {
        super(context);
        mexist = exist;
        mtopstr = topstr;
        mbotstr = botstr;

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
                    pt.setTextSize(30);
                    canvas.rotate(90, 100 + 250 * j, 150 + 310 * i-40);
                    canvas.drawText(tablemap.get(k), 100 + 250 * j, 150 + 310 * i-40, pt);
                    canvas.rotate(-90, 100 + 250 * j, 150 + 310 * i-40);

                    canvas.drawCircle(100 + 250 * j, 150 + 310 * i, 70, pt2);

                    pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
                    canvas.drawCircle(190 + 250 * j, 150 + 310 * i, 50, pt);
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
                    pt.setTextSize(30);
                    canvas.rotate(90, 100 + 250 * j, 1400 + 310 * i-40);
                    canvas.drawText(tablemap.get(k), 100 + 250 * j, 1400 + 310 * i-40, pt);
                    canvas.rotate(-90, 100 + 250 * j, 1400 + 310 * i-40);
                    canvas.drawCircle(100 + 250 * j, 1400 + 310 * i, 70, pt2);

                    pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
                    canvas.drawCircle(190 + 250 * j, 1400 + 310 * i, 50, pt);
                    canvas.drawCircle(190 + 250 * j, 1400 + 310 * i, 50, pt2);

                }
                k++;
            }
        }


    }
}