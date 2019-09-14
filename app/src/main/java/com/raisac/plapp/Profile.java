package com.raisac.plapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    ImageView userpic;
    Context context;
    Button createAccount;
    EditText edtUsername;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseStorage mStorage;
    DatabaseReference mDatabaseReference, mDatabaseUsers;
    StorageReference mStorageReference;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    ProgressDialog progressDialog;
    Uri FilepathUri;
    int ImageRequest_code = 7;
    String mName;
    String mPicUrl;

    Dialog imageUploadDialog;
    public static final int CAMERA_REQUEST_CODE = 5467;
    public static final int PICKFILE_REQUEST_CODE = 8352;
    private static final String TAG = "Settings_Profile";

    private Uri mSelectedImageUri;
    ProgressDialog progressBar;
    private Bitmap mSelectedImageBitmap;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static final double MB = 1000000.0;
    public static final double MB_THRESHOLD = 5.0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profile);
//           Toolbar profileToolbar = findViewById(R.id.profiletoolbar);
        //setActionBar(profileToolbar);

        createAccount = findViewById(R.id.createaccount_btn);
        edtUsername = findViewById(R.id.settings_edtUsername);
        userpic = findViewById(R.id.registerProfile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //progressBar = findViewById(R.id.progressBarCreateAcc);
        progressBar = new ProgressDialog(Profile.this);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Plapp");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        // String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseUsers.setValue(true);


        userpic.setOnClickListener(this);
        createAccount.setOnClickListener(this);

        getUserInfo();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createaccount_btn:

                UploadImage();

                break;

            case R.id.registerProfile:
                chooseImage();

                break;
        }
    }

    private void UploadImage() {

        if (FilepathUri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            String uid = mAuth.getUid();
            final String username1 = edtUsername.getText().toString();
            // User user = new User(username1, null,null);
            //mDatabaseUsers.child(mCurrentUser.getUid()).push().setValue(user);
            if (!TextUtils.isEmpty(username1)) {
                final String userId = mAuth.getCurrentUser().getUid();
                StorageReference ref = mStorageReference.child("profile_images").child(FilepathUri.getLastPathSegment());
                ref.putFile(FilepathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                        String uid = mAuth.getUid();
                        User user1 = new User(username1, userId, downloadUrl.toString());
                        mDatabaseUsers.child(uid).push().setValue(user1);

                        Intent intent = new Intent(Profile.this, HomeSlider.class);
//                        intent.putExtra("username", username1);
//                        intent.putExtra("profilepic", downloadUrl.toString());
                        startActivity(intent);
                        progressDialog.setMessage("sign Up completed");
                        return;
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Initialising " + (int) progress + "%...");
                    }
                });
            }else {
                edtUsername.setError("please Enter Username");
            //users.child("profile").setValue(userUploadInfo);

        }
        }
    }

    private void getUserInfo() {
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    String username = (String) dataSnapshot.child("username").getValue();
                    edtUsername.setText(username);
                }

                if (dataSnapshot.exists()) {
                    Uri mPicUrl = (Uri) dataSnapshot.child("profileImageUrl").getValue();
                    Glide.with(getApplication()).load(mPicUrl).into(userpic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void chooseImage() {
        imageUploadDialog = new Dialog(Profile.this);
        imageUploadDialog.setCancelable(false);
        imageUploadDialog.setContentView(R.layout.activity_change_photo_dialog);
        imageUploadDialog.show();

        //TODO initialise the textview for choosing an image from memory
        TextView selectPhoto = imageUploadDialog.findViewById(R.id.dialogChoosePhoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing phone memory,");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        //TODO initialise the textview for choosing an image from camera
        TextView takePhoto = imageUploadDialog.findViewById(R.id.dialogOpenCamera);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starting camera,");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //TODO rsults after selecting image from phone memory
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Log.d(TAG, "onActivityResult: image: " + selectedImageUri);

            //TODO send the bitmap and frrgment to the interface
            userpic.setImageURI(FilepathUri);
            imageUploadDialog.dismiss();
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: done taking a photo.");

            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");

            userpic.setImageBitmap(bitmap);
            imageUploadDialog.dismiss();

        }
    }

}

