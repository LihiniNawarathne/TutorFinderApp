package com.example.tutorfinder.Admin_adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.tutorfinder.Admin_models.Studnets;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class studentsAdapter<context> extends RecyclerView.Adapter<studentsAdapter.stuViewHolder> {


    ArrayList<Studnets> slist;
    static Context context;

    public studentsAdapter(ArrayList<Studnets> slist, Context context) {
        this.slist = slist;
        this.context = context;
    }


    @NonNull
    @Override
    public studentsAdapter.stuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(context).inflate(R.layout.activity_admin_students_details,parent,false);
        return new stuViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull stuViewHolder holder, int position) {
        Studnets s = slist.get(position);

        holder.nic.setText(s.getNic());
        holder.name.setText(s.getName());
        holder.email.setText(s.getEmail());
        holder.phone.setText(s.getPhone());
        holder.school.setText(s.getSchool());
        holder.alstream.setText(s.getAlstream());
        holder.dob.setText(s.getDob());
        //Picasso.get().load(s.getProimg()).into(holder.proimg);
        Glide.with(context).load(s.getProimg()).into(holder.proimg);
        holder.uid.setText(s.getUid());
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public static class stuViewHolder extends RecyclerView.ViewHolder {

        FirebaseDatabase db;
        DatabaseReference ref;

        ImageView proimg;
        TextView alstream,dob,email,name,nic,phone,school,uid;
        Button btndelete;
        public stuViewHolder(@NonNull View itemView) {
            super(itemView);
            proimg = itemView.findViewById(R.id.rvimgpro);
            alstream = itemView.findViewById(R.id.rvalstream);
            dob = itemView.findViewById(R.id.rvdob);
            email = itemView.findViewById(R.id.rvstuemail);
            name = itemView.findViewById(R.id.rvsname);
            nic = itemView.findViewById(R.id.rvsnic);
            phone = itemView.findViewById(R.id.rvsphone);
            school = itemView.findViewById(R.id.rvschool);
            uid = itemView.findViewById(R.id.rvuid);
            btndelete =itemView.findViewById(R.id.btndeclinestudents);

//            btndelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    db = FirebaseDatabase.getInstance();
//                    ref = db.getReference("Student");
//
//                    Studnets stu = new Studnets(proimg.getResources().toString(),alstream.getText().toString(),dob.getText().toString(),email.getText().toString(),name.getText().toString(),nic.getText().toString(),phone.getText().toString(),
//                            school.getText().toString(),uid.getText().toString());
//
//                    ref.child(stu.getUid()).removeValue();
//
//                    Toast.makeText(context, "student deleted successfully", Toast.LENGTH_SHORT).show();
//                }
//            });

            btndelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    db = FirebaseDatabase.getInstance();
                    //mAuth = FirebaseAuth.getInstance();
                    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                    ref = db.getReference("Tutors");

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Are you sure?");
                    dialog.setMessage("Do you want to delete student , " + name.getText().toString() + "");
                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db = FirebaseDatabase.getInstance();
                            ref = db.getReference("Student");

                            Studnets stu = new Studnets(proimg.getResources().toString(), alstream.getText().toString(), dob.getText().toString(), email.getText().toString(), name.getText().toString(), nic.getText().toString(), phone.getText().toString(),
                                    school.getText().toString(), uid.getText().toString());

                            ref.child(stu.getUid()).removeValue();

                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
            });
        }
    }
}