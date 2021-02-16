package com.shalla.tamilanfoodrecipes.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shalla.tamilanfoodrecipes.MainActivity;
import com.shalla.tamilanfoodrecipes.R;


public class LoginActivity extends AppCompatActivity {

    //Const
    private static final String TAG = "LoginActivity";
    private Context mContext = LoginActivity.this;

    //Vars
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;

    //Id's
    private Button ContinueAsGuest,phone_signup;

    private ProgressBar progressBar;

    TextInputEditText editTextCountryCode, editTextPhone;
    AppCompatButton buttonContinue;
    ProgressDialog progressDialog;
    String code,number;
    public static String isGuest;
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enableData();

        progressBar = findViewById(R.id.progressBar2);
        editTextCountryCode = findViewById(R.id.editTextCountryCode);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonContinue = findViewById(R.id.buttonContinue);
        phone_signup=findViewById(R.id.signupMobileNum);
        ContinueAsGuest=findViewById(R.id.ContinueasGuest);
        db= FirebaseFirestore.getInstance();
        prefs = getSharedPreferences("locations", MODE_PRIVATE);
        spEditor = getSharedPreferences("locations", MODE_PRIVATE).edit();

        setupFirebaseAuth();

        init();
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                 code = editTextCountryCode.getText().toString().trim();
                 number = editTextPhone.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editTextPhone.setError("Valid number is required");
                    editTextPhone.requestFocus();
                    return;
                }
                ProgeressDialog();
                progressDialog.show();
                Query query = db.collection("Users");
                query.whereEqualTo("phone_number", code+number).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot verify) {
                        try{
                        if (!verify.isEmpty()) {
                            progressDialog.dismiss();
                            String phoneNumber = code + number;
                            Intent intent = new Intent(LoginActivity.this, VerifyPhoneLoginActivity.class);
                            intent.putExtra("phoneNumber", phoneNumber);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            editTextPhone.setError("This number not registered, Please create an account");
                        }

                        }catch(Exception e){ Log.d("error is : ",e.getMessage());}
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "No records found, create a account now", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        
        phone_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,Phone_Signup.class));
                finish();
            }
        });
        ContinueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spEditor.putString("isGuest","yes");
                spEditor.apply();
                startActivity(new Intent(mContext,MainActivity.class));
                finish();
            }
        });

    }
    public void onBackPressed() {
        AlertDialog.Builder builderExit=new AlertDialog.Builder(mContext);
        builderExit.setTitle("Exit ?");
        builderExit.setMessage("Do you want to exit ?");
        builderExit.setCancelable(false);

        builderExit.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setIcon(R.drawable.common_full_open_on_phone).show();
    }

    private boolean isStringNull(String string){
        return string.equals("");
    }

    /*
    * Firebase login methods
    * */

    private void init(){
        try{
            isGuest=prefs.getString("isGuest","");
            if(isGuest=="yes"){
                Toast.makeText(mContext, "You are entering by guest account", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                if (mAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }catch(Exception e){ Log.d("error is : ",e.getMessage());}
    }

    private void setupFirebaseAuth(){
        try{
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        }catch(Exception e){ Log.d("error is : ",e.getMessage());}
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //Prompting user to enable data connection
    public boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if((wifi != null & cm != null)
                && (wifi.isConnected()| datac.isConnected())){
            return true;
        } else {
            return false;

        }

    }   //Prompting user to enable data connection
    public void enableData() {
        final AlertDialog.Builder builderExit = new AlertDialog.Builder(mContext);

        if(!isOnline()==true){
            LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
            final View view = factory.inflate(R.layout.image_for_dialog, null);
            builderExit.setTitle("No Data Connection Available");
            builderExit.setMessage("Please Enable Internet or Wifi Connection To Continue.");
            builderExit.setCancelable(false);
            builderExit.setView(view);
            builderExit.setIcon(R.drawable.no_wifi_foreground);
            builderExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            builderExit.show();

        }
    }
    private void ProgeressDialog(){
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing please wait");
    }
}