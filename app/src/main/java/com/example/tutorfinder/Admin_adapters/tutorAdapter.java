package com.example.tutorfinder.Admin_adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tutorfinder.Admin_models.Tutors;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class tutorAdapter<context> extends RecyclerView.Adapter<tutorAdapter.tutorViewHolder> {

    ArrayList<Tutors> tlist;
    static Context context;

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
    public void onBindViewHolder(@NonNull tutorAdapter.tutorViewHolder holder, int position) {
        Tutors t = tlist.get(position);
        holder.NIC.setText(t.getNIC());
        holder.address.setText(t.getAddress());
        holder.email.setText(t.getEmail());
        holder.name.setText(t.getName());
        //holder.password.setText(t.getPassword());
        holder.phoneno.setText(t.getPhoneno());
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

        TextView NIC,address,email,name,phoneno,subject,qualifications;
        Button deletetutors;

        public tutorViewHolder(@NonNull View itemView) {
            super(itemView);

            NIC = itemView.findViewById(R.id.rvNIC_tutorview);
            address = itemView.findViewById(R.id.rvaddress_tutorview);
            email = itemView.findViewById(R.id.rvEmail_tutorview);
            name = itemView.findViewById(R.id.rvname_tutorview);
            //password = itemView.findViewById(R.id.rvpwd_tutorview);
            phoneno = itemView.findViewById(R.id.rvphoneno_tutorview);
            subject = itemView.findViewById(R.id.rvsubject_tutorview);
            qualifications = itemView.findViewById(R.id.rvqalific_tutorview);

            deletetutors = itemView.findViewById(R.id.btndeclinetutors_tutorview);



//            deletetutors.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                    db = FirebaseDatabase.getInstance();
//                    ref = db.getReference("Tutors");
//
//                    Tutors deleteTutor = new Tutors(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
//                            phoneno.getText().toString(),subject.getText().toString(),qualifications.getText().toString());
//
//
//                    ref.child(deleteTutor.getNIC()).removeValue();
//
//                    Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
//                }
//            });
            deletetutors.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    db = FirebaseDatabase.getInstance();
                    //mAuth = FirebaseAuth.getInstance();
                    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                    ref = db.getReference("Tutors");

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Are you sure?");
                    dialog.setMessage("Permanently delete, "+name.getText().toString()+" tutor from Tutor finder organization");
                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            fuser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(context, "Account deleted", Toast.LENGTH_SHORT).show();
//
//
//                                    }
//                                }
//                            });
                            Tutors deleteTutor = new Tutors(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
                                    phoneno.getText().toString(),subject.getText().toString(),qualifications.getText().toString());

                            ref.child(deleteTutor.getNIC()).removeValue();
                            Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog =dialog.create();
                    alertDialog.show();
                }
            });
        }
    }
}