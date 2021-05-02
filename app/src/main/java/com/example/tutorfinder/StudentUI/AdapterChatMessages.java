package com.example.tutorfinder.StudentUI;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorfinder.Database.GroupMessageModel;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterChatMessages extends RecyclerView.Adapter<AdapterChatMessages.MyHolder>{

    Context context;
    List<GroupMessageModel> messages;

    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;

    final int MSG_TYPE_LEFT=0;
    final int MSG_TYPE_RIGHT=1;

    //constructor
    public AdapterChatMessages(Context context, ArrayList<GroupMessageModel> messages) {
        this.context = context;
        this.messages = messages;

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public AdapterChatMessages.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate row_chats

        if(viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_groupchat_right, parent, false);
            return new AdapterChatMessages.MyHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.row_groupchat_left, parent, false);
            return new AdapterChatMessages.MyHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatMessages.MyHolder holder, int position) {

        GroupMessageModel model = messages.get(position);

        //get data
        String message=model.getMessage();
        String sender=model.getSender();
        String timestamp=model.getTimestamp();

        //get Time
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp));
//        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa",cal).toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
        String strDate = sdf.format(cal.getTime());


        //set data
        holder.tvmessage.setText(message);
        holder.tvtime.setText(strDate);
        
        //set user name
        setSenderName(model,holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, ""+sender, Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(context, ChatActivityStudent.class);
//                intent.putExtra("chatName", chatName);
//
//                context.startActivity(intent);
            }
        });
    }

    private void setSenderName(GroupMessageModel model, MyHolder holder) {

        //get info using uid
        reference = FirebaseDatabase.getInstance().getReference("Student");

        reference.orderByChild("uid").equalTo(model.getSender()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    String name =""+ds.child("name").getValue();

                    holder.tvname.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getSender().equals(user.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else
            return MSG_TYPE_LEFT;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView tvname,tvmessage,tvtime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.tvname);
            tvmessage= itemView.findViewById(R.id.tvmessage);
            tvtime= itemView.findViewById(R.id.tvtime);
        }
    }
}
