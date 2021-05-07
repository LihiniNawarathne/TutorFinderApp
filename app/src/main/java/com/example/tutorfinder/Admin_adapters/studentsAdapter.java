package com.example.tutorfinder.Admin_adapters;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class studentsAdapter<context> extends RecyclerView.Adapter<studentsAdapter.stuViewHolder> {

    ArrayList<Studnets> slist;
    Context context;

    public studentsAdapter(ArrayList<Studnets> slist, Context context) {
        this.slist = slist;
        this.context = context;
    }


    @NonNull
    @Override
    public stuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        Glide.with(context).load(s.getProimg()).into(holder.proimg);
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public static class stuViewHolder extends RecyclerView.ViewHolder {
        //connection
        FirebaseDatabase db;
        DatabaseReference ref;

        ImageView proimg;
        TextView alstream,dob,email,name,nic,phone,school,uid;
        Button btndelete;

        public stuViewHolder(@NonNull View itemView) {
            super(itemView);

            alstream = itemView.findViewById(R.id.rvalstream);
            dob = itemView.findViewById(R.id.rvdob);
            email = itemView.findViewById(R.id.rvstuemail);
            name = itemView.findViewById(R.id.rvsname);
            nic = itemView.findViewById(R.id.rvsnic);
            phone = itemView.findViewById(R.id.rvsphone);
            school = itemView.findViewById(R.id.rvschool);
            uid = itemView.findViewById(R.id.rvuid);
            btndelete =itemView.findViewById(R.id.btndeclinestudents);

            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseDatabase.getInstance();
                    ref = db.getReference("Student");

                    Studnets stu = new Studnets(proimg.getResources().toString(),alstream.getText().toString(),dob.getText().toString(),email.getText().toString(),name.getText().toString(),nic.getText().toString(),phone.getText().toString(),
                            school.getText().toString(),uid.getText().toString());

                    ref.child(stu.getUid()).removeValue();
                    //Toast.makeText(, "dd", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
