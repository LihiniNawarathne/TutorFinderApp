package com.example.tutorfinder.MainUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView etvForgottenEmail;
    Button btnforgot;
    String email;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase rootNode;
    DatabaseReference reference1,reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Up");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initialize firebaseauth instance
        mAuth = FirebaseAuth.getInstance();

        etvForgottenEmail = findViewById(R.id.etvForgottenEmail);
        btnforgot = findViewById(R.id.btnforgot);

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etvForgottenEmail.getText().toString().trim();

                if(email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etvForgottenEmail.setError("Invalid Email");
                    etvForgottenEmail.setFocusable(true);
                }
                else {
                        recoverPassword();
                }

            }
        });

    }

    private void recoverPassword() {

        //progress Dialog
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Please wait");
        pd.setMessage("Sending email....");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                pd.dismiss();

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Email has been sent..", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                }
                else
                    Toast.makeText(ForgotPasswordActivity.this, "Failed..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(ForgotPasswordActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}