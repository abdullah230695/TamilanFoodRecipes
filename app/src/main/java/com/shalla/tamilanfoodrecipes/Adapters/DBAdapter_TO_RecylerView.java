package com.shalla.tamilanfoodrecipes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.shalla.tamilanfoodrecipes.Models.DB_TO_RECYCLERVIEW;
import com.shalla.tamilanfoodrecipes.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class DBAdapter_TO_RecylerView extends FirestoreRecyclerAdapter<DB_TO_RECYCLERVIEW, DBAdapter_TO_RecylerView.viewHolder> {


    public DBAdapter_TO_RecylerView(@NonNull FirestoreRecyclerOptions<DB_TO_RECYCLERVIEW> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, final int position, @NonNull final DB_TO_RECYCLERVIEW model) {
        holder.Title.setText(model.getTitle());
        holder.Category.setText("Category : "+model.getCategory());
        String imageURL=model.getReciepe_image_url();
        if (imageURL==null) {
            Glide.with(holder.cImg.getContext()).load(R.drawable.ic_baseline_person_pin_24).into(holder.cImg);
        } else {
            Glide.with(holder.cImg.getContext()).load(imageURL).into(holder.cImg);
        }
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_food_list_inflater, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView Category,Title;
        CircleImageView cImg;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.tvFoodTitle);
            Category=itemView.findViewById(R.id.tvCategory);
            cImg=itemView.findViewById(R.id.cImgFoodPic);
        }

    }
}
