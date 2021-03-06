package com.example.tutorfinder.MainUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.AdminUI.Admin_MainActivity;
import com.example.tutorfinder.R;
import com.example.tutorfinder.StudentUI.DashboardActivity;
import com.example.tutorfinder.TutorUI.CreateClassTutor;
import com.example.tutorfinder.TutorUI.Selections;
import com.example.tutorfinder.TutorUI.TutorViewDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    //views
    TextView registerlink,forgot_passwordlink;
    EditText lemail,lpassword;
    Button btnLogin;

    String loginEmail,loginPassword;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase rootNode;
    DatabaseReference reference1,reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize firebaseauth instance
        mAuth = FirebaseAuth.getInstance();

        lemail = findViewById(R.id.emailLogin);
        lpassword = findViewById(R.id.passwordLogin);
        registerlink = findViewById(R.id.tvhLink2);
        forgot_passwordlink =findViewById(R.id.tvhLink1);
        btnLogin = findViewById(R.id.btnLogin);

        //navigate to myprofile once click the login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =lemail.getText().toString().trim();
                String password = lpassword.getText().toString().trim();


                //validate
                if(!email.isEmpty() && !password.isEmpty()) {

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        lemail.setError("Invalid Email");
                        lemail.setFocusable(true);
                    } else if (password.length() < 6) {
                        //set error
                        lpassword.setError("Password length should be at least 6 characters");
                        lpassword.setFocusable(true);
                        //Toast.makeText(registerStudent2.this, "Password too short", Toast.LENGTH_SHORT).show();
                    } else {
                        //check whether the user exist or not
                        loginUser(email, password);
                    }

                }
                else{
                    Toast.makeText(LoginActivity.this, "Fields cannot be empty..", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //navigate to register user page
        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,registerUserSelect.class ));
            }
        });

        //navigate to forget password page
        forgot_passwordlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class ));
            }
        });
    }


    public void loginUser(String loginEmail, String loginPassword){


            //progress Dialog
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait");
            pd.setMessage("Logging in....");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            mAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            if (!task.isSuccessful()) {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            } else {

                                user = mAuth.getCurrentUser();
                                String uemail = user.getEmail();
                                rootNode = FirebaseDatabase.getInstance();
                                reference1 = rootNode.getReference("Student");
                                reference2 = rootNode.getReference("Tutors");

                                Query checkUser1 = reference1.orderByChild("email").equalTo(uemail);

                                //check user role
                                //if user is student
                                checkUser1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {

                                            pd.dismiss();

                                            Log.d("TAG", "Student Login");
                                            Toast.makeText(LoginActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                        } else {
                                            //if user is tutor
                                            Query checkUser2 = reference2.orderByChild("email").equalTo(uemail);

                                            checkUser2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {

                                                        pd.dismiss();

                                                        Log.d("TAG", "Tutor Login");

                                                        Toast.makeText(LoginActivity.this, "Tutor Exists", Toast.LENGTH_SHORT).show();


                                                        startActivity(new Intent(LoginActivity.this, Selections.class));


                                                    } else if(uemail.matches("(.*)admin(.*)")){
                                                        pd.dismiss();

                                                        //else the user is admin
                                                        Toast.makeText(LoginActivity.this, "Hello Admin", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(LoginActivity.this, Admin_MainActivity.class));


                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    pd.dismiss();

                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        pd.dismiss();
                                    }
                                });

//                            FirebaseUser  user = mAuth.getCurrentUser();
//
//                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
//
//                                String email =
//
//                            }


//                            Toast.makeText(LoginActivity.this, "User Exists", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //get and show the error
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


    }

}