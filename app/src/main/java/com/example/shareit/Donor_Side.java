package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class Donor_Side extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
//    Button logout;
    CardView donateFood, donateClothes, donateShelter, viewContributions;
    TextView userDetail;
    FirebaseUser user;
    DatabaseReference UserDB;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_side);

        mAuth = FirebaseAuth.getInstance();
        UserDB = FirebaseDatabase.getInstance("https://share-it-6d179-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");

//        logout = findViewById(R.id.logout);

        donateFood = findViewById(R.id.donateFood);
        donateClothes = findViewById(R.id.donateClothes);
        donateShelter = findViewById(R.id.donateShelter);
        viewContributions = findViewById(R.id.viewContributions);

        userDetail = findViewById(R.id.user_details);
        user = mAuth.getCurrentUser();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }

        if(user == null){
            Intent intent_login = new Intent(getApplicationContext(), Login.class);
            startActivity(intent_login);
            finish();
        }else {
            String UserID = user.getUid();
            UserDB.child(UserID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {

                    userDetail.setText("Hello " + String.valueOf(dataSnapshot.child("name").getValue()) + ", what are you donating?" );

                }
            });
        }

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                Intent intent_login = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent_login);
//                finish();
//                Toast.makeText(Donor_Side.this, "Logged Out", Toast.LENGTH_SHORT).show();
//            }
//        });

//        donate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(), Spinner.class);
//                startActivity(intent);
//            }
//        });

        donateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent food_donate = new Intent(getApplicationContext(), sendFood.class);
                startActivity(food_donate);
                Toast.makeText(Donor_Side.this, "Donate Food", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        donateClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cloth_donate = new Intent(getApplicationContext(), donation_cloth.class);
                startActivity(cloth_donate);
                Toast.makeText(Donor_Side.this, "Donate Clothes", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        donateShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shelter_donate = new Intent(getApplicationContext(), donation_shelter.class);
                startActivity(shelter_donate);
                Toast.makeText(Donor_Side.this, "Donate Shelter", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewContributions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_contributions = new Intent(getApplicationContext(), View_Contributions.class);
                startActivity(intent_contributions);
            }
        });

    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Donor_Side.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();;
            }else{
                mAuth.signOut();
                Intent intent_login = new Intent(getApplicationContext(), Login.class);
                startActivity(intent_login);
                finish();
                Toast.makeText(this, "Permission is compulsory", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}