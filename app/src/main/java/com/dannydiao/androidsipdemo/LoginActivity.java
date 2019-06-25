package com.dannydiao.androidsipdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();


    Button login = findViewById(R.id.login_button);
    Button register = findViewById(R.id.register_button);
    TextView accout_textview = findViewById(R.id.account_input);
    TextView password_textview = findViewById(R.id.key_input);

    String accout;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accout = accout_textview.toString();
                password = password_textview.toString();


                RequestBody formBody = new FormBody.Builder()
                        .add("name",)
                        .build();
                Request request = new Request.Builder()
                        .url("https://diaosudev.cn:6666/login")
                        .post(formBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
        });
    }
}
