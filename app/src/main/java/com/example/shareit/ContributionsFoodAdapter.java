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

public class ContributionsFoodAdapter extends FirestoreRecyclerAdapter<FoodItem, ContributionsFoodAdapter.FoodViewHolder> {

    Context context;

    public ContributionsFoodAdapter(@NonNull FirestoreRecyclerOptions<FoodItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributionscard_item, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if(getItemCount() == 0)
            Toast.makeText(context, "There are no Food items", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onBindViewHolder(@NonNull ContributionsFoodAdapter.FoodViewHolder holder, int position, @NonNull FoodItem model) {

        holder.Date.setText(model.TimeStamp.toDate().toLocaleString());
        holder.FoodName.setText(String.valueOf(model.FoodName));
        holder.FoodCount.setText(String.valueOf(model.FoodCount));

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
