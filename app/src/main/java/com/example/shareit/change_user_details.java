package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class change_user_details extends AppCompatActivity {

    TextInputEditText edt_Name;
    AutoCompleteTextView edt_User_Class;
    String name, usertype;
    Button btn_save;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference UserDB;
    String[] UserClass = {"Donor", "Receiver"};
    ArrayAdapter<String> adapterClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_details);

        mAuth = FirebaseAuth.getInstance();
        UserDB = FirebaseDatabase.getInstance("https://share-it-6d179-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        user = mAuth.getCurrentUser();

        name = getIntent().getStringExtra("userName");
        usertype = getIntent().getStringExtra("userType");
        Log.d(name, usertype);

        edt_Name = findViewById(R.id.register_name);
        edt_User_Class = findViewById(R.id.register_user_type);

        adapterClasses = new ArrayAdapter<String>(this, R.layout.userclass, UserClass);
        progressBar = findViewById(R.id.change_progressbar);
        btn_save = findViewById(R.id.userd_change_btn);
        edt_User_Class.setAdapter(adapterClasses);

        String UserID = user.getUid();

        UserDB.child(UserID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                Log.d("UserID", UserID );
                Log.d("Username", String.valueOf(dataSnapshot.child("name").getValue()) );
                edt_Name.setText(name);
                if(usertype == "Donor") {
                    edt_User_Class.setListSelection(1);
                }else {
                    edt_User_Class.setListSelection(2);
                }

            }
        });


        edt_User_Class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usertype = String.valueOf(parent.getItemAtPosition(position));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = String.valueOf(edt_Name.getText());
                UserDB.child(mAuth.getCurrentUser().getUid()).child("name").setValue(name);
                UserDB.child(mAuth.getCurrentUser().getUid()).child("usertype").setValue(usertype);
                Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_main);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}