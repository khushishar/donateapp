package com.example.shareit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ViewClothes extends AppCompatActivity {

    RecyclerView recyclerView;
    private ClothAdapter clothAdapter;
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private CollectionReference ClothDB = DB.collection("Clothes");
    ProgressDialog progressDialog;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clothes);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {

        Query query = ClothDB.whereEqualTo("Status", true).orderBy("TimeStamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ClothItem> clothOptions = new FirestoreRecyclerOptions.Builder<ClothItem>()
                .setQuery(query, ClothItem.class)
                .build();

        clothAdapter = new ClothAdapter(clothOptions);
        recyclerView = findViewById(R.id.clothRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(progressDialog.isShowing())
            progressDialog.dismiss();

        recyclerView.setAdapter(clothAdapter);

        clothAdapter.setOnClothItemClickListener(new ClothAdapter.onClothItemClickListener() {
            @Override
            public void onClothItemClick(DocumentSnapshot documentSnapshot, int position) {
                ClothItem clothItem = documentSnapshot.toObject(ClothItem.class);
                Toast.makeText(ViewClothes.this, "Donor Name : " + clothItem.DonorName + "\nDonor Number: " + clothItem.DonorNumber, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + clothItem.Latitude + "," + clothItem.Longitude));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        clothAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        clothAdapter.stopListening();
    }

}