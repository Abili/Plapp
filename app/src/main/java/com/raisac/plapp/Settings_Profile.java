package com.raisac.plapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.raisac.plapp.Utils.FilePaths;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static droidninja.filepicker.FilePickerConst.REQUEST_CODE;

public class Settings_Profile extends AppCompatActivity implements ChangePhotoDialog.OnPhotoReceivedListener {

    public  interface   OnPhotoRecievedListener{
        public void getImagePath(Uri imagePath);
        public void getImageBitmap(Bitmap bitmap);
    }
    OnPhotoRecievedListener mOnPhotoRecieved;

    TextView edtName, edtAbout;
    ImageView imgProfile, camera;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseUsers;
    FirebaseAuth mAuth;
    int ImageRequest_code = 7;
    Button create_account;
    EditText settings_username;
    CardView optionsBtns;
    FirebaseDatabase mUsers;
    Boolean mStoragePermission = false;
    private byte[] mBytes;
    private double progress;
    private ProgressBar mProgressBar;
    Dialog profileDialog;


    private static final String TAG = "Settings_Profile";

    private Uri mSelectedImageUri;
    private Bitmap mSelectedImageBitmap;

    public static final double MB = 1000000.0;
    public  static  final double MB_THRESHOLD = 5.0;

    public static  final int CAMERA_REQUEST_CODE =5467;
    public static  final int PICKFILE_REQUEST_CODE =8352;
    public Settings_Profile() {
    }


