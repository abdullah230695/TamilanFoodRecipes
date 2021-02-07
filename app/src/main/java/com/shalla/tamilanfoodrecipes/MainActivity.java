package com.shalla.tamilanfoodrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shalla.tamilanfoodrecipes.FoodLists.AllFoodsList;
import com.shalla.tamilanfoodrecipes.Profile.myProfileView;

public class MainActivity extends AppCompatActivity {

    ImageView imgHome,imgAllFoodList,imgProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupID();

        imgAllFoodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(), AllFoodsList.class);
                startActivity(intent);
                finish();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(), myProfileView.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupID() {
        imgHome=findViewById(R.id.imgHome);
        imgAllFoodList=findViewById(R.id.imgAllFoodList);
        imgProfile=findViewById(R.id.imgProfile);

    }


    public void toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}