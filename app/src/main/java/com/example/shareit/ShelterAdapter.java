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
import com.google.firebase.firestore.DocumentSnapshot;

public class ShelterAdapter extends FirestoreRecyclerAdapter<ShelterItem, ShelterAdapter.ShelterViewHolder> {

    private ShelterAdapter.onShelterItemClickListener listener;
    Context context;

    public ShelterAdapter(@NonNull FirestoreRecyclerOptions<ShelterItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public ShelterAdapter.ShelterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sheltercard_item, parent, false);
        return new ShelterAdapter.ShelterViewHolder(v);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if(getItemCount() == 0)
            Toast.makeText(context, "There are no Food items", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onBindViewHolder(@NonNull ShelterAdapter.ShelterViewHolder holder, int position, @NonNull ShelterItem model) {

        holder.DonorName.setText(String.valueOf(model.DonorName));
        holder.DonorNumber.setText(String.valueOf(model.DonorNumber));
        holder.ShelterDesc.setText(String.valueOf(model.ShelterDescription));
        holder.ShelterAvailability.setText(String.valueOf(model.ShelterAvailability));

    }

    class ShelterViewHolder extends RecyclerView.ViewHolder {

        TextView DonorName, DonorNumber, ShelterDesc, ShelterAvailability;
        CardView cardView;
        public ShelterViewHolder(@NonNull View itemView) {
            super(itemView);
            DonorName = itemView.findViewById(R.id.shelterDonorName);
            DonorNumber = itemView.findViewById(R.id.shelterDonorNumber);
            ShelterDesc = itemView.findViewById(R.id.shelterItemDesc);
            ShelterAvailability = itemView.findViewById(R.id.shelterItemCount);
            cardView = itemView.findViewById(R.id.shelterItemCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onShelterItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface onShelterItemClickListener{
        //        void onFoodItemClick(DocumentSnapshot documentSnapshot, int position);
        void onShelterItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnShelterItemClickListener(ShelterAdapter.onShelterItemClickListener listener){
        this.listener = listener;
    }

}
