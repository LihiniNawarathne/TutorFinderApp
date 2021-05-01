package com.example.tutorfinder.Admin_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tutorfinder.Admin_models.TutorRequestHandler;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TutorRequestAdapter<context> extends RecyclerView.Adapter<TutorRequestAdapter.tutorRequestViewHolder>{

    ArrayList<TutorRequestHandler> rlist;
    Context context;

    public TutorRequestAdapter(Context context, ArrayList<TutorRequestHandler> rlist){
        this.rlist=rlist;
        this.context=context;
    }
    @NonNull
    @Override
    public tutorRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_admin_requestform,parent,false);
        return new tutorRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull tutorRequestViewHolder holder, int position) {
        TutorRequestHandler TutorRequestHandler = rlist.get(position);
        holder.NIC.setText(TutorRequestHandler.getNIC());
        holder.address.setText(TutorRequestHandler.getAddress());
        holder.email.setText(TutorRequestHandler.getEmail());
        holder.name.setText(TutorRequestHandler.getName());
        holder.password.setText(TutorRequestHandler.getPassword());
        holder.phoneNo.setText(TutorRequestHandler.getPhoneNo());
        holder.subject.setText(TutorRequestHandler.getSubject());
        holder.qualifications.setText(TutorRequestHandler.getQualifications());
    }

    @Override
    public int getItemCount() {

        return rlist.size();
    }

    public static class tutorRequestViewHolder extends RecyclerView.ViewHolder{

        FirebaseDatabase db;
        DatabaseReference ref;
        //Tutors t;

        TextView NIC,address,email,name,password,phoneNo,subject,qualifications;
        Button btnAccept,btndecline;
        public tutorRequestViewHolder(@NonNull View itemView) {

            super(itemView);
            NIC = itemView.findViewById(R.id.rvNIC);
            address = itemView.findViewById(R.id.rvaddress);
            email = itemView.findViewById(R.id.rvEmail);
            name = itemView.findViewById(R.id.rvname);
            password = itemView.findViewById(R.id.rvpwd);
            phoneNo = itemView.findViewById(R.id.rvphoneno);
            subject = itemView.findViewById(R.id.rvsubject);
            qualifications = itemView.findViewById(R.id.rvqalific);
            //button accept
            btnAccept = itemView.findViewById(R.id.btnaccept);

            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseDatabase.getInstance();
                    ref = db.getReference("Tutors");

                    //---------------------------------------------------------------AUTHENTICATION-------------------------------------------------------------------

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //TutorRequestHandler tutors = new TutorRequestHandler(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
                                //password.getText().toString(),phoneNo.getText().toString(),subject.getText().toString(),qualifications.getText().toString());

                                //ref.child(tutors.getNIC()).setValue(tutors);
                            }
                            else{

                            }
                        }
                    });
                    //-----------------------------------------------------------------------------------------------------------------------------------------

                    TutorRequestHandler tutors = new TutorRequestHandler(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
                            password.getText().toString(),phoneNo.getText().toString(),subject.getText().toString(),qualifications.getText().toString());

                    ref.child(tutors.getNIC()).setValue(tutors);
                    //after cliking the accept button this request form will be deleted in the request tutors table
                    ref = db.getReference("TutorRequest");
                    ref.child(tutors.getNIC()).removeValue();
                }
            });
            //button decline this button use to when admin decline the request this form also deleted in the table
            btndecline = itemView.findViewById(R.id.btndecline);
            btndecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseDatabase.getInstance();
                    ref = db.getReference("TutorRequest");

                    TutorRequestHandler dtutor = new TutorRequestHandler(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
                            password.getText().toString(),phoneNo.getText().toString(),subject.getText().toString(),qualifications.getText().toString());

                    ref.child(dtutor.getNIC()).removeValue();
                }
            });

        }

    }
}
