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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateDetailsStudent extends AppCompatActivity {

    //firebase
    FirebaseDatabase rootNode;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    //views
    CircleImageView propic,epropic;
    TextView StudentName,SBirthDay,SPhonepro,Semail,eStudentName,eSPhonepro,epass,erepass;
    Spinner Streampro,eStreampro;
    Button editProfile;
    ImageButton imgPropicupload;

    String name,dob,phone,alstream,email,nic,sname,sphone,sstream,sepass,serepass,proimg;

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

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update_details);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Profile");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //progress Dialog
        ProgressDialog pd = new ProgressDialog(UpdateDetailsStudent.this);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("Student");

        //init
        propic = findViewById(R.id.imgPropic);
        StudentName = findViewById(R.id.tvStudentName);
        SBirthDay= findViewById(R.id.tvSBirthDay);
        SPhonepro = findViewById(R.id.tvSPhonepr);
        Streampro=findViewById(R.id.tvStreampro);
        Semail = findViewById(R.id.tvSemail);
        editProfile =findViewById(R.id.buttoneditPro);
        imgPropicupload=findViewById(R.id.imgPropicupload);

        Query checkUser = reference.orderByChild("email").equalTo(user.getEmail());
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //get data
                    name =""+ds.child("name").getValue();
                    dob =""+ds.child("dob").getValue();
                    phone =""+ds.child("phone").getValue();
                    alstream =""+ds.child("alstream").getValue();
                    email=""+ds.child("email").getValue();
                    proimg=""+ds.child("proimg").getValue();
                    nic=""+ds.child("nic").getValue();

                    //set data
                    StudentName.setText(name);
                    SBirthDay.setText(dob);
                    SPhonepro.setText(phone);
                    //Streampro.setText(alstream);
                    Semail.setText(email);

                    //set spinner
                    List<String> list = new ArrayList<>();

                    list.add(alstream);
                    list.add("Science(Maths)");
                    list.add("Science(Bio)");
                    list.add("Commerce");
                    list.add("Art");
                    list.add("Other");

                    ArrayAdapter<String> dataAdapter =new ArrayAdapter<String>(UpdateDetailsStudent.this, android.R.layout.simple_spinner_dropdown_item,list);
                    Streampro.setAdapter(dataAdapter);


                    try {
                        //if image received set it to the image view
                        Picasso.get().load(proimg).into(propic);
                    }
                    catch (Exception e){
                        //if there is an exception load default image
                        Picasso.get().load(R.drawable.studentpro).into(propic);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //upadte Profile picture
        imgPropicupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImagePickDialog();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //init
                epropic = findViewById(R.id.imgPropic);

                eStudentName = findViewById(R.id.tvStudentName);
                eSPhonepro = findViewById(R.id.tvSPhonepr);
                eStreampro=findViewById(R.id.tvStreampro);
                epass = findViewById(R.id.tvpasswordedit);
                sepass=epass.getText().toString().trim();
                erepass = findViewById(R.id.tvpasswordeditRe);
                serepass=erepass.getText().toString().trim();

                //convert to string
                sname=eStudentName.getText().toString().trim();
                sphone=eSPhonepro.getText().toString().trim();
                sstream= Streampro.getSelectedItem().toString();
                sepass=epass.getText().toString().trim();
                serepass=erepass.getText().toString().trim();

                if(!sname.isEmpty() && !sphone.isEmpty()){

                    //validate data
                    if(!sname.matches("^[a-zA-Z]+(([,. ][a-zA-Z ])?[a-zA-Z]*)*$")){
                        StudentName.setError("Invalid Name");
                        StudentName.setFocusable(true);
                    }
                    else  if(!sphone.matches("^0[7-9][0-9]{8}$")) {
                        SPhonepro.setError("Invalid Name");
                        SPhonepro.setFocusable(true);
                    }
                    else {

                        pd.setTitle("Please wait");
                        pd.setMessage("Logging in....");
                        pd.setCanceledOnTouchOutside(false);
                        pd.show();

                        nameChanged();
                        phoneChanged();
                        streamChanged();

                        if (!sepass.isEmpty() || !serepass.isEmpty()) {
                            passwordChanged();

                        } else {

                            pd.dismiss();
                            Toast.makeText(UpdateDetailsStudent.this, "Profile details are updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateDetailsStudent.this, DashboardActivity.class));
                        }
                    }
               }
                else {
                    Toast.makeText(UpdateDetailsStudent.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();

                }

            }


        //update data
            private void nameChanged()  {
                if(!name.equals(sname)){
                    reference.child(user.getUid()).child("name").setValue(sname);
                    name = sname;

                }
            }

            private void phoneChanged() {
                if(!phone.equals(sphone)){
                      reference.child(user.getUid()).child("phone").setValue(sphone);
                      phone = sphone;
                }
            }

            private void streamChanged() {
                if(!alstream.equals(sstream)){
                    reference.child(user.getUid()).child("alstream").setValue(sstream);
                    alstream = sstream;
                }
            }

            private void passwordChanged() {

                    if(!serepass.isEmpty()  && !sepass.isEmpty()) {//check whether the provided passwords fields are empty or not

                        if(sepass.length()<6  && erepass.length()<6){//check password length
                            Toast.makeText(UpdateDetailsStudent.this, "Password length should be at least 6 characters", Toast.LENGTH_SHORT).show();
                        }

                        else{

                                if (sepass.equals(serepass)) {//check whetehe the provided password are same or not

                                    user.updatePassword(sepass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pd.dismiss();

                                            //password updated
                                            Toast.makeText(UpdateDetailsStudent.this, "Passwords successfully changed", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(UpdateDetailsStudent.this, "Details are updated", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(UpdateDetailsStudent.this, DashboardActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UpdateDetailsStudent.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                } else {
                                    Toast.makeText(UpdateDetailsStudent.this, "Passwords should be same", Toast.LENGTH_SHORT).show();
                                }
                        }
                    }
                    else {
                        Toast.makeText(UpdateDetailsStudent.this, "Please fill both password fields", Toast.LENGTH_SHORT).show();
                    }
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
                propic.setImageURI(img_uri);
                setPropic();
            }
            if(requestCode == IMG_PICK_CAMERA){
                //picked from camera
                propic.setImageURI(img_uri);
                setPropic();
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

    private void setPropic() {

        //progress Dialog
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Please wait");
        pd.setMessage("Setting profile image");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        //file name and path name in firebase
        String filnamepath = "proImageUpload/"+""+System.currentTimeMillis();

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

                    reference.child(user.getUid()).child("proimg").setValue(""+downloadUri);
                    pd.dismiss();
                    Toast.makeText(UpdateDetailsStudent.this, "Image uploading is successful.", Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //failed uploading image
                Toast.makeText(UpdateDetailsStudent.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UpdateDetailsStudent.this, LoginActivity.class);

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