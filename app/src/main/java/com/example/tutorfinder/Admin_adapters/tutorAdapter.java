package com.example.tutorfinder.Admin_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tutorfinder.Admin_models.Tutors;
import com.example.tutorfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class tutorAdapter<context> extends RecyclerView.Adapter<tutorAdapter.tutorViewHolder> {

    ArrayList<Tutors> tlist;
    Context context;

    public tutorAdapter(ArrayList<Tutors> tlist, Context context) {
        this.tlist = tlist;
        this.context = context;
    }


    @NonNull
    @Override
    public tutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View v2 = LayoutInflater.from(context).inflate(R.layout.activity_admin_tutors_details,parent,false);
        View vt = LayoutInflater.from(context).inflate(R.layout.activity_admin_tutors_details,parent,false);
        return new tutorViewHolder(vt);
    }

    @Override
    public void onBindViewHolder(@NonNull tutorViewHolder holder, int position) {
        Tutors t = tlist.get(position);
        holder.NIC.setText(t.getNIC());
        holder.address.setText(t.getAddress());
        holder.email.setText(t.getEmail());
        holder.name.setText(t.getName());
        holder.password.setText(t.getPassword());
        holder.phoneNo.setText(t.getPhoneNo());
        holder.subject.setText(t.getSubject());
        holder.qualifications.setText(t.getQualifications());
    }

    @Override
    public int getItemCount() {
        return tlist.size();
    }

    public static class tutorViewHolder extends RecyclerView.ViewHolder {

        FirebaseDatabase db;
        DatabaseReference ref;

        TextView NIC,address,email,name,password,phoneNo,subject,qualifications;
        Button deletetutors;

        public tutorViewHolder(@NonNull View itemView) {
            super(itemView);

            NIC = itemView.findViewById(R.id.rvNIC_tutorview);
            address = itemView.findViewById(R.id.rvaddress_tutorview);
            email = itemView.findViewById(R.id.rvEmail_tutorview);
            name = itemView.findViewById(R.id.rvname_tutorview);
            password = itemView.findViewById(R.id.rvpwd_tutorview);
            phoneNo = itemView.findViewById(R.id.rvphoneno_tutorview);
            subject = itemView.findViewById(R.id.rvsubject_tutorview);
            qualifications = itemView.findViewById(R.id.rvqalific_tutorview);

            deletetutors = itemView.findViewById(R.id.btndeclinetutors_tutorview);
            deletetutors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseDatabase.getInstance();
                    ref = db.getReference("Tutors");

                    Tutors deleteTutor = new Tutors(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
                            password.getText().toString(),phoneNo.getText().toString(),subject.getText().toString(),qualifications.getText().toString());

                    ref.child(deleteTutor.getNIC()).removeValue();
                }
            });
        }
    }
}

