package com.example.rajulnahar.calllogger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUser extends AppCompatActivity {

    EditText name;
    EditText phone;
    Button save;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        name = (EditText) findViewById(R.id.etName);
        phone = (EditText) findViewById(R.id.etPhone);
        save = (Button) findViewById(R.id.btnSaveContact);
        database = Database.getInstance(AddUser.this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetails userDetails = new UserDetails();
                userDetails.name = name.getText().toString();
                userDetails.phone = phone.getText().toString();
                long x = database.addUserDetails(userDetails);
                Log.e("addUser",String.valueOf(x));
                Intent intent = new Intent(AddUser.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}
