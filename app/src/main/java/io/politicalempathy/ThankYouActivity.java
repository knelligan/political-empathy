package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ThankYouActivity extends AppCompatActivity {
    //create button interactivity for logout button
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        //create variable to access resources button
        logout = findViewById((R.id.logoutthankyou));

        //set the action for clicking the resources button
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //must have this open the resources activity
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ThankYouActivity.this, LoginActivity.class));
            }
        });
    }
}