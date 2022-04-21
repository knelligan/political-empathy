package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;

public class ComparisonActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView authorTitle, authorEconScore, authorSocScore, userEconScore, userSocScore;
    Spinner spinner;
    Button takeAgain, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        //set the spinner to view in the xml
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.authors, android.R.layout.simple_spinner_item);

        //set the dropdown menu
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //create the spinner listener to wait for user input
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        //instantiate text fields
        authorTitle = findViewById(R.id.authortitle);
        authorEconScore = findViewById(R.id.authoreconscore);
        authorSocScore = findViewById(R.id.authorsocscore);
        userEconScore = findViewById(R.id.usereconscore);
        userSocScore = findViewById(R.id.usersocscore);

        //set user score text
        userEconScore.setText("Economic Score: " + DbQuery.globalEconScore);
        userSocScore.setText("Social Score: " + DbQuery.globalSocScore);

        //create interactive response for take again button
        takeAgain = (Button) findViewById(R.id.takeagaincompare);
        logout = (Button) findViewById(R.id.logoutcompare);
        takeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear response list to store fresh results
                DbQuery.globalResponseList.clear();

                //reset the quote counter to 0 to display survey from start
                DbQuery.globalQuoteCounter = 0;
                startActivity(new Intent(ComparisonActivity.this, QuoteActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click is working for submit");
                //must have this open the resources activity
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ComparisonActivity.this, LoginActivity.class));
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerText = adapterView.getItemAtPosition(i).toString();
        System.out.println("spinner text is: " + spinnerText);

        //call display score to show the quote author's scores
        displayScore(spinnerText);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void displayScore(String selectedText) {

        if (selectedText.equals("Jimmy Carter")) {
            //set carter's text fields
            authorTitle.setText("Jimmy Carter");
            authorEconScore.setText("Economic Score: -4");
            authorSocScore.setText("Social Score: -4");

        } else if (selectedText.equals("Bill Clinton")) {
            //set carter's text fields
            authorTitle.setText("Bill Clinton");
            authorEconScore.setText("Economic Score: -2");
            authorSocScore.setText("Social Score: -3");


        } else if (selectedText.equals("John F. Kennedy")) {
            //set carter's text fields
            authorTitle.setText("John F. Kennedy");
            authorEconScore.setText("Economic Score: -5");
            authorSocScore.setText("Social Score: 2");

        } else if (selectedText.equals("Abraham Lincoln")) {
            //set carter's text fields
            authorTitle.setText("Abraham Lincoln");
            authorEconScore.setText("Economic Score: -3");
            authorSocScore.setText("Social Score: -2");


        } else if (selectedText.equals("Barack Obama")) {
            //set carter's text fields
            authorTitle.setText("Barack Obama");
            authorEconScore.setText("Economic Score: -4");
            authorSocScore.setText("Social Score: 3");

        } else if (selectedText.equals("Rand Paul")) {
            //set carter's text fields
            authorTitle.setText("Rand Paul");
            authorEconScore.setText("Economic Score: 9");
            authorSocScore.setText("Social Score: -7");


        } else if (selectedText.equals("Ronald Reagan")) {
            //set carter's text fields
            authorTitle.setText("Ronald Reagan");
            authorEconScore.setText("Economic Score: 6");
            authorSocScore.setText("Social Score: 6");

        } else if (selectedText.equals("Theodore Roosevelt")) {
            //set carter's text fields
            authorTitle.setText("Theodore Roosevelt");
            authorEconScore.setText("Economic Score: 0");
            authorSocScore.setText("Social Score: 5");


        } else if (selectedText.equals("Marco Rubio")) {
            //set carter's text fields
            authorTitle.setText("Marco Rubio");
            authorEconScore.setText("Economic Score: 3");
            authorSocScore.setText("Social Score: 4");

        } else if (selectedText.equals("Bernie Sanders")) {
            //set carter's text fields
            authorTitle.setText("Bernie Sanders");
            authorEconScore.setText("Economic Score: 9");
            authorSocScore.setText("Social Score: -7");

        }
    }

}