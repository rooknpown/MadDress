package com.example.facebooklogin03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private TextView textView;
    private LoginButton loginButton;
    Button tel;
    Button imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        //이미지 버튼 클릭시
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                startActivity(intent);

            }
        });


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        textView.setText("Successfully logged in");
                        //연락처 버튼 클릭시
                        tel = (Button) findViewById(R.id.tel);
                        tel.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        textView.setText("Login cancled");
                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });

    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



}
