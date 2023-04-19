package com.example.shareit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class ContributionsFoodAdapter extends FirestoreRecyclerAdapter<FoodItem, ContributionsFoodAdapter.FoodViewHolder> {

    Context context;
//    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
//    private CollectionReference FoodDB = DB.collection("Foods");

    public ContributionsFoodAdapter(@NonNull FirestoreRecyclerOptions<FoodItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributionscard_item, parent, false);
        return new FoodViewHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public void changeStatus(int position){
        getSnapshots().getSnapshot(position).getReference().update("Status", false);
    }

//    public boolean getStatus(int position){
//        try{
//            return getSnapshots().getSnapshot(position).getBoolean("Status");
//        } catch (ArrayIndexOutOfBoundsException e) {
//            return false;
//        }
//    }
//
//    public void renew(int position){
//        DocumentSnapshot snap = getSnapshots().getSnapshot(position);
//
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        Map<String, Object> FoodItem = new HashMap<>();
//        FoodItem.put("DonorID", snap.getString("DonorID"));
//        FoodItem.put("DonorName", snap.getString("DonorName"));
//        FoodItem.put("DonorNumber", snap.getString("DonorNumber"));
//        FoodItem.put("FoodName", snap.getString("FoodName"));
//        FoodItem.put("FoodCount", snap.getString("DonorFoodCount"));
//        FoodItem.put("Location", snap.getGeoPoint("Location"));
//        FoodItem.put("TimeStamp", com.google.firebase.Timestamp.now().toDate());
//        FoodItem.put("MilliSec", timestamp.getTime());
//        FoodItem.put("Status", true);
//
//        FoodDB.add(FoodItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(context, "Food Order: " + documentReference.getId() + "set successfully", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(context, "Food Order unsuccessful \nError Code:  " + e , Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

//    @Override
//    public void onDataChanged() {
//        super.onDataChanged();
//        if(getItemCount() == 0)
//            Toast.makeText(context, "There are no Food items", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onBindViewHolder(@NonNull ContributionsFoodAdapter.FoodViewHolder holder, int position, @NonNull FoodItem model) {

        holder.Date.setText(model.TimeStamp.toDate().toLocaleString());
        holder.FoodName.setText(String.valueOf(model.FoodName));
        holder.FoodCount.setText(String.valueOf(model.FoodCount));

        if(!model.Status)
            holder.cardView.setCardBackgroundColor(Color.parseColor("#EEFC5E"));

    }

    class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView Date, FoodName, FoodCount;
        CardView cardView;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.contributionsItemName);
            FoodCount = itemView.findViewById(R.id.contributionsItemCount);
            Date = itemView.findViewById(R.id.contributionsItemDate);
            cardView = itemView.findViewById(R.id.contributionsItemCard);

        }
    }

}
