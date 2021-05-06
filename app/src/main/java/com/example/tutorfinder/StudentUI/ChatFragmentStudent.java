package com.example.tutorfinder.StudentUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutorfinder.Database.ChatHelperClass;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatFragmentStudent extends Fragment {

    private FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;

    RecyclerView recyclerView;

    AdapterChatGroupsList adapterGroups;
    ArrayList<ChatHelperClass> groupList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_chat, container, false);

        recyclerView =view.findViewById(R.id.rvChatGroup);

        groupList=new ArrayList<>();

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("ChatGroups");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clearing previous info
                groupList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //check wheteher the user is in the participant list
                    if(ds.child("participents").child(user.getUid()).exists()) {
                        ChatHelperClass modelclass = ds.getValue(ChatHelperClass.class);

                        groupList.add(modelclass);
                        adapterGroups = new AdapterChatGroupsList(getActivity(), groupList);

                        recyclerView.setAdapter(adapterGroups);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}