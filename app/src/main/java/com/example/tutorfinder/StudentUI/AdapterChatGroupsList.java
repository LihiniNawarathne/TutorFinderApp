package com.example.tutorfinder.StudentUI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorfinder.Database.ChatHelperClass;
import com.example.tutorfinder.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterChatGroupsList extends RecyclerView.Adapter<AdapterChatGroupsList.MyHolder>{

    Context context;
    List<ChatHelperClass> chatlist;

    //constructor
    public AdapterChatGroupsList(Context context, ArrayList<ChatHelperClass> chatlist) {
        this.context = context;
        this.chatlist = chatlist;

    }

    @NonNull
    @Override
    public AdapterChatGroupsList.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate row_chatgroups
        View view = LayoutInflater.from(context).inflate(R.layout.row_chatgroups,parent,false);


        return new AdapterChatGroupsList.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //get data
        String chatName=chatlist.get(position).getGroupTitle();
        //String participants=chatlist.get(position).getParticipents();

        //set data
        holder.tvChatGroupName.setText(chatName);
        holder.tvChatlastMessage.setText("Hy");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, ""+chatName, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, StudentChatActivity.class);
                    intent.putExtra("chatName", chatName);

                    context.startActivity(intent);
                }
            });


    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView tvChatGroupName,tvChatlastMessage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvChatGroupName = itemView.findViewById(R.id.tvChatGroupName);
            tvChatlastMessage= itemView.findViewById(R.id.tvChatlastMessage);
        }
    }
}

