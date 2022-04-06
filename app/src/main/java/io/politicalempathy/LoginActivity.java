package io.politicalempathy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser, forgotPassword;
    private EditText editEmail, editPassword;
    private Button login;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("oncreate is working");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //create a link from the register text on login page
        registerUser = (TextView) findViewById(R.id.registeroption);

        //set the click function for the text
        registerUser.setOnClickListener(this);

        //create a link from the forgot password text on login page
        forgotPassword = (TextView) findViewById(R.id.forgotpasswordoption);

        //set the click function for the text
        forgotPassword.setOnClickListener(this);

        //initialize button for logging in
        login = (Button) findViewById(R.id.loginbuttonid);
        login.setOnClickListener(this);

        //create field to capture user text input for email
        editEmail = (EditText) findViewById(R.id.loginemailentryid);
        editPassword = (EditText) findViewById(R.id.loginpasswordentryid);

        //create instanceof firebase auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registeroption:
                startActivity(new Intent(LoginActivity.this, RegisterUserActivity.class));
                break;
            case R.id.forgotpasswordoption:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
            case R.id.loginbuttonid:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        //create strings from user input & trim extraneous spaces
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            //if email field is empty, provide error feedback
            editEmail.setError("Email is required!");

            //give focus to a email view
            editEmail.requestFocus();

        }
        if (password.isEmpty()) {
            //if password field is empty, provide error feedback
            editPassword.setError("Password is required!");

            //give focus to a password view
            editPassword.requestFocus();

        }
        //check that email address is in a valid format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //if email field is empty, provide error feedback
            editEmail.setError("Email format is invalid!");

            //give focus to a email view
            editEmail.requestFocus();
        }

        //check that password length is long enough
        if (password.length() < 6) {
            //firebase requires passwords to be at least 6 characters
            editPassword.setError("Minimum length for password is 6 characters!");

            //give focus to a password view
            editPassword.requestFocus();
        }

        //researched these methods here: https://www.tabnine.com/code/java/methods/com.google.firebase.auth.FirebaseAuth/signInWithEmailAndPassword
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //check if user has verified their email address
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "User successfully logged in!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                    }else{
                        //if it hasn't been verified, send a verification email
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Verify your account (check your email)", Toast.LENGTH_LONG).show();
                    }
                    //go to take the quote assessment


                } else {
                    //provide user error feedback and reload
                    Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

//        //create variable to access  button
//        login = findViewById((R.id.loginbuttonid));
//
//        //set the action for clicking the start button
//        login.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, QuoteActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }

//        @Override
//        public void onClick (View view){
//            switch (view.getId()) {
//                case R.id.registeroption:
//                    startActivity(new Intent(LoginActivity.this, RegisterUserActivity.class));
//                    break;
//
//            }
//        }
//    }