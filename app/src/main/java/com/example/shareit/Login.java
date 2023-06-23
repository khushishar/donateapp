package com.example.shareit;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    TextInputEditText edt_Mail, edt_Password;
    Button btn_login;
    ProgressBar progressBar;
    TextView to_register;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String name;
    DatabaseReference UserDB;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        name = null;
        UserDB = FirebaseDatabase.getInstance("https://share-it-6d179-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");

        if(currentUser != null){
            if(currentUser.isEmailVerified()){
                String UserID = currentUser.getUid();

                UserDB.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(UserID).exists()){
                            Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent_main);
                            finish();
                        }else{
                            Intent intent_user_details = new Intent(getApplicationContext(), user_details_register.class);
                            intent_user_details.putExtra("email", currentUser.getEmail());
                            startActivity(intent_user_details);
                            finish();
                        }

                    }
                });

            }else{
                Toast.makeText(this, "Please verify email before proceeding", Toast.LENGTH_SHORT).show();
                currentUser.sendEmailVerification();
                mAuth.signOut();
                Intent intent_login = new Intent(getApplicationContext(), Login.class);
                startActivity(intent_login);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        edt_Mail = findViewById(R.id.login_email);
        edt_Password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.login_btn);
        to_register = findViewById(R.id.to_register);
        progressBar = findViewById(R.id.login_progressbar);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }

        LocationRequest locationRequest1 = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 100).setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(1000).setMaxUpdateDelayMillis(100).build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if( locationResult == null){
                    return;
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest1, locationCallback, null);
            return;
        }

        to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getApplicationContext(), Register.class);
                startActivity(intent_register);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(edt_Mail.getText());
                password = String.valueOf(edt_Password.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    if(mAuth.getCurrentUser().isEmailVerified()){

                                        String UserID = mAuth.getCurrentUser().getUid();
                                        UserDB.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                            @Override
                                            public void onSuccess(DataSnapshot dataSnapshot) {

                                                if(dataSnapshot.child(UserID).exists()){
                                                    Toast.makeText(Login.this, "LoggedIn Successfully.",Toast.LENGTH_SHORT).show();
                                                    Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent_main);
                                                    finish();
                                                }else{
                                                    Intent intent_user_details = new Intent(getApplicationContext(), user_details_register.class);
                                                    intent_user_details.putExtra("email", currentUser.getEmail());
                                                    startActivity(intent_user_details);
                                                    finish();
                                                }

                                            }
                                        });

                                    }else{

                                        Toast.makeText(Login.this, "Please verify email before proceeding", Toast.LENGTH_SHORT).show();
                                        currentUser.sendEmailVerification();
                                        mAuth.signOut();
                                        Intent intent_login = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent_login);
                                        finish();
                                    }

                                } else {

                                    Toast.makeText(Login.this, "LogIn failed.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Login.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();;
            }else{
                Toast.makeText(this, "Require Permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}