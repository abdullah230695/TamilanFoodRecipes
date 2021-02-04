package com.shalla.tamilanfoodrecipes.FoodLists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shalla.tamilanfoodrecipes.Adapters.DBAdapter_TO_RecylerView;
import com.shalla.tamilanfoodrecipes.MainActivity;
import com.shalla.tamilanfoodrecipes.Models.DB_TO_RECYCLERVIEW;
import com.shalla.tamilanfoodrecipes.ParticularFood.ParticularFoodItem;
import com.shalla.tamilanfoodrecipes.Profile.MyProfile;
import com.shalla.tamilanfoodrecipes.R;
import com.shalla.tamilanfoodrecipes.RecyclerItemClick.RecyclerItemClickListener;

public class AllFoodsList extends AppCompatActivity implements View.OnClickListener{
    RecyclerView RVbooking_history;
    DBAdapter_TO_RecylerView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_foods_list);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query cr=db.collection("Recipes");

        RVbooking_history = findViewById(R.id.rvAllFoodList);



        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);

        RVbooking_history.setLayoutManager(linearLayoutManager1);
        RVbooking_history.setHasFixedSize(true);


        FirestoreRecyclerOptions<DB_TO_RECYCLERVIEW> options = new FirestoreRecyclerOptions.Builder<DB_TO_RECYCLERVIEW>()
                .setQuery(cr, DB_TO_RECYCLERVIEW.class)
                .build();

        adapter=new DBAdapter_TO_RecylerView(options);
        RVbooking_history.setAdapter(adapter);

        RVbooking_history.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                RVbooking_history, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Intent intent = new Intent(getApplicationContext(), ParticularFoodItem.class);
                    intent.putExtra("category",adapter.getItem(position).getCategory());
                    intent.putExtra("title",adapter.getItem(position).getTitle());
                    intent.putExtra("description",adapter.getItem(position).getDescription());
                    intent.putExtra("foodID",adapter.getItem(position).getFoodID());
                    intent.putExtra("foodPicURL",adapter.getItem(position).getReciepe_image_url());
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.push_out_down);
                }catch (Exception e){ }
            }
            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imgHome :
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            case R.id.imgProfile :
                startActivity(new Intent(getApplicationContext(), MyProfile.class));
                finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}