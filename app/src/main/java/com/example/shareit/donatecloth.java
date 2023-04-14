package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donatecloth extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    Button getcloth,givecloth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donatecloth);
        getcloth = findViewById(R.id.provideclothes);
        givecloth= findViewById(R.id.receiveclothes);

        getcloth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_sendFood = new Intent(getApplicationContext(), sendFood.class);
                startActivity(intent_sendFood);
//                finish();
            }
        });

        givecloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_getFood = new Intent(getApplicationContext(), ViewFood.class);
                startActivity(intent_getFood);
//                finish();


            }
        });

    }}



