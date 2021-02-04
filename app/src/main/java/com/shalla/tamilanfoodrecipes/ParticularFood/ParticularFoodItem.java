package com.shalla.tamilanfoodrecipes.ParticularFood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import static com.shalla.tamilanfoodrecipes.Profile.MyProfile.*;
import static com.shalla.tamilanfoodrecipes.Profile.LoginActivity.*;

import com.google.firebase.firestore.Query;
import com.shalla.tamilanfoodrecipes.FoodLists.AllFoodsList;
import com.shalla.tamilanfoodrecipes.MainActivity;
import com.shalla.tamilanfoodrecipes.Profile.MyProfile;
import com.shalla.tamilanfoodrecipes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticularFoodItem extends AppCompatActivity implements  View.OnClickListener {
    @NonNull
    TextView tvCategory,tvTitle,tvDescription,tvDislikesCount,tvLikesCount;
    @NonNull
    ImageView imgFoodPic,imgLike,imgDisLike,imgFav,imgShare,imgHome,imgAllFoodList,imgProfile;
    @NonNull
    TextView tvIng,tvIng1,tvIng2,tvIng3,tvIng4,tvIng5,tvIng6,tvIng7,tvIng8,tvIng9,tvIng10,
            tvIng11,tvIng12,tvIng13,tvIng14,tvIng15,tvIng16,tvIng17,tvIng18,tvIng19;
    ConstraintLayout particularParent;

    @NonNull
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @NonNull
    String title,description,foodPicURL,foodID,UID,category;
    @NonNull
    String  Ing,Ing1,Ing2,Ing3,Ing4,Ing5,Ing6,Ing7,Ing8,Ing9,Ing10,
            Ing11,Ing12,Ing13,Ing14,Ing15,Ing16,Ing17,Ing18,Ing19;
    @NonNull
    int OldLikesCount,OldDisLikesCount;
    boolean templike,tempdislike,isFavoured,isLiked,isDisLiked;
    @NonNull
    List ingredientsList=new ArrayList();
    @NonNull
    Map<String,Object> updateLikes=new HashMap<>();
    Map<String,Object> favourite=new HashMap<>();
    List <String>isLikedList=new ArrayList();
    List <String>isDisLikedList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_food_item);
        setupID();
        imgAllFoodList.setOnClickListener(this);
        imgProfile.setOnClickListener(this);


        try {
            title=getIntent().getStringExtra("title");
            description=getIntent().getStringExtra("description");
            foodPicURL=getIntent().getStringExtra("foodPicURL");
            foodID=getIntent().getStringExtra("foodID");
            category=getIntent().getStringExtra("category");
            isGuest=prefs.getString("isGuest","");

            if(!isGuest.equals("yes")) {
                if (mAuth.getCurrentUser().getUid() != null) {
                    UID = mAuth.getCurrentUser().getUid();
                }
            }


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
            if(!isGuest.equals("yes")) {
                getIsFavoured();
                getisLiked();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuest.equals("yes")){
                    snackbar("இந்த அம்சத்தைப் பயன்படுத்த உங்கள் மொபைல் எண்ணுடன் ஒரு கணக்கை உருவாக்கவும்");
                    return;
                }
                templike=true;
                OldLikesCount=OldLikesCount+1;
                imgLike.setImageResource(R.drawable.ic_baseline_thumb_up_done);
                tvLikesCount.setText(String.valueOf(OldLikesCount));
                imgLike.setEnabled(false);
                imgDisLike.setEnabled(true);
                if(templike){
                    OldDisLikesCount=OldDisLikesCount-1;
                    tvDislikesCount.setText(String.valueOf(OldDisLikesCount));
                    imgDisLike.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24);
                }
               updateLikesCount( "இது உங்களுக்கு பிடித்திருக்கிறது");
            }
        });

        imgDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuest.equals("yes")){
                    snackbar("இந்த அம்சத்தைப் பயன்படுத்த உங்கள் மொபைல் எண்ணுடன் ஒரு கணக்கை உருவாக்கவும்");
                    return;
                }
                tempdislike=true;
                OldDisLikesCount=OldDisLikesCount+1;
                imgDisLike.setImageResource(R.drawable.ic_baseline_thumb_down);
                tvDislikesCount.setText(String.valueOf(OldDisLikesCount));
                imgDisLike.setEnabled(false);
                imgLike.setEnabled(true);
                if(tempdislike){
                    OldLikesCount=OldLikesCount-1;
                    tvLikesCount.setText(String.valueOf(OldLikesCount));
                    imgLike.setImageResource(R.drawable.ic_baseline_thumb_up_alt_24);
                }
                updateLikesCount("இதை நீங்கள் விரும்பவில்லை");
            }

        });

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuest.equals("yes")){
                    snackbar("இந்த அம்சத்தைப் பயன்படுத்த உங்கள் மொபைல் எண்ணுடன் ஒரு கணக்கை உருவாக்கவும்");
                    return;
                }
                if(isFavoured){
                    Task<Void> dr= db.collection("Users").document(UID)
                            .collection("MyFavourites").document(foodID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    snackbar("இந்த உணவு உங்களுக்கு பிடித்த பட்டியலிலிருந்து அகற்றப்பட்டது");
                                    imgFav.setImageResource(R.drawable.imgfav);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                    return;
                }
                imgFav.setImageResource(R.drawable.imgfavred);
                favourite.put("title",title);
                favourite.put("category",category);
                favourite.put("reciepe_image_url",foodPicURL);
                favourite.put("foodID",foodID);
                if (UID != null&foodID!=null) {
                    db.collection("Users").document(UID).collection("MyFavourites").document(foodID)
                            .set(favourite).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //snackbar("இந்த உணவு உங்களுக்கு பிடித்த பட்டியலில் சேர்க்கப்பட்டுள்ளது");
                            isFavoured = true;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            toast("ஓ! பின்னர் முயற்சிக்கவும்");
                        }
                    });
                }
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuest=="yes"){
                    toast("Not Authorized");
                    return;
                }
            }
        });
    }

    private void getisLiked() {
        db.collection("Recipes").document(foodID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                if(value.exists()){
                    isLikedList = (List) value.getData().get("likedBy");
                    Log.d("likeByList", isLikedList.toString());
                    isDisLikedList= (List) value.getData().get("dislikedBy");
                    Log.d("dislikeByList", isDisLikedList.toString());
                    if(isLikedList.contains(UID)){
                        isLiked=true;
                        imgLike.setEnabled(false);
                        imgLike.setImageResource(R.drawable.ic_baseline_thumb_up_done);
                    }else if(isDisLikedList.contains(UID)){
                        imgDisLike.setEnabled(false);
                        imgDisLike.setImageResource(R.drawable.ic_baseline_thumb_down);
                    }else{
                        imgLike.setImageResource(R.drawable.ic_baseline_thumb_up_alt_24);
                        imgDisLike.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24);
                    }
                }
            }
        });
    }

    private void getIsFavoured() {
       DocumentReference dr=db.collection("Users").document(UID).collection("MyFavourites").document(foodID);

       dr.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                if(value.exists()&&value!=null){
                    snackbar("உங்களுக்கு பிடித்த பட்டியலில் இந்த உணவைச் சேர்த்துள்ளீர்கள்");
                    imgFav.setImageResource(R.drawable.imgfavred);
                    isFavoured=true;
                }else{
                    imgFav.setImageResource(R.drawable.imgfav);
                    isFavoured=false;
                }
            }
        });
    }

    private void snackbar(String msg) {
        Snackbar snackbar = Snackbar.make(particularParent, msg, Snackbar.LENGTH_LONG).setTextColor(Color.YELLOW).setDuration(5000);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(3);  // show multiple line
        snackbar.show();
    }

    private void updateLikesCount(String toastMsg) {
        updateLikes.put("likes",OldLikesCount);
        updateLikes.put("dislikes",OldDisLikesCount);
        if(toastMsg.equals("Liked")) {
            updateLikes.put("likedBy", FieldValue.arrayUnion(UID));
            updateLikes.put("dislikedBy", FieldValue.arrayRemove(UID));
        }else{
            updateLikes.put("likedBy", FieldValue.arrayRemove(UID));
            updateLikes.put("dislikedBy", FieldValue.arrayUnion(UID));
        }
        db.collection("Recipes").document(foodID).update(updateLikes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                toast(toastMsg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toast("Check yoy internet connection");
            }
        });
    }

    private void getFoodInfo() {
        try {
            db.collection("Recipes").document(foodID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null){
                        return;
                    }
                    if(value.exists()){
                        ingredientsList = (List) value.getData().get("ingredients");
                        Log.d("List", ingredientsList.toString());
                        setIngredients();
                        OldLikesCount= ((Long) value.get("likes")).intValue();
                        OldDisLikesCount=  ((Long) value.get("dislikes")).intValue();
                            tvLikesCount.setText(String.valueOf(OldLikesCount));
                            tvDislikesCount.setText(String.valueOf(OldDisLikesCount));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setIngredients() {
      for(int i=0;i<ingredientsList.size();i++){
            switch (i) {
                case 0:
                    Ing = ingredientsList.get(0).toString();
                    tvIng.setVisibility(View.VISIBLE);
                    tvIng.setText(Ing);
                    break;
                case 1:
                    Ing1 = ingredientsList.get(1).toString();
                    tvIng1.setVisibility(View.VISIBLE);
                    tvIng1.setText(Ing1);
                    break;
                case 2:
                    Ing2 = ingredientsList.get(2).toString();
                    tvIng2.setVisibility(View.VISIBLE);
                    tvIng2.setText(Ing2);
                    break;
                case 3:
                    Ing3 = ingredientsList.get(3).toString();
                    tvIng3.setVisibility(View.VISIBLE);
                    tvIng3.setText(Ing3);
                    break;
                case 4:
                    Ing4 = ingredientsList.get(4).toString();
                    tvIng4.setVisibility(View.VISIBLE);
                    tvIng4.setText(Ing4);
                    break;
                case 5:
                    Ing5 = ingredientsList.get(5).toString();
                    tvIng5.setVisibility(View.VISIBLE);
                    tvIng5.setText(Ing5);
                    break;
                case 6:
                    Ing6 = ingredientsList.get(6).toString();
                    tvIng6.setVisibility(View.VISIBLE);
                    tvIng6.setText(Ing6);
                    break;
                case 7:
                    Ing7 = ingredientsList.get(7).toString();
                    tvIng7.setVisibility(View.VISIBLE);
                    tvIng7.setText(Ing7);
                    break;
                case 8:
                    Ing8 = ingredientsList.get(8).toString();
                    tvIng8.setVisibility(View.VISIBLE);
                    tvIng8.setText(Ing8);
                    break;
                case 9:
                    Ing9 = ingredientsList.get(9).toString();
                    tvIng9.setVisibility(View.VISIBLE);
                    tvIng9.setText(Ing9);
                    break;
                case 10:
                    Ing10 = ingredientsList.get(10).toString();
                    tvIng10.setVisibility(View.VISIBLE);
                    tvIng10.setText(Ing10);
                    break;
                case 11:
                    Ing11 = ingredientsList.get(11).toString();
                    tvIng11.setVisibility(View.VISIBLE);
                    tvIng11.setText(Ing11);
                    break;
                case 12:
                    Ing12 = ingredientsList.get(12).toString();
                    tvIng12.setVisibility(View.VISIBLE);
                    tvIng12.setText(Ing12);
                    break;
                case 13:
                    Ing13 = ingredientsList.get(13).toString();
                    tvIng13.setVisibility(View.VISIBLE);
                    tvIng13.setText(Ing13);
                    break;
                case 14:
                    Ing14 = ingredientsList.get(14).toString();
                    tvIng14.setVisibility(View.VISIBLE);
                    tvIng14.setText(Ing14);
                    break;
                case 15:
                    Ing15 = ingredientsList.get(15).toString();
                    tvIng15.setVisibility(View.VISIBLE);
                    tvIng15.setText(Ing15);
                    break;
                case 16:
                    Ing16 = ingredientsList.get(16).toString();
                    tvIng16.setVisibility(View.VISIBLE);
                    tvIng16.setText(Ing16);
                    break;
                case 17:
                    Ing17 = ingredientsList.get(17).toString();
                    tvIng17.setVisibility(View.VISIBLE);
                    tvIng17.setText(Ing17);
                    break;
                case 18:
                    Ing18 = ingredientsList.get(18).toString();
                    tvIng18.setVisibility(View.VISIBLE);
                    tvIng18.setText(Ing18);
                    break;
                case 19:
                    Ing19 = ingredientsList.get(19).toString();
                    tvIng19.setVisibility(View.VISIBLE);
                    tvIng19.setText(Ing19);
                    break;
                default:
                    break;
            }
        }

        /*if (Ing != null) {
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
        }*/
        
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
        imgLike=findViewById(R.id.imgLike);
        imgDisLike=findViewById(R.id.imgDisLike);
        imgFav=findViewById(R.id.imgFav);
        imgShare=findViewById(R.id.imgShare);
        tvLikesCount=findViewById(R.id.tvLikesCount);
        tvDislikesCount=findViewById(R.id.tvDislikesCount);
        prefs = getSharedPreferences("locations", MODE_PRIVATE);
        spEditor = getSharedPreferences("locations", MODE_PRIVATE).edit();
        particularParent=findViewById(R.id.particularParent);
        imgHome=findViewById(R.id.imgHome);
        imgAllFoodList=findViewById(R.id.imgAllFoodList);
        imgProfile=findViewById(R.id.imgProfile);


    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imgHome :
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            case R.id.imgAllFoodList :
                startActivity(new Intent(getApplicationContext(), AllFoodsList.class));
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
}