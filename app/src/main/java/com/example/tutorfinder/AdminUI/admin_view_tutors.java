package com.example.tutorfinder.AdminUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tutorfinder.Admin_adapters.tutorAdapter;
import com.example.tutorfinder.Admin_models.Tutors;
import com.example.tutorfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_view_tutors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference("Tutors");


    private tutorAdapter tutoradapter;
    private ArrayList<Tutors> tlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_tutors);

        recyclerView = findViewById(R.id.rcTutorView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tlist = new ArrayList<>();
        //adapter = new MyAdapter(this,list);
        //recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tutors t = dataSnapshot.getValue(Tutors.class);
                    tlist.add(t);
                    tutoradapter = new tutorAdapter(tlist, admin_view_tutors.this);
                    recyclerView.setAdapter(tutoradapter);
                }
                tutoradapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}