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


import com.example.tutorfinder.Admin_models.TutorRequestHandler;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;
import java.util.ArrayList;

public class TutorRequestAdapter<context> extends RecyclerView.Adapter<TutorRequestAdapter.tutorRequestViewHolder>{

    ArrayList<TutorRequestHandler> rlist;
    static Context context;
    static String password1;
    private static FirebaseDatabase database;
    private static DatabaseReference reference,reference1;
    private static FirebaseAuth mAuth;
    private static FirebaseUser fuser;
    private BreakIterator NICT;

    public TutorRequestAdapter(Context context, ArrayList<TutorRequestHandler> rlist) {
        this.rlist = rlist;
        this.context = context;
    }

    @NonNull
    @Override
    public tutorRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_admin_requestform, parent, false);
        return new tutorRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull tutorRequestViewHolder holder, int position) {
        TutorRequestHandler TutorRequestHandler = rlist.get(position);
        holder.NIC.setText(TutorRequestHandler.getNic());
        holder.address.setText(TutorRequestHandler.getAddress());
        holder.email.setText(TutorRequestHandler.getEmail());
        holder.name.setText(TutorRequestHandler.getName());
        //holder.password.setText(TutorRequestHandler.getPassword());
        holder.phoneNo.setText(TutorRequestHandler.getphoneno());
        holder.subject.setText(TutorRequestHandler.getSubject());
        holder.qualifications.setText(TutorRequestHandler.getQualifications());

        password1 = TutorRequestHandler.getPassword();

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(TutorRequestHandler.getEmail(), password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //if (mAuth.getCurrentUser().isEmailVerified()) {
                                        database = FirebaseDatabase.getInstance();
                                        reference = database.getReference("Tutors");
                                        TutorRequestHandler tutors = new TutorRequestHandler(TutorRequestHandler.getNic(),TutorRequestHandler.getAddress(), TutorRequestHandler.getEmail(), TutorRequestHandler.getName(),
                                                password1, TutorRequestHandler.getphoneno(), TutorRequestHandler.getSubject(),TutorRequestHandler.getQualifications());
                                        //password.getText().toString(),

                                        reference.child(tutors.getNic()).setValue(tutors);
                                        Toast.makeText(context, "Send verification email", Toast.LENGTH_SHORT).show();

                                        rlist.remove(position);
                                        notifyDataSetChanged();
//                                                reference1 = database.getReference("TutorRequest").child(tutors.getNic());
//                                                //reference1.child(NIC.getText().toString()).removeValue();
//                                                Task<Void> mTsk = reference1.removeValue();
                                    } else {
                                        //}
                                    }
                                }

                            });
                        }
                    }
                });
            }
        });

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("TutorRequest");

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Delete ?");
                dialog.setMessage("Are you sure you want to delete "+TutorRequestHandler.getName()+" tutor's request from Tutor finder organization");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rlist.remove(position);
                        notifyDataSetChanged();
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


    @Override
    public int getItemCount() {

        return rlist.size();
    }

    public static class tutorRequestViewHolder extends RecyclerView.ViewHolder {

        TextView NIC, address, email, name, phoneNo, subject, qualifications, password;
        Button btnAccept, btnDecline;

        public tutorRequestViewHolder(@NonNull View itemView) {

            super(itemView);
            NIC = itemView.findViewById(R.id.rvNIC);
            address = itemView.findViewById(R.id.rvaddress);
            email = itemView.findViewById(R.id.rvEmail);
            name = itemView.findViewById(R.id.rvname);
            //password = itemView.findViewById(R.id.rvpwd);
            phoneNo = itemView.findViewById(R.id.rvphoneno);
            subject = itemView.findViewById(R.id.rvsubject);
            qualifications = itemView.findViewById(R.id.rvqalific);
            //button accept
            btnAccept = itemView.findViewById(R.id.btnaccept);
            btnDecline = itemView.findViewById(R.id.btndecline);


//            btnAccept.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            //if (mAuth.getCurrentUser().isEmailVerified()) {
//                                            database = FirebaseDatabase.getInstance();
//                                            reference = database.getReference("Tutors");
//                                            TutorRequestHandler tutors = new TutorRequestHandler(NIC.getText().toString(), address.getText().toString(), email.getText().toString(), name.getText().toString(),
//                                                    password1, phoneNo.getText().toString(), subject.getText().toString(), qualifications.getText().toString());
//                                            //password.getText().toString(),
//
//                                            reference.child(tutors.getNic()).setValue(tutors);
//                                            Toast.makeText(context, "Send verification email", Toast.LENGTH_SHORT).show();
//
//
////                                                reference1 = database.getReference("TutorRequest").child(tutors.getNic());
////                                                //reference1.child(NIC.getText().toString()).removeValue();
////                                                Task<Void> mTsk = reference1.removeValue();
//                                        } else {
//                                            //}
//                                        }
//                                    }
//
//                                });
//                            }
//                        }
//                    });
//                }
//            });



//                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                if (mAuth.getCurrentUser().isEmailVerified()) {
//
//                                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if (task.isSuccessful()) {
//
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    Toast.makeText(context, "Didnot verify the mail", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//
//                    });
//                }
//
//                        });

            //button delete

//            btnDecline.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    database = FirebaseDatabase.getInstance();
//                    //mAuth = FirebaseAuth.getInstance();
//                    fuser = FirebaseAuth.getInstance().getCurrentUser();
//
//
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//                    dialog.setTitle("Delete ?");
//                    dialog.setMessage("Are you sure you want to delete "+name.getText().toString()+" tutor's request from Tutor finder organization");
//                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            fuser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(context, "Account deleted", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//                            });
//                            TutorRequestHandler dtr = new TutorRequestHandler(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
//                                    password1,phoneNo.getText().toString(),subject.getText().toString(),qualifications.getText().toString());
//
//                            reference.child(dtr.getNic()).removeValue();
//                            Toast.makeText(context, "rejected tutor request", Toast.LENGTH_SHORT).show();
//                            delete();
//
//                            reference = database.getReference("TutorRequest").child(dtr.getNic());
//                            Task<Void> task = reference.removeValue();
//                        }
//                    });
//                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int which) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    AlertDialog alertDialog =dialog.create();
//                    alertDialog.show();
//                }
//            });


//            btnDecline.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    database = FirebaseDatabase.getInstance();
//                    reference1 = database.getReference("TutorRequest");
//                    TutorRequestHandler dtr = new TutorRequestHandler(NIC.getText().toString(),address.getText().toString(),email.getText().toString(),name.getText().toString(),
//                    password1,phoneNo.getText().toString(),subject.getText().toString(),qualifications.getText().toString());
//                    reference1.child(dtr.getNic()).removeValue();
//                    //Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
//                }
//            });


        }
//        public void delete(){
//            database = FirebaseDatabase.getInstance();
//            reference1 = database.getReference("TutorRequest");
//
//            //TutorRequestHandler th = new TutorRequestHandler(NIC.getText.toString(),)
//
//            reference1.child(NIC.getText().toString()).removeValue();
//
//        }
    }


}