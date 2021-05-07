package com.example.tutorfinder.StudentUI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorfinder.StudentModels.GroupMessageModel;
import com.example.tutorfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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

    String name;

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
        //get data
        GroupMessageModel model = messages.get(position);
        String message=model.getMessage();
        String sender=model.getSender();
        String timestamp=model.getTimestamp();
        String messageType=model.getType();

        //get Time
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp));
        SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy   HH:mm");
        String time = sdf.format(cal.getTime());

        //set data
        if(messageType.equals("text")){
          //  holder.tvmessage.setText(message);
            //text message,hide ImageView and show messageText
            holder.tvmessage.setVisibility(View.VISIBLE);
            holder.imgMSG.setVisibility(View.GONE);
            holder.tvmessage.setText(message);
        }
        else {
            //Image message,hide Text and show Image
            holder.tvmessage.setVisibility(View.GONE);
            holder.imgMSG.setVisibility(View.VISIBLE);
            try {
                Picasso.get().load(message).placeholder(R.drawable.ic_imag_msg).into(holder.imgMSG);
            }
            catch (Exception e){
                holder.imgMSG.setImageResource(R.drawable.ic_imag_msg);
            }
          //  holder.tvmessage.setText("Sent Photoessage");
        }

        holder.tvtime.setText(time);
        
        //set user name
        setSenderName(model,holder);

        holder.imgMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, StudentFullImage.class);
                intent.putExtra("image", message);

                context.startActivity(intent);

            }
        });

    }

    //get sender name and set it
    private void setSenderName(GroupMessageModel model, MyHolder holder) {

        //get info using uid
        reference = FirebaseDatabase.getInstance().getReference("Student");

        reference.orderByChild("uid").equalTo(model.getSender()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    name =""+ds.child("name").getValue();

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

        TextView tvname ,tvmessage,tvtime;
        ImageView imgMSG;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.tvname);
            tvmessage= itemView.findViewById(R.id.tvmessage);
            tvtime= itemView.findViewById(R.id.tvtime);
            imgMSG= itemView.findViewById(R.id.imgMSG);
        }
    }
}
