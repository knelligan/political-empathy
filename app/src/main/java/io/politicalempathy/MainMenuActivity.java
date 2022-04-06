package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainMenuActivity extends AppCompatActivity {
    private Button quoteButton;
    private Button toleranceButton;
    private Button resourcesButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //render the main menu layout
        setContentView(R.layout.activity_main_menu);

        //create toolbar for the top of the main menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create a title fo the main menu
        Objects.requireNonNull(getSupportActionBar()).setTitle("Main Menu");

        //ensure there is a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //create a string to pass to the next toolbar
        String toolbarTitle = getIntent().getStringExtra("MENU CHOICE");

        //create variable to access quote button
        quoteButton = findViewById((R.id.quote_quiz_button));

        //set the action for clicking the quote button
        quoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //must have this open the quote survey activity
                //Intent intent = new Intent(MainMenuActivity.this, MainMenuActivity.class);
                Intent intent = new Intent(MainMenuActivity.this, QuoteActivity.class);
                startActivity(intent);
            }
        });

        //create variable to access tolerance button
        toleranceButton = findViewById((R.id.tolerance_quiz_button));

        //set the action for clicking the tolerance button
        toleranceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //must have this open the tolerance quiz activity
                Intent intent = new Intent(MainMenuActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        //create variable to access resources button
        resourcesButton = findViewById((R.id.resources_button));

        //set the action for clicking the resources button
        resourcesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //must have this open the resources activity
                Intent intent = new Intent(MainMenuActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        //create variable to access resources button
        logoutButton = findViewById((R.id.mainmenu_logout_button));

        //set the action for clicking the resources button
        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //must have this open the resources activity
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            MainMenuActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}