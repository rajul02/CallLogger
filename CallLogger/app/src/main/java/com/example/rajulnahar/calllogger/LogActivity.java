package com.example.rajulnahar.calllogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewLogAdapter recyclerViewLogAdapter;
    LinearLayoutManager linearLayoutManager;
    Database database=Database.getInstance(LogActivity.this);
    List<LogInfo> logs= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerviewlog);
        recyclerViewLogAdapter= new RecyclerViewLogAdapter(LogActivity.this);
        linearLayoutManager= new LinearLayoutManager(LogActivity.this);
        recyclerView.setAdapter(recyclerViewLogAdapter);
        logs=database.getLogInfo();
        recyclerViewLogAdapter.setLogs(logs);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}
