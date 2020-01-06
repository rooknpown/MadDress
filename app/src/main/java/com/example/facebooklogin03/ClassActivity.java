package com.example.facebooklogin03;

import android.app.Activity;
import android.os.Bundle;

public class ClassActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ClassView(this));
    }

}
