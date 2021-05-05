package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.Database.ClassHelperClass;
import com.example.tutorfinder.Database.GroupMessageModel;
import com.example.tutorfinder.Database.NotificationModel;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivityStudent extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference reference,reference2;
    FirebaseUser user;

    String groupName;
    TextView tvGroupNamest;
    EditText etvSentMSG;
    ImageButton imgSendMsg;
    RecyclerView chats;

    ArrayList<GroupMessageModel> chatList;
    AdapterChatMessages adapterchatlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_student);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search Results");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("ChatGroups");


        //Retrieve the value of group name
        groupName = getIntent().getExtras().getString("chatName");

        tvGroupNamest = findViewById(R.id.tvGroupNamest);
        etvSentMSG = findViewById(R.id.etvSentMSG);
        imgSendMsg = findViewById(R.id.imgSendMsg);
        chats =findViewById(R.id.recycleViewmessages);

        //set groupName
        tvGroupNamest.setText(groupName);

        //load previous messages
        loadMessages();


        //when click send_image button
        imgSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String message = etvSentMSG.getText().toString();
                
                //validate message
                if(message.isEmpty()){
                    //show toast message
                    Toast.makeText(ChatActivityStudent.this, "Can't send an empty message", Toast.LENGTH_SHORT).show();
                    
                }
                else{
                    //send message
                    sendMSG(message);
                    //show toast message
                    Toast.makeText(ChatActivityStudent.this, "Sending reply..", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //load previous messages
    private void loadMessages() {
        //init list
        chatList = new ArrayList<>();

        reference.child(groupName).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){

                    GroupMessageModel model = ds.getValue(GroupMessageModel.class);
                    chatList.add(model);

                }
                adapterchatlist = new AdapterChatMessages(ChatActivityStudent.this,chatList);

                chats.setAdapter(adapterchatlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //send message to the chat
    private void sendMSG(String message) {

        //timestamp
        String timestamp =""+System.currentTimeMillis();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",""+user.getUid());
        hashMap.put("message",""+message);
        hashMap.put("timestamp",""+timestamp);
        hashMap.put("type",""+"text");

        //add to the db
        reference.child(groupName).child("messages").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //message sent
                etvSentMSG.setText("");
                getParticipants();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //sending message failed
                Toast.makeText(ChatActivityStudent.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //get Paricipants
    private void getParticipants(){

        Query q1= reference.child(groupName).child("participents");

        q1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //get data
                    String uid =""+ds.child("uid").getValue().toString();
                    Log.d("Meeeee2"," NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    System.out.println("user--- :"+uid);
                    setnotifications(uid);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //add notification
    private void setnotifications(String uid) {

        //store data in firebase
        String time = ""+System.currentTimeMillis();
        String message = "New Message from\n "+groupName+" group";

        reference2 = FirebaseDatabase.getInstance().getReference("Student");
        NotificationModel mm= new NotificationModel(message,time);

        //check if the participant is sender not
        if(!uid.equals(user.getUid())) {

            reference2.child(uid).child("notifications").child(time).setValue(mm).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //Added notification

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //sending message failed
                    Toast.makeText(ChatActivityStudent.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}