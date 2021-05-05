package com.example.tutorfinder.StudentUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;
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


public class ProfileFragmentStudent extends Fragment {

    //firebase
    FirebaseDatabase rootNode;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    //views
    CircleImageView propic;
    TextView StudentName,SBirthDay,SPhonepro,Streampro,Semail;
    Button editProfile,deleteProfile;

    String nic,name,dob,phone,alstream,proimg,email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_student, container, false);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("Student");

        //init
        propic = view.findViewById(R.id.imgPropic);
        StudentName = view.findViewById(R.id.tvStudentName);
        SBirthDay= view.findViewById(R.id.tvSBirthDay);
        SPhonepro = view.findViewById(R.id.tvSPhonepr);
        Streampro=view.findViewById(R.id.tvStreampro);
        Semail = view.findViewById(R.id.tvSemail);
        editProfile =view.findViewById(R.id.buttoneditPro);
        deleteProfile =view.findViewById(R.id.buttondeletetPro);

        Query checkUser = reference.orderByChild("email").equalTo(user.getEmail());
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //get data
                    name =""+ds.child("name").getValue();
                    dob =""+ds.child("dob").getValue();
                    phone =""+ds.child("phone").getValue();
                    nic=""+ds.child("nic").getValue();
                    alstream =""+ds.child("alstream").getValue();
                    proimg=""+ds.child("proimg").getValue();
                    email=""+ds.child("email").getValue();

                    //set data
                    StudentName.setText(name);
                    SBirthDay.setText(dob);
                    SPhonepro.setText(phone);
                    Streampro.setText(alstream );
                    Semail.setText(email);

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

                startActivity(new Intent(getActivity(), UpdateDetailsStudent.class));
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
                            Toast.makeText(getActivity(), "User has been deleted Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                        else {
                            //if an unsuccessful registration direct back to registration form with a toast
                            Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        return view;
    }
}