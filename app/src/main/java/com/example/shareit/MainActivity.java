package com.example.shareit;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 100;
    Button logout,cloth,food,shelter;
    TextView userDetail;
    FirebaseUser user;
    DatabaseReference UserDB;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        UserDB = FirebaseDatabase.getInstance("https://share-it-6d179-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        logout = findViewById(R.id.logout);
        cloth= findViewById(R.id.donateclothes);
        food = findViewById(R.id.donateFood);
        shelter=findViewById(R.id.donateshelter);
        userDetail = findViewById(R.id.user_details);
        user = mAuth.getCurrentUser();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }

//        LocationRequest locationRequest1 = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 100).setWaitForAccurateLocation(false)
//                .setMinUpdateIntervalMillis(1000).setMaxUpdateDelayMillis(100).build();
//
//        LocationCallback locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                if( locationResult == null){
//                    return;
//                }
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest1, locationCallback, null);
//            return;
//        }
//
//        LocationRequest mLocationRequest = LocationRequest.create();
//        mLocationRequest.setInterval(60000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationCallback mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    if (location != null) {
//                        //TODO: UI updates.
//                    }
//                }
//            }
//        };
//        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);

        if(user == null){
            Intent intent_login = new Intent(getApplicationContext(), Login.class);
            startActivity(intent_login);
            finish();
        }else {
            String UserID = user.getUid();
            UserDB.child(UserID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    userDetail.setText("Hello " + String.valueOf(dataSnapshot.child("name").getValue()));

                }
            });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent_login = new Intent(getApplicationContext(), Login.class);
                startActivity(intent_login);
                finish();
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sendFood = new Intent(getApplicationContext(), sendFood.class);
                startActivity(intent_sendFood);
//                finish();
            }
        });

        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_getFood = new Intent(getApplicationContext(),donatecloth.class);
                startActivity(intent_getFood);
//                finish();


            }
        });
        shelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_getFood = new Intent(getApplicationContext(), ViewFood.class);
                startActivity(intent_getFood);
//                finish();
            }
        });
    }



    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
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