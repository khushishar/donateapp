package com.example.shareit;

import android.content.Context;
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

public class ContributionsClothAdapter extends FirestoreRecyclerAdapter<ClothItem, ContributionsClothAdapter.ClothViewHolder> {

    Context context;

    public ContributionsClothAdapter(@NonNull FirestoreRecyclerOptions<ClothItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public ContributionsClothAdapter.ClothViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributionscard_item, parent, false);
        return new ContributionsClothAdapter.ClothViewHolder(v);
    }

    @Override
    public void onDataChanged () {
        super.onDataChanged();
        if (getItemCount() == 0)
            Toast.makeText(context, "There are no Food items", Toast.LENGTH_SHORT).show();
    }

        @Override
        protected void onBindViewHolder (@NonNull ContributionsClothAdapter.ClothViewHolder holder,
        int position, @NonNull ClothItem model){

        holder.Date.setText(String.valueOf(model.Timestamp.toDate()));
        holder.ClothName.setText(String.valueOf(model.ClothName));
        holder.ClothCount.setText(String.valueOf(model.ClothCount));

    }


    class ClothViewHolder extends RecyclerView.ViewHolder {

            TextView Date, ClothName, ClothCount;
            CardView cardView;

            public ClothViewHolder(@NonNull View itemView) {
                super(itemView);
                ClothName = itemView.findViewById(R.id.contributionsItemName);
                ClothCount = itemView.findViewById(R.id.contributionsItemCount);
                Date = itemView.findViewById(R.id.contributionsItemDate);
                cardView = itemView.findViewById(R.id.contributionsItemCard);

            }
        }

}

