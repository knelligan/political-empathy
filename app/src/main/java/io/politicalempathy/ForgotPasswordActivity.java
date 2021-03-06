package io.politicalempathy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editEmail;
    private Button resetButton, returnButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //initialize the return to login menu button
        returnButton = (Button) findViewById(R.id.loginreturn);
        returnButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });


        //create field to capture user text input for email
        editEmail = (EditText) findViewById(R.id.useremailforgotpasswordvalue);


        //initialize the reset button
        resetButton = (Button) findViewById(R.id.resetpasswrodid);
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                resetPassword();
            }


        });

        auth = FirebaseAuth.getInstance();


    }
    /**
     * Resets the user password
     */
    private void resetPassword() {


        //create strings from user input & trim extraneous spaces
        String email = editEmail.getText().toString().trim();

        if (email.isEmpty()) {
            //if email field is empty, provide error feedback
            editEmail.setError("Email is required!");

            //give focus to a email view
            editEmail.requestFocus();
        }

        //check that email address is in a valid format
        //researched here: https://www.programcreek.com/java-api-examples/?class=android.util.Patterns&method=EMAIL_ADDRESS
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //if email field is empty, provide error feedback
            editEmail.setError("Email format is invalid!");

            //give focus to a email view
            editEmail.requestFocus();
        }

        //send an email reset
        //researched here: https://stackoverflow.com/questions/42800349/forgot-password-in-firebase-for-android
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Check email for a password reset", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Something is wrong. Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}