    @Override
    public void getImagePath(Uri imagePath) {
        if (!imagePath.toString().equals("")) {
            mSelectedImageBitmap = null;
            mSelectedImageUri = imagePath;
            Log.d(TAG, "getImagePath: get image url: " + mSelectedImageUri);
            ImageLoader.getInstance().displayImage(imagePath.toString(), imgProfile);

        }
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mSelectedImageBitmap = bitmap;
            mSelectedImageUri = null;
            Log.d(TAG, "getImageBitmap: get image bitmap: " + mSelectedImageBitmap);
            imgProfile.setImageBitmap(bitmap);


        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        settings_username = findViewById(R.id.settings_edtUsername);
        create_account = findViewById(R.id.createaccount_btn);

        final String username = settings_username.getText().toString();
        imgProfile = findViewById(R.id.registerProfile);

        verifyStoragePermission();
        init();
        hideSoftKeys();




        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStoragePermission) {
                    ChangePhotoDialog dialog = new ChangePhotoDialog();
                    dialog.show(getSupportFragmentManager(), "ChangePhotoDialog");
                } else {
                    verifyStoragePermission();
                }
           }
       });




    }

    private void init() {
        getUserAccountData();


        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settings_username.getText().toString().equals("")) {
                    settings_username.setError("Username is Required");
                } else {
                    DatabaseReference username = mUsers.getInstance().getReference();
                    username.child(getString(R.string.users_node))
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(getString(R.string.field_name))
                            .setValue(settings_username.getText().toString().trim());
                    sendImageTofirebase();

                }
            }
        });
    }
    private void sendImageTofirebase() {
        if (mSelectedImageUri != null) {
            uploadNewPhoto(mSelectedImageUri);
        } else if (mSelectedImageBitmap != null) {
            uploadNewPhoto(mSelectedImageBitmap);
        }
    }

    private void uploadNewPhoto(Uri imageUri) {
        //TODO upload a new photo to firebase storage
        Log.d(TAG, "uploadNewPhoto: uploading new profile photo to firbase.");
        BackgroundImageResize resize = new BackgroundImageResize(null);
        resize.execute(imageUri);

    }

    private void uploadNewPhoto(Bitmap imageBitmap) {
        //TODO upload a new photo to firebase storage
        Log.d(TAG, "uploadNewPhoto: uploading new profile photo to firbase.");
        BackgroundImageResize resize = new BackgroundImageResize(imageBitmap);
        Uri uri = null;
        resize.execute(uri);

    }


    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {
        Bitmap nBitmap;

        public BackgroundImageResize(Bitmap bm) {
            if (bm != null) {
                nBitmap = bm;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
            Toast.makeText(Settings_Profile.this, "compressing image", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected byte[] doInBackground(Uri... params) {
            Log.d(TAG, "doInBackground: started.");
            if (nBitmap == null) {
                try {
                    nBitmap = MediaStore.Images.Media.getBitmap(Settings_Profile.this.getContentResolver(), params[0]);
                    Log.d(TAG, "doInBackground: bitmap size megabytes. " + nBitmap.getByteCount() / MB + "MB");
                }catch (IOException e){
                    Log.d(TAG, "doInBackground: IOException: ", e.getCause());

                }
            }
            byte[] bytes = null;
            for(int i=1; i<11; i++){
                if(i == 10){
                    Toast.makeText(Settings_Profile.this,"That image is too large.", Toast.LENGTH_SHORT).show();
                    break;
                }
                bytes = getBytesFromBitmap(nBitmap, 100/i);
                Log.d(TAG, "doInBackground: megabytes: (" + (11-i) + "0%) " + bytes.length/MB + "MB");
                if(bytes.length/MB < MB_THRESHOLD){
                    return  bytes;
                }

            }

            return bytes;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            hideDialog();
            mBytes = bytes;
            executeUploadTask();
        }
    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


    private byte[] getBytesFromBitmap(Bitmap nBitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        nBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }

    private void executeUploadTask() {
        showDialog();
        FilePaths filePaths =new FilePaths();
        //specify where the photo will be stored
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(filePaths.FIREBASE_IMAGE_STORAGE  + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                            + "/profile_image"); //just replacethe oldimage with the new image

        if(mBytes.length/MB < MB_THRESHOLD) {

            // Create file metadata including the content type
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpg")
                    .setContentLanguage("en") //see nodes below
                    /*
                    Make sure to use proper language code ("English" will cause a crash)
                    I actually submitted this as a bug to the Firebase github page so it might be
                    fixed by the time you watch this video. You can check it out at https://github.com/firebase/quickstart-unity/issues/116
                     */
                    .setCustomMetadata("Mitch's special meta data", "JK nothing special here")
                    .setCustomMetadata("location", "Iceland")
                    .build();
            //if the image size is valid then we can submit to database
            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(mBytes, metadata);
            //uploadTask = storageReference.putBytes(mBytes); //without metadata


            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Now insert the download url into the firebase database
                    Uri firebaseURL = taskSnapshot.getDownloadUrl();
                    Toast.makeText(Settings_Profile.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess: firebase download url : " + firebaseURL.toString());
                    FirebaseDatabase.getInstance().getReference()
                            .child(getString(R.string.users_node))
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(getString(R.string.field_profile_image))
                            .setValue(firebaseURL.toString());

                    hideDialog();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(Settings_Profile.this, "could not upload photo", Toast.LENGTH_SHORT).show();

                    hideDialog();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double currentProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    if(currentProgress > (progress + 15)){
                        progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.d(TAG, "onProgress: Upload is " + progress + "% done");
                        Toast.makeText(Settings_Profile.this, progress + "%", Toast.LENGTH_SHORT).show();
                    }

                }
            })
            ;
        }else{
            Toast.makeText(this, "Image is too Large", Toast.LENGTH_SHORT).show();
        }

    }


    private void getUserAccountData() {


        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.users_node))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
                            + singleSnapshot.getValue(User.class).toString());
                    User user = singleSnapshot.getValue(User.class);
                    settings_username.setText(user.username);
                    ImageLoader.getInstance().displayImage(user.getPhotoUrl(), imgProfile);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void verifyStoragePermission() {
        Log.d(getString(R.string.settings_profile_tag), "verifyPermission: asking user for permission.");
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permission[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permission[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permission[2]) == PackageManager.PERMISSION_GRANTED) {
            mStoragePermission = true;
        } else {
            ActivityCompat.requestPermissions(
                    Settings_Profile.this,
                    permission,
                    REQUEST_CODE
            );
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //TODO rsults after selecting image from phone memory
        if(requestCode==PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();
            Log.d(TAG, "onActivityResult: image: " +selectedImageUri);

            //TODO send the bitmap and frrgment to the interface
            mOnPhotoRecieved.getImagePath(selectedImageUri);
            profileDialog.dismiss();
        }

        else if(requestCode ==CAMERA_REQUEST_CODE && resultCode ==Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: done taking a photo.");

            Bitmap bitmap;
            bitmap=(Bitmap)data.getExtras().get("data");

            mOnPhotoRecieved.getImageBitmap(bitmap);
           profileDialog.dismiss();

        }
    }

}

