package com.dannydiao.androidsipdemo;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView name_textview = findViewById(R.id.name_input);
        TextView password_textview = findViewById(R.id.key_input);
        TextView ID_textview = findViewById(R.id.ID_input);
        Button register = findViewById(R.id.register_button);
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_textview.getText().toString();
                String password = password_textview.getText().toString();
                String ID = ID_textview.getText().toString();

                RequestBody formBody = new FormBody.Builder()
                        .add("name", name)
                        .add("userID", ID)
                        .add("password", password)
                        .build();

                final Request request = new Request.Builder()
                        .url("https://diaosudev.cn:6666/register")
                        .post(formBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Looper.prepare();
                        if (response.body().string().equals("success!")) {
                            Toast.makeText(getApplication(), "注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplication(), "网络出问题啦233请检查网络", Toast.LENGTH_SHORT).show();
                        }


                        Looper.loop();

                    }
                });






            }
        });

    }
}
