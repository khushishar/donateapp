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

public class ViewShelters extends AppCompatActivity {

    RecyclerView recyclerView;
    private ShelterAdapter shelterAdapter;
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private CollectionReference ShelterDB = DB.collection("Shelters");
    ProgressDialog progressDialog;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shelters);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        Query query = ShelterDB.whereEqualTo("Status", true).orderBy("TimeStamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ShelterItem> shelterOptions = new FirestoreRecyclerOptions.Builder<ShelterItem>()
                .setQuery(query, ShelterItem.class)
                .build();

        shelterAdapter = new ShelterAdapter(shelterOptions);
        recyclerView = findViewById(R.id.shelterRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(progressDialog.isShowing())
            progressDialog.dismiss();

        recyclerView.setAdapter(shelterAdapter);

        shelterAdapter.setOnShelterItemClickListener(new ShelterAdapter.onShelterItemClickListener() {
            @Override
            public void onShelterItemClick(DocumentSnapshot documentSnapshot, int position) {
                ShelterItem shelterItem = documentSnapshot.toObject(ShelterItem.class);
                Toast.makeText(ViewShelters.this, "Donor Name : " + shelterItem.DonorName + "\nDonor Number: " + shelterItem.DonorNumber, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + shelterItem.Location.getLatitude() + "," + shelterItem.Location.getLongitude()));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        shelterAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shelterAdapter.stopListening();
    }

}