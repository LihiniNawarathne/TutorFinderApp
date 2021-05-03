package com.example.tutorfinder.StudentUI;

import android.app.Notification;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutorfinder.Database.NotificationModel;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationFragmentStudent extends Fragment {

    RecyclerView rvNotification;

    ArrayList<NotificationModel> notificationList;
    AdapterNotification adapterNotification;

    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_student, container, false);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Student");


        //init recyclerview
        rvNotification = view.findViewById(R.id.rvNotification);

        getAllnotifications();

        return view;
    }

    private void getAllnotifications() {

        notificationList = new ArrayList<>();

        reference.child(user.getUid()).child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //getData
                    NotificationModel model = ds.getValue(NotificationModel.class);

                    //add to list
                    notificationList.add(model);
                    //adapter
                    adapterNotification = new AdapterNotification(getContext(),notificationList);
                    //set to recyclerview
                    rvNotification.setAdapter(adapterNotification);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

