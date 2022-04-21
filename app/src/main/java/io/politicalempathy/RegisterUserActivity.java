package io.politicalempathy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    //declare an instance of firebase auth
    private FirebaseAuth mAuth;

    //create Textviews to create interactivity with fields
    private TextView iconHeader, editFullName, editAge, editEmail, editPassword;

    //create button interactivity for register button
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //initialize firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        //create interactive response with icon/header
        iconHeader = (TextView) findViewById(R.id.logotextthin);
        iconHeader.setOnClickListener(this);

        //create interactive response for register button
        register = (Button) findViewById(R.id.registerbuttonid);
        register.setOnClickListener(this);

        //create interactivity for the edit text fields
        editFullName = (EditText) findViewById(R.id.userfullnamevalue);
        editAge = (EditText) findViewById(R.id.useragevalue);
        editEmail = (EditText) findViewById(R.id.useremailregistervalue);
        editPassword = (EditText) findViewById(R.id.userpasswordregistervalue);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //if the user clicks the logo/text at the top of the app, return to login page
            case R.id.logotextthin:
                startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                break;
            //if the user clicks the register button, call registerUser method
            case R.id.registerbuttonid:
                registerUser();
        }
    }

    /**
     * User registration method that takes in text and does basic error checking
     * and provides user feedback if information was not entered in the
     * correct feedback. Upon completion the information is loaded into
     * firebase.
     *
     * This uses firebase authentication and realtime database for storage
     */
    private void registerUser() {
        //create strings from user input & trim extraneous spaces
        String name = editFullName.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        //validate user input
        if (name.isEmpty()) {
            //if name field is empty, provide error feedback
            editFullName.setError("Full name is required!");

            //give focus to a name view
            editFullName.requestFocus();

        }
        if (age.isEmpty()) {
            //if age field is empty, provide error feedback
            editAge.setError("Age is required!");

            //give focus to a age view
            editAge.requestFocus();

        }
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
        //validate age
        if (!age.isEmpty()) {
            int ageValue = Integer.parseInt(age);
            if (ageValue < 18 || ageValue > 120) {
                //try to ensure valid age entered
                editAge.setError("Must be 18 or older (max age is 120)!");

                //give focus to a email view
                editAge.requestFocus();
            }
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
        //researched this here: https://www.tabnine.com/code/java/methods/com.google.firebase.auth.FirebaseAuth/createUserWithEmailAndPassword
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, age, email);

                            //researched this here: https://riptutorial.com/firebase-database/example/28681/understanding-which-data-referenced-by-getreference--
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //firebase documentation for auth: https://firebase.google.com/docs/auth/android/password-auth
                                    if (task.isSuccessful()) {

                                        //create the user in the database if registration completes
                                        DbQuery.createUserData(email, name, new CompleteListener() {
                                            @Override
                                            public void onSuccess() {
                                                //tell the user that thy were registered successfully
                                                Toast.makeText(RegisterUserActivity.this, "User successfully registered!", Toast.LENGTH_LONG).show();

                                                //move to the login page
                                                Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                                                startActivity(intent);

                                                //ensure the user can't click back after registering
                                                RegisterUserActivity.this.finish();
                                            }

                                            @Override
                                            public void onFailure() {
                                                //if can't create database provide user feedback of failure
                                                Toast.makeText(RegisterUserActivity.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();

                                            }
                                        });



                                    } else {
                                        Toast.makeText(RegisterUserActivity.this, "Failed to register user!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterUserActivity.this, "Failed to register user!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}