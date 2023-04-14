package com.example.shareit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodItem> foodItemArrayList;
    SelectListener itemClickListener;

    public MyAdapter(Context context, ArrayList<FoodItem> foodItemArrayList, SelectListener itemClickListener) {
        this.context = context;
        this.foodItemArrayList = foodItemArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.foodcard_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        FoodItem FoodItem = foodItemArrayList.get(position);
        holder.DonorName.setText(String.valueOf(FoodItem.DonorName));
        holder.DonorNumber.setText(String.valueOf(FoodItem.DonorNumber));
        holder.FoodName.setText(String.valueOf(FoodItem.FoodName));
        holder.FoodCount.setText(String.valueOf(FoodItem.FoodCount));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(FoodItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodItemArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView DonorName, DonorNumber, FoodName, FoodCount;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DonorName = itemView.findViewById(R.id.foodDonorName);
            DonorNumber = itemView.findViewById(R.id.foodDonorNumber);
            FoodName = itemView.findViewById(R.id.foodItemName);
            FoodCount = itemView.findViewById(R.id.foodItemCount);
            cardView = itemView.findViewById(R.id.foodItemCard);
        }
    }
}
