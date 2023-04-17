package com.example.shareit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewFood extends AppCompatActivity {

    RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private CollectionReference FoodDB = DB.collection("Foods");
    ProgressDialog progressDialog;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {

        Query query = FoodDB.whereEqualTo("Status", true).orderBy("TimeStamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FoodItem> foodOptions = new FirestoreRecyclerOptions.Builder<FoodItem>()
                .setQuery(query, FoodItem.class)
                .build();

        foodAdapter = new FoodAdapter(foodOptions);
        recyclerView = findViewById(R.id.foodRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(progressDialog.isShowing())
            progressDialog.dismiss();

        recyclerView.setAdapter(foodAdapter);

        foodAdapter.setOnFoodItemClickListener(new FoodAdapter.onFoodItemClickListener() {
            @Override
            public void onFoodItemClick(DocumentSnapshot documentSnapshot, int position) {
                FoodItem foodItem = documentSnapshot.toObject(FoodItem.class);
                Toast.makeText(ViewFood.this, "Donor Name : " + foodItem.DonorName + "\nDonor Number: " + foodItem.DonorNumber, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + foodItem.Latitude + "," + foodItem.Longitude));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        foodAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        foodAdapter.stopListening();
    }

}