package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorfinder.Database.ClassHelperClass;
import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchClass extends AppCompatActivity {

    DatabaseReference reference;

    RecyclerView recyclerView;
    AdapterClassGroupList adapterClass;
    ArrayList<ClassHelperClass> classlist;

    String subject;
    Button join;
    EditText className,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search Results");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView =findViewById(R.id.rvSearchGroup);

        join= findViewById(R.id.btnjoinGroup);

        //Retrieve the value
        Intent intent=getIntent();
        subject = intent.getExtras().getString("subjectName");


        classlist=new ArrayList<>();
        getAllClasses(subject);

//        join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(searchClass.this, amount.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(searchClass.this, uploadSlipImgStudent.class);
//                intent.putExtra("className", className.getText().toString());
//                intent.putExtra("amount", amount.getText().toString());
//
//                startActivity(intent);
//            }
//        });

    }

    private void getAllClasses(String subject) {

        Toast.makeText(this, "Sub"+subject, Toast.LENGTH_SHORT).show();
        reference = FirebaseDatabase.getInstance().getReference("ClassGroups");

        Query checkUser = reference.orderByChild("subject").equalTo(subject);

        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clearing previous info
                classlist.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    ClassHelperClass modelclass=ds.getValue(ClassHelperClass.class);

                    classlist.add(modelclass);
                    adapterClass= new AdapterClassGroupList(searchClass.this,classlist);

                    recyclerView.setAdapter(adapterClass);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

            //progress Dialog
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait");
            pd.setMessage("Login out..");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(searchClass.this, LoginActivity.class);

            pd.dismiss();

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