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

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewFood extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    ArrayList<FoodItem> FoodItemArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore FoodDB;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.foodRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FoodDB = FirebaseFirestore.getInstance();

        FoodItemArrayList = new ArrayList<FoodItem>();
        myAdapter = new MyAdapter(ViewFood.this, FoodItemArrayList, this);

        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

    }

    private void EventChangeListener() {
        FoodDB.collection("Foods").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value.isEmpty()){
                    Toast.makeText(ViewFood.this, "There are no items ", Toast.LENGTH_SHORT).show();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
                else if (error != null) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("FireStore Error", error.getMessage());
                    return;
                }else{
                    for (DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED ){
                            FoodItemArrayList.add(dc.getDocument().toObject(FoodItem.class));
                        }
                        if(dc.getType() == DocumentChange.Type.REMOVED){
                            FoodItemArrayList.remove(dc.getDocument().toObject(FoodItem.class));
                        }
                        myAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }

            }
        });
    }

    @Override
    public void onItemClick(FoodItem foodItem) {
        Toast.makeText(this, "Donor Name : " + foodItem.DonorName + "\nDonor Number: " + foodItem.DonorNumber, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + foodItem.Latitude + "," + foodItem.Longitude));
        startActivity(intent);
    }
}