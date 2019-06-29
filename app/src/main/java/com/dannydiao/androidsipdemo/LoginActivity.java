package com.dannydiao.androidsipdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    String accout;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.login_button);
        Button register = findViewById(R.id.register_button);
        TextView accout_textview = findViewById(R.id.account_input);
        TextView password_textview = findViewById(R.id.key_input);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accout = accout_textview.getText().toString();
                password = password_textview.getText().toString();
                RequestBody formBody = new FormBody.Builder()
                        .add("userID", accout)
                        .add("password", password)
                        .build();
                Request request = new Request.Builder()
                        .url("https://diaosudev.cn:6666/login?userID=" + accout + "&password=" + password)
                        .get()
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("响应", response.body().string());
                    }
                });




            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}
