package com.example.fithub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static com.example.fithub.UploadActivity.REQUEST_IMAGE_CAPTURE;

public class DataRegisteryContinued extends AppCompatActivity {

    private ImageView profilepic;
    byte[] byteArray;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    private DatabaseReference databaseinfo;
    private FirebaseAuth mauth;
    private FirebaseUser currentuser;
    String downloadurl;
    Button continuebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataregisterycontinued);
        mauth = FirebaseAuth.getInstance();
        currentuser = mauth.getCurrentUser();
        mStorageRef= FirebaseStorage.getInstance().getReference(currentuser.getUid());
        profilepic = findViewById(R.id.imageView4);
        profilepic.setImageResource(R.drawable.exampleprofile);
        continuebutton = findViewById(R.id.Continue2);




        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //img.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();

            profilepic.setImageBitmap(imageBitmap);

            StorageReference Ref = mStorageRef.child((currentuser.getUid()));
            uploadTask = Ref.putBytes(byteArray)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    System.out.println(uri.toString());

                                    downloadurl = uri.toString();

                                    databaseinfo = FirebaseDatabase.getInstance().getReference("Personal Info").child(currentuser.getUid()).child("profilepicurl");
                                    databaseinfo.setValue(downloadurl);

                                }
                            });





                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });




        }
    }


    public void onclickcontinue(View view)
    {
        Intent nextpage = new Intent(this, HomePageActivity.class);
        startActivity(nextpage);
    }

}
