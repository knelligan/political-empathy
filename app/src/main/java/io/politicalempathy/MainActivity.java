package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView title;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_login2);

        //create variable to access title text (to change font)
        title = findViewById((R.id.main_title));

        //create variable to access start button
        start = findViewById((R.id.main_startButton));

        //create typeface for text/logo
        Typeface typeface = ResourcesCompat.getFont(this, R.font.barlowboldx);
        title.setTypeface(typeface);

        //set the action for clicking the start button
        start.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                Intent intent = new Intent(MainActivity.this, QuoteActivity.class);
                startActivity(intent);
            }
        });


    }
}