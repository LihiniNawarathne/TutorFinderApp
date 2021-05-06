package com.example.tutorfinder.TutorUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tutorfinder.AdminUI.admin_view_students;
import com.example.tutorfinder.Admin_adapters.studentsAdapter;

import com.example.tutorfinder.Database.TutorStudentsView;
import com.example.tutorfinder.R;
import com.example.tutorfinder.Tutor_Adapter.ClassStudentsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference("Student");


    private ClassStudentsAdapter stuadapter;
    private ArrayList<TutorStudentsView> slist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdetails);

        recyclerView = findViewById(R.id.recyclerStudentview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        slist = new ArrayList<>();
        root.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TutorStudentsView stu = dataSnapshot.getValue(TutorStudentsView.class);
                    slist.add(stu);
                    stuadapter = new ClassStudentsAdapter(slist, StudentDetails.this);
//                    recyclerView.setAdapter(stuadapter);
                }
                stuadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
