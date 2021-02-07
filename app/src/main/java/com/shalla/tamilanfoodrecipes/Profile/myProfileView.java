package com.shalla.tamilanfoodrecipes.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.shalla.tamilanfoodrecipes.R;

import static com.shalla.tamilanfoodrecipes.Profile.LoginActivity.prefs;
import static com.shalla.tamilanfoodrecipes.Profile.LoginActivity.spEditor;

public class myProfileView extends AppCompatActivity {

    Button btnLogout;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_view);
        prefs = getSharedPreferences("locations", MODE_PRIVATE);
        spEditor = getSharedPreferences("locations", MODE_PRIVATE).edit();
        btnLogout=findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getApplicationContext(), "Logout Success", Toast.LENGTH_SHORT).show();
                spEditor.putString("isGuest","no");
                spEditor.apply();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}