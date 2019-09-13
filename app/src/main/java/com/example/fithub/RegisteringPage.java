package com.example.fithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisteringPage extends AppCompatActivity {

    private EditText Emailregister;
    private EditText passwordregister;
    private EditText confirmpasswordregister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering_page);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Emailregister = (EditText) findViewById(R.id.emailregister);
        passwordregister = (EditText) findViewById(R.id.passwordregister);
        confirmpasswordregister = (EditText) findViewById(R.id.confirmpasswordregister);
    }


    public void registerUser(View view)
    {
        final Intent DataRegistery = new Intent(this, DataRegistery.class);

        String email = Emailregister.getText().toString().trim();
        String password = passwordregister.getText().toString().trim();
        String confirmpassword = confirmpasswordregister.getText().toString().trim();


        if(TextUtils.isEmpty(email) || !email.contains("@") || !email.contains("."))
        {
            Emailregister.setError("Invalid Email");
            return;
        } else if(TextUtils.isEmpty(password) || password.length() < 8)
        {
            passwordregister.setError("Invalid password");
            return;
        } else if(!password.equals(confirmpassword))
        {
            confirmpasswordregister.setError("Password doesn't match");
            return;
        }


        final AlertDialog alertDialog = new AlertDialog.Builder(RegisteringPage.this).create();
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    //start new page
                    startActivity(DataRegistery);
                }
                else
                {
                   alertDialog.setTitle("something");
                    alertDialog.setMessage("registration failed!");
                    alertDialog.show();
                    progressDialog.dismiss();
                    //registration failed
                }


            }
        });



    }
}
