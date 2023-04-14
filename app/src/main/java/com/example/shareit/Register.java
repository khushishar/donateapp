package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputEditText edt_Mail, edt_Password, edt_Name, edt_Phone;
    Button btn_register;
    ProgressBar progressBar;
    TextView to_login;
    FirebaseAuth mAuth;
    DatabaseReference UserDB;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent_main);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        UserDB = FirebaseDatabase.getInstance("https://share-it-6d179-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        edt_Mail = findViewById(R.id.register_email);
        edt_Password = findViewById(R.id.register_password);
        edt_Name = findViewById(R.id.register_name);
        edt_Phone = findViewById(R.id.register_phone);
        btn_register = findViewById(R.id.register_btn);
        to_login = findViewById(R.id.to_login);
        progressBar = findViewById(R.id.register_progressbar);

        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent(getApplicationContext(), Login.class);
                startActivity(intent_login);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,name, phone,password;

                email = String.valueOf(edt_Mail.getText());
                password = String.valueOf(edt_Password.getText());
                name = String.valueOf(edt_Name.getText());
                phone = String.valueOf(edt_Phone.getText());



                if(TextUtils.isEmpty(email) ||!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                    Toast.makeText(Register.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(name)){
                    Toast.makeText(Register.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(phone)||!phone.matches("^[+]?[0-9]{10,13}$")){
                    Toast.makeText(Register.this, "Please Enter correct phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String UserID = user.getUid();
                                    insertUserData(UserID);
                                    Toast.makeText(Register.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                                    Intent intent_login = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent_login);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });

    }

    public void insertUserData (String UserID) {
        String name = String.valueOf(edt_Name.getText());
        String phone = String.valueOf(edt_Phone.getText());
        String email = String.valueOf(edt_Mail.getText());
        String userId = UserID;
        User user = new User(name, phone, email, userId);

        UserDB.child("Users").child(user.getUserId()).setValue(user);
    }
}