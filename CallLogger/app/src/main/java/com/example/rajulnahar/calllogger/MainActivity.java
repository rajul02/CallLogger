package com.example.rajulnahar.calllogger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    FloatingActionButton fabCall;
    FloatingActionButton fabLog;
    EditText number;
    ImageButton call;

    Database database;
    Time now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Database.getInstance(MainActivity.this);
        List<UserDetails> userDetailsList= new ArrayList<>();

        fabCall = (FloatingActionButton) findViewById(R.id.add);
        fabLog = (FloatingActionButton) findViewById(R.id.log);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:00"));
        final Date current = cal.getTime();
        call = (ImageButton) findViewById(R.id.call);
        number = (EditText) findViewById(R.id.inpNumber);
         now = new Time();
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,AddUser.class);
                startActivity(intent);
                finish();
            }
        });
        fabLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,LogActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInfo logInfo= new LogInfo();
                logInfo.name="unknown";
                logInfo.msg="dialed";
                logInfo.time=current.toString();
                long x= database.addLogInfo(logInfo);
                Log.e("nupur",String.valueOf(x));
                String np = number.getText().toString();
                if(np.length() == 10){
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+np));
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Incorrect Number Length", Toast.LENGTH_SHORT).show();
                }

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setAdapter(recyclerViewAdapter);
        userDetailsList= database.getUserDetails();
        recyclerViewAdapter.setUserDetailses(userDetailsList);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);



    }



}
