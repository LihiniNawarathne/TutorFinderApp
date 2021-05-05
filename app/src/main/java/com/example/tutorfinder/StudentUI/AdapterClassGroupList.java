package com.example.tutorfinder.StudentUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorfinder.Database.ClassHelperClass;
import com.example.tutorfinder.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterClassGroupList extends RecyclerView.Adapter<AdapterClassGroupList.MyHolder>{

    Context context;
    List<ClassHelperClass> classlist;

    //constructor
    public AdapterClassGroupList( Context context,ArrayList<ClassHelperClass> classlist) {
        this.context = context;
        this.classlist = classlist;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate row_classsearch_results
        View view = LayoutInflater.from(context).inflate(R.layout.row_classsearch_results,parent,false);


        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //get data
        String className=classlist.get(position).getClassName();
        String subject=classlist.get(position).getSubject();
        String Time=classlist.get(position).getTime();
        String Grade=classlist.get(position).getGrade();
        String tutor=classlist.get(position).getTutor();
        String amount= String.valueOf(classlist.get(position).getAmount());

        //set data
        holder.stvClassNameS.setText("Class Name - "+className);
        holder.stvClassGradeS.setText("Grade - "+Grade);
        holder.stvClassTimeS.setText("Time - "+Time);
        holder.stvClassTutorS.setText("Tutor - "+tutor);
        holder.stvClassAmountS.setText("Amount(for 6 months)RS :- "+amount);
        holder.stvsearhchedSubject.setText(subject);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
            holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, ""+className, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, uploadSlipImgStudent.class);
                intent.putExtra("className", className);
                intent.putExtra("amount", amount);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classlist.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView stvClassAmountS,stvClassTutorS,stvClassTimeS,stvClassGradeS,stvClassNameS,stvsearhchedSubject;
        Button join;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            stvClassAmountS = itemView.findViewById(R.id.tvClassAmountS);
            stvClassTutorS= itemView.findViewById(R.id.tvClassTutorS);
            stvClassTimeS= itemView.findViewById(R.id.tvClassTimeS);
            stvClassGradeS= itemView.findViewById(R.id.tvClassGradeS);
            stvClassNameS= itemView.findViewById(R.id.tvClassNameS);
            stvsearhchedSubject= itemView.findViewById(R.id.tvsearhchedSubject);
            join= itemView.findViewById(R.id.btnjoinGroup);
        }
    }
}
