package com.dannydiao.androidsipdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    String accout;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //请求权限
        if((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(LoginActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO},1);
        }

        //绑定控件
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
                        String LoginResult = response.body().string();
                        Boolean LoginStatus = false;
                        String LoginDes;
                        JSONArray jsonArray = null;
                        String name = "";
                        String ID = "";
                        ArrayList<String> name_1 = new ArrayList<>();
                        ArrayList<String> ID_1 = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(LoginResult);
                            LoginStatus = jsonObject.getBoolean("pass");
                            jsonArray = jsonObject.getJSONArray("user_list");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (LoginStatus) {


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    //将name和ID数据通过Intent传入下一个Activity
                                    name = jsonArray.getJSONObject(i).getString("name");
                                    ID = jsonArray.getJSONObject(i).getString("userID");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                name_1.add(name);
                                ID_1.add(ID);


                            }

                            Intent intent = new Intent(getApplication(), CallActivity.class);
                            intent.putExtra("ID", accout);
                            startActivity(intent);


                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(LoginResult);
                                LoginDes = jsonObject.getString("description");
                                Looper.prepare();
                                Toast.makeText(getApplication(), LoginDes, Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

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
