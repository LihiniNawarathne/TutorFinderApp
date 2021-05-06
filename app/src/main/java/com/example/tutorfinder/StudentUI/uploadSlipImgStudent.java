package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.Database.NotificationModel;
import com.example.tutorfinder.Database.StudentHelperClass;
import com.example.tutorfinder.Database.joinClass;
import com.example.tutorfinder.MainUI.LoginActivity;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static java.lang.Long.parseLong;

public class uploadSlipImgStudent extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference1,reference2;
    FirebaseUser user;

    //view
    ImageButton paymentSlip,slipimageButton;
    ImageView etvSlipUploadimg;
    Button send;
    long paymentid,paymenyID=0,amount;
    String StudentUID,className,subject,samount,Stream;
    TextView name,payment;

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


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_slip_img_student);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Confirm payment");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference1 = rootNode.getReference("joinGroupClass");
        reference2 = rootNode.getReference("Student");

        //get data from searchClass by intent
        className = getIntent().getStringExtra("className") ;
        samount= getIntent().getStringExtra("amount");
        subject= getIntent().getStringExtra("subject");
        amount= Long.parseLong(samount);//convert to long

        name=findViewById(R.id.tvclasspaid1);
        payment=findViewById(R.id.tvpaidAmount1);
        etvSlipUploadimg=findViewById(R.id.etvSlipUploadimg);
        slipimageButton=findViewById(R.id.slipimageButton);

        //set text view with strings
        name.setText("Class Name :"+className);
        payment.setText("Total Amount(RS.) :"+samount);

        paymentSlip =findViewById(R.id.slipimageButton);
        send= findViewById(R.id.btnsendSlip);

                //get last paymentID
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            paymentid =(snapshot.getChildrenCount());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //get Stream of Student
                Query checkUser = reference2.orderByChild("email").equalTo(user.getEmail());

                checkUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot ds:snapshot.getChildren()){
                            //get student's stream
                            Stream="" + ds.child("alstream").getValue();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //upload slip
                 slipimageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //image pick dialog
                        showImagePickDialog();
                    }
             });

            //send slip details with image of the slip
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.e("Message",""+paymentid);

                    addToPayments(paymentid);
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
        contentValues.put(MediaStore.Images.Media.TITLE,"SlipImageTitle");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"SlipImageDescription");

        img_uri =getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        //camera intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,img_uri);
        startActivityForResult(intent,IMG_PICK_CAMERA);
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);

    }

    private boolean checkStoragePermission(){
        boolean permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){


            if(requestCode==IMG_PICK_GALLERY){
                //picked from gallery
                img_uri=data.getData();
                etvSlipUploadimg.setImageURI(img_uri);
            }
            if(requestCode == IMG_PICK_CAMERA){
                //picked from camera
                etvSlipUploadimg.setImageURI(img_uri);
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
                        Toast.makeText(this, "camera and Storage permission required.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(this, "Storage permission required.", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

        }
    }

    //add details of the payment to the join table
    private void addToPayments(long paymentid) {

        //progress Dialog
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Please wait");
        pd.setMessage("Sending Details");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        //file name and path name in firebase
        String filnamepath = "slipIMGUpload/"+""+System.currentTimeMillis();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filnamepath);

        StudentUID =user.getUid();
        paymenyID = paymentid+1;


        //upload image
        storageReference.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                while (!uriTask.isSuccessful());

                Uri downloadUri = uriTask.getResult();

                if(uriTask.isSuccessful()){
                    //image uri recived

                    joinClass addNewPayment= new joinClass(paymenyID,className,StudentUID,amount,""+downloadUri,subject,Stream);

                    //add to the db
                    reference1.child(String.valueOf(paymenyID)).setValue(addNewPayment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //store details in joinclass table
                            pd.dismiss();
                            Toast.makeText(uploadSlipImgStudent.this, "Payment is Successful", Toast.LENGTH_SHORT).show();
                            setnotifications();
                            startActivity(new Intent(uploadSlipImgStudent.this, DashboardActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            pd.dismiss();
                            //sending message failed
                            Toast.makeText(uploadSlipImgStudent.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(uploadSlipImgStudent.this, DashboardActivity.class));
                        }
                    });

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //failed uploading image
                Toast.makeText(uploadSlipImgStudent.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //add notification
    private void setnotifications(){

        //store data in firebase
        String time = ""+System.currentTimeMillis();
        String message = "Your request to join\n"+className+" group has been received";

        reference2 = FirebaseDatabase.getInstance().getReference("Student");
        NotificationModel mm= new NotificationModel(message,time);

        reference2.child(user.getUid()).child("notifications").child(time).setValue(mm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Added notification

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //sending message failed
                Toast.makeText(uploadSlipImgStudent.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //inflate option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating meny
        getMenuInflater().inflate(R.menu.menu_main_opt,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //handle menu item click logout
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();

        if(id==R.id.logoutoption){

            //progress Dialog
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait");
            pd.setMessage("Login out..");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(uploadSlipImgStudent.this, LoginActivity.class);

            pd.dismiss();

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