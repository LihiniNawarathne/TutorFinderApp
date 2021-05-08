package com.example.tutorfinder.AdminUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorfinder.Admin_adapters.studentpaymentAdapter;
import com.example.tutorfinder.Admin_models.studentPayment;
import com.example.tutorfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_add_chatgroup_tec extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference("joinGroupClass");
    // Button add,delete;

    private studentpaymentAdapter adapter;
    private ArrayList<studentPayment> stupaymentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_chatgroup_tec);

        recyclerView = findViewById(R.id.rvtec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stupaymentList = new ArrayList<>();

        root.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stupaymentList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    studentPayment studentPay = dataSnapshot.getValue(studentPayment.class);
                    if (studentPay.getAlstream().equals("technology")) {
                        stupaymentList.add(studentPay);
                        adapter = new studentpaymentAdapter(stupaymentList, admin_add_chatgroup_tec.this);
                        recyclerView.setAdapter(adapter);
                    }
                }
                //adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
