package com.example.fithub;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.example.fithub.UploadActivity.REQUEST_IMAGE_CAPTURE;
import android.widget.TimePicker;


public class MyBodyDialog extends AppCompatDialogFragment {

    public static String nopic =  "https://firebasestorage.googleapis.com/v0/b/fithub-a4992.appspot.com/o/fastebilder%2Ficons8-no-camera-100.png?alt=media&token=c52cd66a-3ef1-4aea-a63c-2cde9d38a779";

    private TextView datemybody;
    private EditText weightmybody;
    private Button uploadbutton;
    private String date;
    private String weight;
    private DatabaseReference databaseinfo;
    private FirebaseAuth mauth;
    private FirebaseUser currentuser;
    private DayOfWeek dayOfWeek = null;
    private String dayofweekuper = null;
    private String dayofweekrefined = null;
    private String pic;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    DatabaseReference chef;
    private TimePicker timePicker1;


    private ImageView viewz;
    byte[] byteArray;

    //String encoded;
    String datefordatabaseformatted;
    String downloadurl;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);



        viewz = view.findViewById(R.id.showpic);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        final String formattedDate = df.format(c);

        SimpleDateFormat datefordatabase = new SimpleDateFormat("hh-mm-ss-ms dd-MMM-yyyy");
        datefordatabaseformatted = datefordatabase.format(c);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dayOfWeek = DayOfWeek.from(LocalDate.now());
            dayofweekuper = dayOfWeek.toString().toLowerCase();
            dayofweekrefined = dayofweekuper.substring(0, 1).toUpperCase() + dayofweekuper.substring(1);
        }

        mauth = FirebaseAuth.getInstance();
        currentuser = mauth.getCurrentUser();

        mStorageRef= FirebaseStorage.getInstance().getReference(currentuser.getUid());


        if (currentuser != null) {

            System.out.println(currentuser.getUid());
        } else {
            Intent newintent = new Intent(getActivity(), MainActivity.class);
            startActivity(newintent);
        }


        datemybody = (TextView) view.findViewById(R.id.currentdatedialog);
        datemybody.setText(dayofweekrefined + " - " + formattedDate);
        weightmybody = (EditText) view.findViewById(R.id.newweightdialog);
        builder.setView(view).setTitle("Update your weight and picture").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if(byteArray != null && !weightmybody.getText().toString().isEmpty())
                        {
                            final ProgressDialog m = new ProgressDialog(getActivity());
                            m.setMessage("Uploading photo....");
                            m.show();

                            StorageReference Ref = mStorageRef.child((currentuser.getUid() + "-" + datefordatabaseformatted));
                            uploadTask = Ref.putBytes(byteArray)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    System.out.println(uri.toString());

                                                    downloadurl = uri.toString();
                                                    pic = uri.toString();
                                                   SendDataToDataBase();

                                                   m.dismiss();

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
                        else
                        {


                            pic = nopic;
                            SendDataToDataBase();
                        }
                    }
                });


        uploadbutton = view.findViewById(R.id.uploadimagedialog);
        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        return builder.create();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //img.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();

           String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            System.out.println(encoded);

            // nm.setText(encoded);

            byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            viewz.setImageBitmap(decodedByte);

            System.out.println();

        }




    }



    public void SendDataToDataBase()
    {
        weight = weightmybody.getText().toString().trim();
        date = datemybody.getText().toString().trim();

        System.out.println(downloadurl);

        if(!weight.isEmpty())
        {
            WeightEntries entries = new WeightEntries(date, weight, pic);
            Date today = Calendar.getInstance().getTime();

            databaseinfo = FirebaseDatabase.getInstance().getReference("Weight entries").child(currentuser.getUid()).child(today.toString());

            databaseinfo.setValue(entries);

            System.out.println("-------------------------");
            System.out.println(datemybody.getText());
            System.out.println(weightmybody.getText());
            System.out.println("-------------------------");

           // chef = FirebaseDatabase.getInstance().getReference().child("Personal Info").child(currentuser.getUid()).child("weight");
           // chef.setValue(weightmybody.getText().toString().trim());

        }
        else {
            System.out.println("data empty, not saving...");
        }
    }

}
