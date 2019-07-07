package com.dannydiao.androidsipdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HallActivity extends AppCompatActivity {

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> ID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);
        Intent intent = getIntent();

        name = intent.getStringArrayListExtra("name");
        ID = intent.getStringArrayListExtra("ID");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FriendsAdapter friendsAdapter = new FriendsAdapter(name, ID);
        recyclerView.setAdapter(friendsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()
                , DividerItemDecoration.VERTICAL));

        Intent intent1 = new Intent(getApplication(), CallActivity.class);
        startActivity(intent1);

    }
}
