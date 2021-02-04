package com.shalla.tamilanfoodrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shalla.tamilanfoodrecipes.FoodLists.AllFoodsList;
import com.shalla.tamilanfoodrecipes.Profile.LoginActivity;
import com.shalla.tamilanfoodrecipes.Profile.MyProfile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgHome,imgAllFoodList,imgProfile;
    LoginActivity la=new LoginActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupID();

        //la.init();
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

            case R.id.imgProfile :
                Intent intent=new Intent(getApplication(),MyProfile.class);
                startActivity(intent);

        }
    }
    public void toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}