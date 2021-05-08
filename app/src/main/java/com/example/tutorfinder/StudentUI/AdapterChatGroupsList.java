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

import com.example.tutorfinder.StudentModels.ChatHelperClass;
import com.example.tutorfinder.R;
import com.example.tutorfinder.StudentModels.GroupMessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterChatGroupsList extends RecyclerView.Adapter<AdapterChatGroupsList.MyHolder>{

    Context context;
    List<ChatHelperClass> chatlist;

    String lastmessage;

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

        //set data
        holder.tvChatGroupName.setText(chatName);

        //get last message

        //connecting to firebase database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatGroups");

        Query lastMessage = reference.child(chatName).child("messages").orderByKey().limitToLast(1);

        lastMessage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    //if you call methods on dataSnapshot it gives you the required values
                    lastmessage =""+ds.child("message").getValue();
                    String timestamp =""+ds.child("timestamp").getValue();
                    System.out.println(lastmessage);

                    //get Time
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(Long.parseLong(timestamp));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy   HH:mm");
                    String time = sdf.format(cal.getTime());

                    holder.tvChatlastMessage.setText(lastmessage);//set chat's last message
                    holder.tvChatlastMsgtime.setText(time);//set chat's last message's time
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});



        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


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

        TextView tvChatGroupName,tvChatlastMessage,tvChatlastMsgtime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvChatGroupName = itemView.findViewById(R.id.tvChatGroupName);
            tvChatlastMessage= itemView.findViewById(R.id.tvChatlastMessage);
            tvChatlastMsgtime= itemView.findViewById(R.id.tvChatlastMsgtime);
        }
    }
}

