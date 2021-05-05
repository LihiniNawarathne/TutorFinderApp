package com.example.tutorfinder.AdminUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tutorfinder.Admin_adapters.studentsAdapter;
import com.example.tutorfinder.Admin_models.Studnets;
import com.example.tutorfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_view_students extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference("Student");


    private studentsAdapter stuadapter;
    private ArrayList<Studnets> slist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_students);

        recyclerView = findViewById(R.id.recyclerStudentview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        slist = new ArrayList<>();
        //adapter = new MyAdapter(this,list);
        //recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Studnets stu = dataSnapshot.getValue(Studnets.class);
                    slist.add(stu);
                    stuadapter = new studentsAdapter(slist, admin_view_students.this);
                    recyclerView.setAdapter(stuadapter);
                }
                stuadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}