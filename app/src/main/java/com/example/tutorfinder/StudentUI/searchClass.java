package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorfinder.StudentModels.ClassHelperClass;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search_class);

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

    }

    private void getAllClasses(String subject) {


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

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(searchClass.this, LoginActivity.class);

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