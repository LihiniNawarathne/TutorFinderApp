package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.Database.OfferModel;
import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class selectedOfferStudent extends AppCompatActivity {

    //firebase
    FirebaseDatabase rootNode;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    String Stream,subject1,tutor1,time1,amount;
    RecyclerView recyclerView;

    ArrayList<OfferModel> offerList;
    AdapterOfferList adapteroffer;

    TextView tvClassname,tvamount,tvfinalAmount;
    Button StbtnOfferSelect;

    long amount1,FullAmount;
    long finalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_selected_offer);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Selected Offer");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

       //init recyclerview
        recyclerView =findViewById(R.id.rvSelectedOffer);
        tvClassname =findViewById(R.id.tvClassname);
        tvamount=findViewById(R.id.tvamount);
        tvfinalAmount=findViewById(R.id.tvfinalAmount);
        StbtnOfferSelect=findViewById(R.id.StbtnOfferSelect);


        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("OfferClasses");

        //get data from CourseOffersStudent
        Stream = getIntent().getStringExtra("Stream");

        getSubjectDetails(Stream);
        getAmount();


        StbtnOfferSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Tot "+finalAmount);

                String getStringAmount = String.valueOf(finalAmount);

                Intent intent = new Intent(selectedOfferStudent.this, uploadSlipImgStudent.class);
                intent.putExtra("className", Stream);
                intent.putExtra("amount", getStringAmount);
                intent.putExtra("subject", "Special");

                startActivity(intent);
            }
        });

    }

    private void getSubjectDetails(String Stream) {

        offerList = new ArrayList<>();
        Toast.makeText(this, "Sub"+Stream, Toast.LENGTH_SHORT).show();

        reference.child(Stream).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //getData
                    OfferModel model = ds.getValue(OfferModel.class);

                    //add to list
                    offerList.add(model);
                    //adapter
                    adapteroffer = new AdapterOfferList(selectedOfferStudent.this,offerList);
                    //set to recyclerview
                    recyclerView.setAdapter(adapteroffer);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAmount() {

        //Query subjectStream = reference.child(Stream).orderByChild("Subject1");
        reference.child(Stream).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //get data
                    amount= ""+ds.child("amount").getValue();

                    amount1 = amount1 +Long.valueOf(amount);

                }
                //invoke getFinalAmount(amount1) to calculate the final amount
                getFinalAmount(amount1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //calculate Final Amount
    @SuppressLint("SetTextI18n")
    private void getFinalAmount(long amount1) {
        FullAmount =amount1;
        System.out.println("Full  "+FullAmount);

        //set Class name
        tvClassname.setText("Class Name :"+Stream);

        //set ToatlAmount
        tvamount.setText("Toatl Amount(For 6 months) :"+FullAmount);

        //getFinalAmount
        finalAmount = (long) (FullAmount * 0.8);

        //setFinalAMount
        tvfinalAmount.setText("  "+finalAmount);


    }

    //inflate option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating meny
        getMenuInflater().inflate(R.menu.menu_main_opt,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //handle menu item click logout
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();

        if(id==R.id.logoutoption){

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(selectedOfferStudent.this, LoginActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}