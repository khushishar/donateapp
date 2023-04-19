package com.example.shareit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class View_Contributions extends AppCompatActivity {

    FirebaseUser user;
    FirebaseAuth mAuth;

    RecyclerView foodContributions, clothContributions, shelterContributions;
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private CollectionReference FoodDB = DB.collection("Foods");
    private ContributionsFoodAdapter contributionsFoodAdapter;
    private ContributionsClothAdapter contributionsClothAdapter;
    private ContributionsShelterAdapter contributionsShelterAdapter;
    private CollectionReference ClothDB = DB.collection("Clothes");
    private CollectionReference ShelterDB = DB.collection("Shelters");

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contributions);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        setUpFoodRecyclerView();
        setUpClothRecyclerView();
        setUpShelterRecyclerView();

    }

    private void setUpShelterRecyclerView() {

        Query query = ShelterDB.whereEqualTo("DonorID", user.getUid()).orderBy("Status", Query.Direction.DESCENDING).orderBy("TimeStamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ShelterItem> shelterOptions = new FirestoreRecyclerOptions.Builder<ShelterItem>()
                .setQuery(query, ShelterItem.class)
                .build();

        contributionsShelterAdapter = new ContributionsShelterAdapter(shelterOptions);
        shelterContributions = findViewById(R.id.shelter_recycler);
        shelterContributions.setHasFixedSize(false);
        shelterContributions.setLayoutManager(new LinearLayoutManager(this));
        shelterContributions.setAdapter(contributionsShelterAdapter);

    }

    private void setUpClothRecyclerView() {

        Query query = ClothDB.whereEqualTo("DonorID", user.getUid()).orderBy("Status", Query.Direction.DESCENDING).orderBy("TimeStamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ClothItem> clothOptions = new FirestoreRecyclerOptions.Builder<ClothItem>()
                .setQuery(query, ClothItem.class)
                .build();

        contributionsClothAdapter = new ContributionsClothAdapter(clothOptions);
        clothContributions = findViewById(R.id.cloth_recycler);
        clothContributions.setHasFixedSize(false);
        clothContributions.setLayoutManager(new LinearLayoutManager(this));
        clothContributions.setAdapter(contributionsClothAdapter);

    }

    private void setUpFoodRecyclerView() {

        Query query = FoodDB.whereEqualTo("DonorID", user.getUid()).orderBy("Status", Query.Direction.DESCENDING).orderBy("TimeStamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FoodItem> foodOptions = new FirestoreRecyclerOptions.Builder<FoodItem>()
                .setQuery(query, FoodItem.class)
                .build();

        contributionsFoodAdapter = new ContributionsFoodAdapter(foodOptions);
        foodContributions = findViewById(R.id.food_recycler);
        foodContributions.setHasFixedSize(false);
        foodContributions.setLayoutManager(new LinearLayoutManager(this));
        foodContributions.setAdapter(contributionsFoodAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        contributionsFoodAdapter.startListening();
        contributionsClothAdapter.startListening();
        contributionsShelterAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        contributionsFoodAdapter.stopListening();
        contributionsClothAdapter.stopListening();
        contributionsShelterAdapter.stopListening();
    }

}