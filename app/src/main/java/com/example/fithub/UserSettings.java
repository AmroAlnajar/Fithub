package com.example.fithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserSettings extends AppCompatActivity {

    EditText currentpass;
    EditText newpass;
    Button newpassbutton;

    FirebaseAuth mauth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);


        currentpass = findViewById(R.id.currentpass);
        newpass = findViewById(R.id.newpass);
        newpassbutton = findViewById(R.id.savepassbutton);

        mauth = FirebaseAuth.getInstance();

        user = mauth.getCurrentUser();

        if(user != null)
        {
            System.out.println(user.getUid());

        } else
        {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }




    }

    public void changepassword(View view)
    {

        final android.app.AlertDialog alertDialog = new AlertDialog.Builder(UserSettings.this).create();


        final Intent intent = new Intent(this, MainActivity.class);


        if(currentpass.getText().toString().length() < 8 || currentpass.getText().toString().isEmpty())
        {
            currentpass.setError("Your password is not correct, please enter your correct password");
            return;

        }
        if(newpass.getText().toString().length() < 8 || newpass.getText().toString().isEmpty())
        {
            newpass.setError("your new password must be at least 8 characters long");
            return;
        }






        AuthCredential credintials = EmailAuthProvider.getCredential(user.getEmail(), currentpass.getText().toString());
        user.reauthenticate(credintials).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    user.updatePassword(newpass.getText().toString());

                    alertDialog.setMessage("success. Close this to log in again");
                    alertDialog.show();
                    mauth.signOut();
                    startActivity(intent);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                e.printStackTrace();
                alertDialog.setMessage(e.getLocalizedMessage());
                alertDialog.show();

                System.out.println(e.getLocalizedMessage());
            }
        });
    }
}
