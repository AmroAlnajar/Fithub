package com.example.fithub;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
            finish();
        }


    }

    public void RegisterHereButton(View view)
    {
        final Intent RegisterPage = new Intent(this, RegisteringPage.class);
        startActivity(RegisterPage);
    }

    public void loguserin(View view)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || !email.contains("@") || !email.contains("."))
        {
            editTextEmail.setError("Invalid Email");
            return;
        }
        if(TextUtils.isEmpty(password) || password.length() < 8)
        {
            editTextPassword.setError("Invalid password");
            return;
        }
        final Intent HomePage = new Intent(this, HomePageActivity.class);

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    startActivity(HomePage);
                    finish();
                } else if(!isNetworkAvailable())
                {

                    progressDialog.dismiss();
                    alertDialog.setMessage("It seems like you're not connected to the internet, Please turn on your wifi or 4G connection");
                    alertDialog.show();

                } else {

                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                    if(errorCode.equals("ERROR_USER_NOT_FOUND") || errorCode.equals("ERROR_WRONG_PASSWORD"))
                    {
                        progressDialog.dismiss();
                        alertDialog.setMessage("Your Username or Password are invalid, make sure you've typed them correctly");
                        alertDialog.show();

                    }


                }

            }
        });



    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
