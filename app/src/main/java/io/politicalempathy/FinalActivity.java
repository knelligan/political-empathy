package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class FinalActivity extends AppCompatActivity {

    //create button interactivity for logout button
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        System.out.println("enteringfinalact");
        //create variable to access resources button
        logout = findViewById((R.id.final_logout5));

        //set the action for clicking the resources button
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //must have this open the resources activity
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FinalActivity.this, LoginActivity.class));
            }
        });
    }
}