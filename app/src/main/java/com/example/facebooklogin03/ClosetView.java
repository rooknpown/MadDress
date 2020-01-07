package com.example.facebooklogin03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.View;


public class ClosetView extends View{
    private Paint pt = new Paint();
    int mlength;
    String[] mtopstr;
    String[] mbotstr;
    private Paint pt2 = new Paint();

    public ClosetView(Context context, int length, String[] topstr, String[] botstr) {
        super(context);
        mlength = length;
        mtopstr = topstr;
        mbotstr = botstr;

        // create the Paint and set its color
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        pt2.setStrokeWidth(5);
        pt2.setStyle(Paint.Style.STROKE);
        pt2.setColor(Color.BLACK);
        if (mlength>0) {
            pt.setColor(Color.rgb(Integer.parseInt(mbotstr[6].substring(0, 3)), Integer.parseInt(mbotstr[6].substring(3, 6)), Integer.parseInt(mbotstr[6].substring(6, 9))));
            canvas.drawCircle(550, 670, 280, pt);
            canvas.drawCircle(550, 670, 280, pt2);
            pt.setColor(Color.rgb(Integer.parseInt(mtopstr[6].substring(0, 3)), Integer.parseInt(mtopstr[6].substring(3, 6)), Integer.parseInt(mtopstr[6].substring(6, 9))));
            canvas.drawCircle(550, 230, 200, pt);
            canvas.drawCircle(550, 230, 200, pt2);
        }
        pt2.setStrokeWidth(2);

        for(int k=5;k>6-mlength;k--) {
            if(k==2) break;
            pt.setColor(Color.rgb( Integer.parseInt(mbotstr[k].substring(0,3)), Integer.parseInt(mbotstr[k].substring(3,6)), Integer.parseInt(mbotstr[k].substring(6,9))));
            canvas.drawCircle(275+265*(5-k), 1190,70, pt);
            canvas.drawCircle(275+265*(5-k), 1190,70, pt2);

            pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
            canvas.drawCircle(275+265*(5-k) ,  1100,50, pt);
            canvas.drawCircle(275+265*(5-k), 1100,50, pt2);


        }

        for(int k=2;k>6-mlength;k--) {
            pt.setColor(Color.rgb( Integer.parseInt(mbotstr[k].substring(0,3)), Integer.parseInt(mbotstr[k].substring(3,6)), Integer.parseInt(mbotstr[k].substring(6,9))));
            canvas.drawCircle(275+265*(2-k) , 1390,70, pt);
            canvas.drawCircle(275+265*(2-k) , 1390,70, pt2);

            pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
            canvas.drawCircle(275+265*(2-k) , 1300,50, pt);
            canvas.drawCircle(275+265*(2-k) , 1300,50, pt2);

        }

    }
}