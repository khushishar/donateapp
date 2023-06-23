package com.example.shareit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        UserDB = FirebaseDatabase.getInstance("https://share-it-6d179-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        user = mAuth.getCurrentUser();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_details);

        edt_Name = findViewById(R.id.register_name);
        edt_User_Class = findViewById(R.id.register_user_type);

        adapterClasses = new ArrayAdapter<String>(this, R.layout.userclass, UserClass);
        progressBar = findViewById(R.id.change_progressbar);
        btn_save = findViewById(R.id.userd_change_btn);
        edt_User_Class.setAdapter(adapterClasses);

        UserDB.child(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                edt_Name.setText(dataSnapshot.child("name").getValue().toString());
                if(dataSnapshot.child("usertype").getValue().toString() == "Donor") {
                    edt_User_Class.setListSelection(0);
                }else {
                    edt_User_Class.setListSelection(1);
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
}