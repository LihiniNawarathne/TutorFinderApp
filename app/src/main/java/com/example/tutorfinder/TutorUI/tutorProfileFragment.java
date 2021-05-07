package com.example.tutorfinder.TutorUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;

import com.example.tutorfinder.StudentUI.UpdateDetailsStudent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.startActivity;
import static com.google.android.material.internal.ContextUtils.getActivity;


public class tutorProfileFragment extends Fragment {

    //firebase
    FirebaseDatabase rootNode;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    //views
    CircleImageView propic;
    TextView TutorName,Address,Phonepro,Qualifications,Temail;
    Button editProfile,deleteProfile;

    String nic,name,phone,qualifications,address,proimg,email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutor_profile, container, false);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("Tutors");

        //init
        propic = view.findViewById(R.id.imgPropic);
        TutorName = view.findViewById(R.id.tvTutorName);
        Address= view.findViewById(R.id.tvTAddress);
        Phonepro= view.findViewById(R.id.tvPhone);
        Qualifications=view.findViewById(R.id.tvQualifications);
        Temail = view.findViewById(R.id.tvTemail);
        editProfile =view.findViewById(R.id.buttoneditPro);
        deleteProfile =view.findViewById(R.id.buttondeletetPro);

        Query checkUser = reference.orderByChild("email").equalTo(user.getEmail());
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //get data
                    name =""+ds.child("name").getValue();

                    phone =""+ds.child("phone").getValue();
                    nic=""+ds.child("nic").getValue();
                    qualifications =""+ds.child("qualifications").getValue();
                    address=""+ds.child("address").getValue();
                    email=""+ds.child("email").getValue();
                    proimg=""+ds.child("proimg").getValue();

                    //set data
                    TutorName.setText(name);
                    Address.setText(address);
                    Phonepro.setText(phone);
                    Qualifications.setText(qualifications );
                    Temail.setText(email);

                    if(!proimg.isEmpty()) {

                        try {
                            //if image received set it to the image view
                            Picasso.get().load(proimg).into(propic);
                        } catch (Exception e) {
                            //if there is an exception load default image
                            Picasso.get().load(R.drawable.studentpro).into(propic);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //handle edit profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), TutorProfileUpdate.class));
            }
        });

        //handle delete profile
        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child(user.getUid()).removeValue();
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {

                            //Log.e("Message",className);
                            Toast.makeText(getActivity(), "User has been deleted ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                        else {
                            //if an unsuccessful registration direct back to registration form with a toast
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        return view;
    }
}