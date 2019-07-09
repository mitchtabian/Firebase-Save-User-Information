package com.tabian.firebasesaveuserdata;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // UI references.
    private EditText mEmail, mPassword;
    private Button btnSignIn,btnSignOut, btnViewDatabase,btnAddToDatabase,btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declare buttons and edit texts in oncreate
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.email_sign_up_button);//NewUpdate
        btnSignIn = findViewById(R.id.email_sign_in_button);
        btnSignOut = findViewById(R.id.email_sign_out_button);
        btnViewDatabase = findViewById(R.id.view_items_screen);
        btnAddToDatabase = findViewById(R.id.user_information);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                if(!email.equals("") && !pass.equals("")){
                    mAuth.createUserWithEmailAndPassword(email,pass);
                }else{
                    toastMessage("You didn't fill in all the fields.");
                }

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                if(!email.equals("") && !pass.equals("")){
                    mAuth.signInWithEmailAndPassword(email,pass);
                }else{
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Signing Out...");
            }
        });

        btnViewDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDatabase.class);
                startActivity(intent);
            }
        });

        btnAddToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddToDatabase.class);
                startActivity(intent);
            }
        });


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


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
