package com.shalla.tamilanfoodrecipes.ParticularFood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shalla.tamilanfoodrecipes.R;

import java.util.ArrayList;
import java.util.List;

public class ParticularFoodItem extends AppCompatActivity {
    @NonNull
    TextView tvCategory,tvTitle,tvDescription;
    @NonNull
    ImageView imgFoodPic;
    @NonNull
    TextView tvIng,tvIng1,tvIng2,tvIng3,tvIng4,tvIng5,tvIng6,tvIng7,tvIng8,tvIng9,tvIng10,
            tvIng11,tvIng12,tvIng13,tvIng14,tvIng15,tvIng16,tvIng17,tvIng18,tvIng19;

    @NonNull
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @NonNull
    String title,description,foodPicURL,foodID,category;
    @NonNull
    String  Ing,Ing1,Ing2,Ing3,Ing4,Ing5,Ing6,Ing7,Ing8,Ing9,Ing10,
            Ing11,Ing12,Ing13,Ing14,Ing15,Ing16,Ing17,Ing18,Ing19;
    @NonNull
    List ingredientsList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_food_item);
        setupID();

        title=getIntent().getStringExtra("title");
        description=getIntent().getStringExtra("description");
        foodPicURL=getIntent().getStringExtra("foodPicURL");
        foodID=getIntent().getStringExtra("foodID");
        //category=getIntent().getStringExtra("category");
        if (title != null) {
            tvTitle.setText(title);
        }
        if (description != null) {
            tvDescription.setText(description);
        }
        if(foodPicURL!=null) {
            Glide.with(getApplicationContext()).load(foodPicURL).into(imgFoodPic);
        }
        getFoodInfo();
    }

    private void getFoodInfo() {
        db.collection("Recipes").document("sGUCCjImREZOIohkrxGc").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                toast(" food details are available");
                if(task.isSuccessful()){
                    try {
                        DocumentSnapshot data = task.getResult();
                        ingredientsList = (List) data.getData().get("ingredients");
                        Log.d("List", ingredientsList.toString());
                        Ing = ingredientsList.get(0).toString();
                        Ing1 = ingredientsList.get(1).toString();
                        Ing2 = ingredientsList.get(2).toString();
                        Ing3 = ingredientsList.get(3).toString();
                        Ing4 = ingredientsList.get(4).toString();
                        Ing5 = ingredientsList.get(5).toString();
                        Ing6 = ingredientsList.get(6).toString();
                        Ing7 = ingredientsList.get(7).toString();
                        Ing8 = ingredientsList.get(8).toString();
                        Ing9 = ingredientsList.get(9).toString();
                        Ing10 = ingredientsList.get(10).toString();
                        Ing11 = ingredientsList.get(11).toString();
                        Ing12 = ingredientsList.get(12).toString();
                        Ing13 = ingredientsList.get(13).toString();
                        Ing14 = ingredientsList.get(14).toString();
                        Ing15 = ingredientsList.get(15).toString();
                        Ing16 = ingredientsList.get(16).toString();
                        Ing17 = ingredientsList.get(17).toString();
                        Ing18 = ingredientsList.get(18).toString();
                        Ing19 = ingredientsList.get(19).toString();

                    }catch (Exception e){
                        setIngredients();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toast("Some food details are not available");
            }
        });
    }

    private void setIngredients() {
        if (Ing != null) {
            tvIng.setVisibility(View.VISIBLE);
            tvIng.setText(Ing);
        }
        if (Ing1 != null) {
            tvIng1.setVisibility(View.VISIBLE);
            tvIng1.setText(Ing1);
        }
        if (Ing2 != null) {
            tvIng2.setVisibility(View.VISIBLE);
            tvIng2.setText(Ing2);
        }
        if (Ing3 != null) {
            tvIng3.setVisibility(View.VISIBLE);
            tvIng3.setText(Ing3);
        }
        if (Ing4 != null) {
            tvIng4.setVisibility(View.VISIBLE);
            tvIng4.setText(Ing4);
        }
        if (Ing5 != null) {
            tvIng5.setVisibility(View.VISIBLE);
            tvIng5.setText(Ing5);
        }
        if (Ing6 != null) {
            tvIng6.setVisibility(View.VISIBLE);
            tvIng6.setText(Ing6);
        }
        if (Ing7 != null) {
            tvIng7.setVisibility(View.VISIBLE);
            tvIng7.setText(Ing7);
        }
        if (Ing8 != null) {
            tvIng8.setVisibility(View.VISIBLE);
            tvIng8.setText(Ing8);
        }
        if (Ing9 != null) {
            tvIng9.setVisibility(View.VISIBLE);
            tvIng9.setText(Ing9);
        }
        if (Ing10 != null) {
            tvIng10.setVisibility(View.VISIBLE);
            tvIng10.setText(Ing10);
        }
        if (Ing11 != null) {
            tvIng11.setVisibility(View.VISIBLE);
            tvIng11.setText(Ing11);
        }
        if (Ing12 != null) {
            tvIng12.setVisibility(View.VISIBLE);
            tvIng12.setText(Ing12);
        }
        if (Ing13 != null) {
            tvIng13.setVisibility(View.VISIBLE);
            tvIng13.setText(Ing13);
        }
        if (Ing14 != null) {
            tvIng14.setVisibility(View.VISIBLE);
            tvIng14.setText(Ing14);
        }
        if (Ing15 != null) {
            tvIng15.setVisibility(View.VISIBLE);
            tvIng15.setText(Ing15);
        }
        if (Ing16 != null) {
            tvIng16.setVisibility(View.VISIBLE);
            tvIng16.setText(Ing16);
        }
        if (Ing17 != null) {
            tvIng17.setVisibility(View.VISIBLE);
            tvIng17.setText(Ing17);
        }
        if (Ing18 != null) {
            tvIng18.setVisibility(View.VISIBLE);
            tvIng18.setText(Ing18);
        }
        if (Ing19 != null) {
            tvIng19.setVisibility(View.VISIBLE);
            tvIng19.setText(Ing19);
        }
    }

    private void toast(String msg){
        Toast.makeText(ParticularFoodItem.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setupID() {
        //tvCategory=findViewById(R.id.);
        tvTitle=findViewById(R.id.tvFoodTitlePartView);
        tvDescription=findViewById(R.id.tvDescriptionPart);
        tvIng=findViewById(R.id.tvIng);
        tvIng1=findViewById(R.id.tvIng1);
        tvIng2=findViewById(R.id.tvIng2);
        tvIng3=findViewById(R.id.tvIng3);
        tvIng4=findViewById(R.id.tvIng4);
        tvIng5=findViewById(R.id.tvIng5);
        tvIng6=findViewById(R.id.tvIng6);
        tvIng7=findViewById(R.id.tvIng7);
        tvIng8=findViewById(R.id.tvIng8);
        tvIng9=findViewById(R.id.tvIng9);
        tvIng10=findViewById(R.id.tvIng10);
        tvIng11=findViewById(R.id.tvIng11);
        tvIng12=findViewById(R.id.tvIng12);
        tvIng13=findViewById(R.id.tvIng13);
        tvIng14=findViewById(R.id.tvIng14);
        tvIng15=findViewById(R.id.tvIng15);
        tvIng16=findViewById(R.id.tvIng16);
        tvIng17=findViewById(R.id.tvIng17);
        tvIng18=findViewById(R.id.tvIng18);
        tvIng19=findViewById(R.id.tvIng19);
        imgFoodPic=findViewById(R.id.imgFoodPic);

    }

}