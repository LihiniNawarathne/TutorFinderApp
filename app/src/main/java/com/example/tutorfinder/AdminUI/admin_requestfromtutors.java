package com.example.tutorfinder.AdminUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tutorfinder.Admin_adapters.TutorRequestAdapter;
import com.example.tutorfinder.Admin_models.TutorRequestHandler;
import com.example.tutorfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_requestfromtutors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference("TutorRequest");
   // Button add,delete;

    private TutorRequestAdapter adapter;
    private ArrayList<TutorRequestHandler> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requestfromtutors);

        recyclerView = findViewById(R.id.recyclerViewrequest);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add = findViewById(R.id.btnaccept);
        //delete = findViewById(R.id.btndecline);

        list = new ArrayList<>();
        //adapter = new MyAdapter(this,list);
        //recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TutorRequestHandler tutorRequestHandler = dataSnapshot.getValue(TutorRequestHandler.class);
                    list.add(tutorRequestHandler);
                    adapter = new TutorRequestAdapter(admin_requestfromtutors.this,list);
                    recyclerView.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}