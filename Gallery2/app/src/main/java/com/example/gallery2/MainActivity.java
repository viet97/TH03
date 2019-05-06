package com.example.gallery2;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText search;
    Button searchBtn;
    Button addBtn;
    Button editBtn;
    Button removeBtn;
    DatabaseHelper db;
    RecyclerView rvListImage;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = findViewById(R.id.add);
        editBtn = findViewById(R.id.edit);
        search = findViewById(R.id.type);
        removeBtn = findViewById(R.id.delete);
        searchBtn = findViewById(R.id.search);
        rvListImage = findViewById(R.id.rv_listView);


        adapter = new ImageAdapter(this);
        rvListImage.setAdapter(adapter);
        rvListImage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        addBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        removeBtn.setOnClickListener(this);

        db = new DatabaseHelper(this);
        db.getAllImage();
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
