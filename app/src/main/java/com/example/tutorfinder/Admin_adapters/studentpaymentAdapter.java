package com.example.tutorfinder.Admin_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.tutorfinder.Admin_models.chatmodel;
import com.example.tutorfinder.Admin_models.studentPayment;
import com.example.tutorfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class studentpaymentAdapter<context> extends RecyclerView.Adapter<studentpaymentAdapter.stuPaymentViewHolder> {


    ArrayList<studentPayment> stupaymentList;
    static Context context;
    static String uid;
    static Long id;
    static Double pay;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    public studentpaymentAdapter(ArrayList<studentPayment> stupaymentList,Context context) {
        this.stupaymentList = stupaymentList;
        this.context=context;
    }

    @NonNull
    @Override
    public studentpaymentAdapter.stuPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.admin_check_paymnet_details, parent, false);
        return new stuPaymentViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull stuPaymentViewHolder holder, int position) {
        studentPayment sp = stupaymentList.get(position);
        holder.paidID.setText(String.valueOf(sp.getPaidID()));
        id = sp.getPaidID();
        holder.className.setText(sp.getClassName());
        holder.payment.setText(String.valueOf(sp.getPayment()));
        pay = sp.getPayment();
        //holder.studentUID.setText(sp.getStudentUID());
        holder.stream.setText(sp.getAlstream());
        holder.subject.setText(sp.getSubject());
        Glide.with(context).load(sp.getSlipIMG()).into(holder.slipIMG);

        uid = sp.getStudentUID();
        //holder.add.setText(holder.paidID.getText().toString(),holder.className.getText().toString());

        holder. add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance();
                ref = db.getReference("ChatGroups");


                chatmodel cm = new chatmodel("Student",uid);

                ref.child(sp.getClassName()).child("participents").child(uid).setValue(cm);
                Toast.makeText(context, "student added to the chat group", Toast.LENGTH_SHORT).show();

//                studentPayment stup = new studentPayment(className.getText().toString(),slipIMG.getResources().toString(),uid,subject.getText().toString(),
//                        stream.getText().toString(),id,pay);

                stupaymentList.remove(position);
                notifyDataSetChanged();

                //ref1 = db.getReference("joinGroupClass");
                //ref1.child(String.valueOf(id)).removeValue();
            }
        });
    }




    @Override
    public int getItemCount() {
        return stupaymentList.size();
    }

    public static class stuPaymentViewHolder extends RecyclerView.ViewHolder{
        FirebaseDatabase db;
        DatabaseReference ref,ref1;

        ImageView slipIMG;
        TextView paidID,className,payment,studentUID,subject,stream;
        Button add;
        //public static Button add;
        public stuPaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            slipIMG = itemView.findViewById(R.id.payslip);
            paidID = itemView.findViewById(R.id.payID);
            className = itemView.findViewById(R.id.payclassname);
            payment = itemView.findViewById(R.id.payment);
            //studentUID = itemView.findViewById(R.id.payUid);
            subject = itemView.findViewById(R.id.paysubject);
            stream = itemView.findViewById(R.id.pstream);
            add = itemView.findViewById(R.id.btnaddclassgroup);

//                add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        db = FirebaseDatabase.getInstance();
//                        ref = db.getReference("ChatGroups");
//
//
//                        chatmodel cm = new chatmodel("Student",uid);
//
//                        ref.child(className.getText().toString()).child("participents").child(uid).setValue(cm);
//                        Toast.makeText(context, "student added to the chat group", Toast.LENGTH_SHORT).show();
//
//                        studentPayment stup = new studentPayment(className.getText().toString(),slipIMG.getResources().toString(),uid,subject.getText().toString(),
//                                stream.getText().toString(),id,pay);
//
//                        //ref1 = db.getReference("joinGroupClass");
//                        //ref1.child(String.valueOf(id)).removeValue();
//                    }
//                });




        }
    }
}