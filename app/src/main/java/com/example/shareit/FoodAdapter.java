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

public class FoodAdapter extends FirestoreRecyclerAdapter<FoodItem, FoodAdapter.FoodViewHolder> {

    private onFoodItemClickListener listener;
    Context context;

    public FoodAdapter(@NonNull FirestoreRecyclerOptions<FoodItem> options) {
        super(options);
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodcard_item, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if(getItemCount() == 0)
            Toast.makeText(context, "There are no Food items", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull FoodItem model) {

        holder.DonorName.setText(String.valueOf(model.DonorName));
        holder.DonorNumber.setText(String.valueOf(model.DonorNumber));
        holder.FoodName.setText(String.valueOf(model.FoodName));
        holder.FoodCount.setText(String.valueOf(model.FoodCount));

    }

     class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView DonorName, DonorNumber, FoodName, FoodCount;
        CardView cardView;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            DonorName = itemView.findViewById(R.id.foodDonorName);
            DonorNumber = itemView.findViewById(R.id.foodDonorNumber);
            FoodName = itemView.findViewById(R.id.foodItemName);
            FoodCount = itemView.findViewById(R.id.foodItemCount);
            cardView = itemView.findViewById(R.id.foodItemCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onFoodItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface onFoodItemClickListener{
        void onFoodItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnFoodItemClickListener(onFoodItemClickListener listener){
        this.listener = listener;
    }

}
