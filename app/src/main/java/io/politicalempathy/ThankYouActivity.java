package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class ThankYouActivity extends AppCompatActivity {
    //create button interactivity for logout button
    private Button logout;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        //access the app name field on the splashpage to set the font
        title = findViewById(R.id.main_title15);

        //set the typeface to a special font Barlow
        Typeface typeface = ResourcesCompat.getFont(this, R.font.barlowboldx);
        title.setTypeface(typeface);

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