package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.Database.GroupMessageModel;
import com.example.tutorfinder.Database.NotificationModel;
import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentChatActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference reference,reference2;
    FirebaseUser user;

    String groupName;
    TextView tvGroupNamest;
    EditText etvSentMSG;
    ImageButton imgSendMsg,imgSendIMG;
    RecyclerView chats;

    ArrayList<GroupMessageModel> chatList;
    AdapterChatMessages adapterchatlist;

    //permission request
    private static final int CAMERA_REQUEST_CODE=1001;
    private static final int STORAGE_REQUEST_CODE=1002;

    //img pick constant
    private static final int IMG_PICK_CAMERA=1003;
    private static final int IMG_PICK_GALLERY=1004;

    //permission to request
    String[] cameraPermission;
    String[] storagePermission;

    Uri img_uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search Results");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("ChatGroups");

        //required permission
        cameraPermission =new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};


        //Retrieve the value of group name
        groupName = getIntent().getExtras().getString("chatName");

        tvGroupNamest = findViewById(R.id.tvGroupNamest);
        etvSentMSG = findViewById(R.id.etvSentMSG);
        imgSendMsg = findViewById(R.id.imgSendMsg);
        imgSendIMG=findViewById(R.id.imgSendIMG);
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
                    Toast.makeText(StudentChatActivity.this, "Can't send an empty message", Toast.LENGTH_SHORT).show();
                    
                }
                else{
                    //send message
                    sendMSG(message);
                    //show toast message
                    Toast.makeText(StudentChatActivity.this, "Sending reply..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //import an image
        imgSendIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //image pick dialog
                showImagePickDialog();

            }
        });


    }

    private void showImagePickDialog() {
        //options
        String[] options = {"Camera","Gallery"};

        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    //camera clicked
                    if(!checkcameraPermission()){
                        requestCameraPermission();
                    }
                    else
                        pickCamera();
                }
                else{
                    //gallery clicked
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else
                        pickGallery();
                }
            }
        })
        .show();
    }

    private void pickGallery(){
        //pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMG_PICK_GALLERY);

    }

    private void pickCamera(){

        ContentValues contentValues=new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"GroupImageTitle");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"GroupImageDescription");

        img_uri =getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,img_uri);
        startActivityForResult(intent,IMG_PICK_CAMERA);
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);

    }

    private boolean checkStoragePermission(){
        boolean permission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return permission;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkcameraPermission(){
        boolean permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return permission && permission2;
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
                adapterchatlist = new AdapterChatMessages(StudentChatActivity.this,chatList);

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
                Toast.makeText(StudentChatActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendImageMSG() {
        //progress Dialog
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Please wait");
        pd.setMessage("Sending Image");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        //file name and path name in firebase
        String filnamepath = "imgmessageUpload/"+""+System.currentTimeMillis();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filnamepath);

        //upload image
        storageReference.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                while (!uriTask.isSuccessful());

                Uri downloadUri = uriTask.getResult();

                if(uriTask.isSuccessful()){
                    //image uri recived

                    //timestamp
                    String timestamp =""+System.currentTimeMillis();

                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("sender",""+user.getUid());
                    hashMap.put("message",""+downloadUri);
                    hashMap.put("timestamp",""+timestamp);
                    hashMap.put("type",""+"image");

                    //add to the db
                    reference.child(groupName).child("messages").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //message sent
                            etvSentMSG.setText("");
                            getParticipants();
                            pd.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            pd.dismiss();
                            //sending message failed
                            Toast.makeText(StudentChatActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //failed uploading image
                Toast.makeText(StudentChatActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(StudentChatActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==IMG_PICK_GALLERY){
                //picked from gallery
                img_uri =data.getData();
                sendImageMSG();
            }
            if(requestCode == IMG_PICK_CAMERA){
                //picked from camera
                sendImageMSG();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted&& writeStorageAccepted){
                        pickCamera();
                    }
                    else{
                        Toast.makeText(this, "camera and Storage permission requires.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean writeStorageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;

                    if(writeStorageAccepted){
                        pickGallery();
                    }
                    else{
                        Toast.makeText(this, "Storage permission requires.", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

        }
    }

    //inflate option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating meny
        getMenuInflater().inflate(R.menu.menu_main_opt,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //handle menu item click logout option
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();

        if(id==R.id.logoutoption){

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(StudentChatActivity.this, LoginActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}