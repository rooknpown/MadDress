package com.example.facebooklogin03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class ClassView extends View{
    private Paint[] toppt = new Paint[20];
    private Paint[] botpt = new Paint[20];
    private Paint pt = new Paint();
    Boolean[] mexist;
    String[] mtopstr;
    String[] mbotstr;

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
        Log.d("aaaaaaaaaaaaaaaaaaaaa", "gggggggggg");
        int k =0;
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++){
                if(mexist[k]) {
                    Log.d("aaaaaaaaaaaaaaaaaaaaa", mbotstr[k].substring(0,3));
                    pt.setColor(Color.rgb( Integer.parseInt(mbotstr[k].substring(0,3)), Integer.parseInt(mbotstr[k].substring(3,6)), Integer.parseInt(mbotstr[k].substring(6,9))));
                    canvas.drawCircle(100 + 250 * j, 150 + 310 * i, 70, pt);
                    pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
                    canvas.drawCircle(190 + 250 * j, 150 + 310 * i, 50, pt);
                }
                k++;
            }
        }

        for(int i=0;i<2;i++) {
            for(int j=0;j<4;j++){
                if(mexist[k]) {
                    Log.d("aaaaaaaaaaaaaaaaaaaaa", mbotstr[k].substring(0,3));
                    pt.setColor(Color.rgb( Integer.parseInt(mbotstr[k].substring(0,3)), Integer.parseInt(mbotstr[k].substring(3,6)), Integer.parseInt(mbotstr[k].substring(6,9))));
                    canvas.drawCircle(100 + 250 * j, 1400 + 310 * i, 70, pt);
                    pt.setColor(Color.rgb( Integer.parseInt(mtopstr[k].substring(0,3)), Integer.parseInt(mtopstr[k].substring(3,6)), Integer.parseInt(mtopstr[k].substring(6,9))));
                    canvas.drawCircle(190 + 250 * j, 1400 + 310 * i, 50, pt);
                }
                k++;
            }
        }


    }
}