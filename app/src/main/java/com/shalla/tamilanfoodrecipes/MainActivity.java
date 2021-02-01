package com.shalla.tamilanfoodrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shalla.tamilanfoodrecipes.FoodLists.AllFoodsList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
ImageView imgHome,imgAllFoodList,imgProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupID();

        imgAllFoodList.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
    }

    private void setupID() {
        imgHome=findViewById(R.id.imgHome);
        imgAllFoodList=findViewById(R.id.imgAllFoodList);
        imgProfile=findViewById(R.id.imgProfile);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imgAllFoodList :
                startActivity(new Intent(getApplicationContext(), AllFoodsList.class));
        }
    }
}