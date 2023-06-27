package com.example.shareit;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
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

public class Receiver_Side extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

    CardView receiveFood, receiveClothes, receiveShelter, logout;
    TextView userDetail;
    FirebaseUser user;
    DatabaseReference UserDB;
    FirebaseAuth mAuth;
    Double LocLatitude, LocLongitude;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_side);

        mAuth = FirebaseAuth.getInstance();
        UserDB = FirebaseDatabase.getInstance("https://share-it-6d179-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        logout = findViewById(R.id.logout);
        receiveFood = findViewById(R.id.receiveFood);
        receiveClothes = findViewById(R.id.receiveClothes);
        receiveShelter = findViewById(R.id.receiveShelter);

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
                    userDetail.setText("Hello " + String.valueOf(dataSnapshot.child("name").getValue()) + ", what dou you need?");
                }
            });
        }

        //get Location

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest locationRequest1 = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 100).setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(1000).setMaxUpdateDelayMillis(100).build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest1, locationCallback, null);
            return;
        }


        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        LocLatitude = location.getLatitude();
                        LocLongitude = location.getLongitude();
                    }
                }
            }
        };
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);




        //Log out btn listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent_login = new Intent(getApplicationContext(), Login.class);
                startActivity(intent_login);
                Toast.makeText(Receiver_Side.this, "Logged Out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        receiveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Receiver_Side.this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                    askPermission();
                }
                LocationServices.getFusedLocationProviderClient(Receiver_Side.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(LocLongitude == null){
                            LocLongitude = location.getLongitude();
                        }
                        if (LocLatitude == null) {
                            LocLatitude = location.getLatitude();
                        }

                        Intent intent_getFood = new Intent(getApplicationContext(), ViewFood.class);
                        Bundle loc = new Bundle();
                        loc.putDouble("LocLongitude", LocLongitude);
                        loc.putDouble("LocLatitude", LocLatitude);
                        intent_getFood.putExtras(loc);
                        Log.d("Longitude", String.valueOf(LocLongitude));
                        Log.d("Latitude", String.valueOf(LocLatitude));
                        startActivity(intent_getFood);
//                        finish();
                    }
                });


            }
        });

        receiveClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Receiver_Side.this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                    askPermission();
                }
                LocationServices.getFusedLocationProviderClient(Receiver_Side.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(LocLongitude == null){
                            LocLongitude = location.getLongitude();
                        }
                        if (LocLatitude == null) {
                            LocLatitude = location.getLatitude();
                        }
                        Intent intent_getFood = new Intent(getApplicationContext(),ViewClothes.class);
                        intent_getFood.putExtra("LocLongitude", LocLongitude);
                        intent_getFood.putExtra("LocLatitude", LocLatitude);
                        startActivity(intent_getFood);
//                      finish();

                    }
                });

            }
        });
        receiveShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(Receiver_Side.this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                    askPermission();
                }
                LocationServices.getFusedLocationProviderClient(Receiver_Side.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(LocLongitude == null){
                            LocLongitude = location.getLongitude();
                        }
                        if (LocLatitude == null) {
                            LocLatitude = location.getLatitude();
                        }
                        Intent intent_getFood = new Intent(getApplicationContext(), ViewShelters.class);
                        intent_getFood.putExtra("LocLongitude", LocLongitude);
                        intent_getFood.putExtra("LocLatitude", LocLatitude);
                        startActivity(intent_getFood);
                    }
                });
//                finish();
            }
        });
    }



    private void askPermission() {
        ActivityCompat.requestPermissions(Receiver_Side.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LocLatitude = location.getLatitude();
                        LocLongitude = location.getLongitude();
                    }
                }
            });
        }
    }

}