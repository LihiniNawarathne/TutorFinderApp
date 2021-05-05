package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.Database.StudentHelperClass;
import com.example.tutorfinder.Database.joinClass;
import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static java.lang.Long.parseLong;

public class uploadSlipImgStudent extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference1,reference2;
    FirebaseUser user;

    //view
    ImageButton paymentSlip;
    Button send;
    long paymentid,paymenyID=0,amount;
    String StudentUID,className,subject,samount,Stream;
    TextView name,payment;

    private Uri imageurl;
    String img ="https://img.traveltriangle.com/blog/wp-content/tr:w-700,h-400/uploads/2015/06/Train-ride-from-Kandy-to-Nuwara-Eliya.jpg";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_slip_img_student);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Up");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference1 = rootNode.getReference("joinGroupClass");
        reference2 = rootNode.getReference("Student");

        //get data from searchClass by intent
        className = getIntent().getStringExtra("className") ;
        samount= getIntent().getStringExtra("amount");
        subject= getIntent().getStringExtra("subject");
        amount= Long.parseLong(samount);//convert to long

        name=findViewById(R.id.tvclasspaid1);
        payment=findViewById(R.id.tvpaidAmount1);

        //set text view with strings
        name.setText("Class Name :"+className);
        payment.setText("Total Amount(RS.) :"+samount);

        paymentSlip =findViewById(R.id.slipimageButton);
        send= findViewById(R.id.btnsendSlip);

                //get last paymentID
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            paymentid =(snapshot.getChildrenCount());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //get Stream of Student
                Query checkUser = reference2.orderByChild("email").equalTo(user.getEmail());

                checkUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot ds:snapshot.getChildren()){
                            //get student's stream
                            Stream="" + ds.child("alstream").getValue();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("Message",""+paymentid);

                addToPayments(paymentid);
            }
        });

    }


    private void addToPayments(long paymentid) {

        StudentUID =user.getUid();

        paymenyID = paymentid+1;

        joinClass addNewPayment= new joinClass(paymenyID,className,StudentUID,amount,img,subject,Stream);

        reference1.child(String.valueOf(paymenyID)).setValue(addNewPayment).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    //Log.e("Message",className);
                    Toast.makeText(uploadSlipImgStudent.this, "Payment is Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(uploadSlipImgStudent.this, DashboardActivity.class));

                }
                else {
                    //if an unsuccessful registration direct back to registration form with a toast
                    Toast.makeText(uploadSlipImgStudent.this, "Payment is Unsuccessful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(uploadSlipImgStudent.this, DashboardActivity.class));
                }
            }
        });

//        HashMap<String,Object> map = new HashMap<>();
//        map.put("ClassID",ClassID);
//        map.put("className",className);
//        map.put("StudentNIC",StudentNIC);
//        map.put("amount",amount);
//        map.put("img",img);
//
//        reference1.child(String.valueOf(ClassID)).updateChildren(map);
    }


    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}