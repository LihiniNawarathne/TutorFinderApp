package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class selectedOfferStudent extends AppCompatActivity {

    //firebase
    FirebaseDatabase rootNode;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    String Stream;
    TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_offer_student);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search Results");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("OfferClasses");

        //get data from CourseOffersStudent
        Stream = getIntent().getStringExtra("Stream");

       // getSubjectDetails();


    }

//    private void getSubjectDetails() {
//
//        Query subjectStream = reference.child(Stream).orderByChild("Subject1");
//        subjectStream .addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot ds:snapshot.getChildren()){
//
//                    //get data
//                    name =""+ds.child("name").getValue();
//                    dob =""+ds.child("dob").getValue();
//                    phone =""+ds.child("phone").getValue();
//                    nic=""+ds.child("nic").getValue();
//                    alstream =""+ds.child("alstream").getValue();
//                    proimg=""+ds.child("proimg").getValue();
//                    email=""+ds.child("email").getValue();
//
//                    //set data
//                    StudentName.setText(name);
//                    SBirthDay.setText(dob);
//                    SPhonepro.setText(phone);
//                    Streampro.setText(alstream );
//                    Semail.setText(email);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}