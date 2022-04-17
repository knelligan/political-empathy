package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuoteActivity extends AppCompatActivity implements View.OnClickListener {
    //the number of the quote in the series, and the text from the quote
    private TextView quoteNum, quoteText;

    //categories for the respondent to choose from
    private Button stronglyAgree, somewhatAgree, somewhatDisagree, stronglyDisagree;


    //current number of the quote being displayed in the series
    //private int quoteCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_econ_left1);


        //get the quote used
        quoteNum = findViewById(R.id.quotenum);


        //get the text used in the quote
        quoteText = findViewById(R.id.quotetext);

        //create the buttons for the survey
        stronglyAgree = findViewById(R.id.stronglyagree1aa);
        somewhatAgree = findViewById(R.id.somewhatagree1);
        somewhatDisagree = findViewById(R.id.somewhatdisagree1);
        stronglyDisagree = findViewById(R.id.stronglydisagree1);

        //set click listeners
        stronglyAgree.setOnClickListener(this);
        somewhatAgree.setOnClickListener(this);
        somewhatDisagree.setOnClickListener(this);
        stronglyDisagree.setOnClickListener(this);

        //load quotes to quote activity.
        //getQuotesList();
        setQuote();

    }


    private void setQuote() {
        //set the quotes to the current quote counter
//        quoteNum.setText(DbQuery.globalQuoteList.get(DbQuery.globalQuoteCounter).getQuoteID());
//        quoteText.setText(DbQuery.globalQuoteList.get(DbQuery.globalQuoteCounter).getQuoteText());

        if (DbQuery.globalQuoteCounter < DbQuery.globalQuoteList.size()) {
            System.out.println("quote counter is: " + DbQuery.globalQuoteCounter + " quotelistsize: " + DbQuery.globalQuoteList.size());
            //update the quote number at the top
            quoteNum.setText("Quote " + String.valueOf(DbQuery.globalQuoteCounter + 1));

            //update quote text
            quoteText.setText(DbQuery.globalQuoteList.get(DbQuery.globalQuoteCounter).getQuoteText());

        } else {
            // Go to calculations of political compass
            // create a class to get calculations based on answers so far, then call
            // the following method
            Intent intent = new Intent(QuoteActivity.this, CalcActivity.class);
            startActivity(intent);
            QuoteActivity.this.finish();
        }
    }

    @Override
    public void onClick(View view) {
        int selection = 0;
        switch (view.getId()) {
            case R.id.stronglyagree1aa:
                selection = 1;
                break;
            case R.id.somewhatagree1:
                selection = 2;
                break;
            case R.id.somewhatdisagree1:
                selection = 3;
                break;
            case R.id.stronglydisagree1:
                selection = 4;
                break;
            default:
        }

        System.out.println("agree/disagree button click method working");

        //make a call to savechoice to save the user's survey response

        saveChoice(selection);
    }

    private void saveChoice(int userChoice) {

        System.out.println("savechoice method working");

        //save the value in the file

        //add user response to the database
        DbQuery.addResponse(DbQuery.globalQuoteCounter, userChoice, new CompleteListener() {
            @Override
            public void onSuccess() {
                System.out.println("response recorded");
            }

            @Override
            public void onFailure() {
                System.out.println("response failed to record");
            }
        });


        //creates a small delay after selction
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 2000);

        //move to the next question
        //nextQuote();
        displayAuthor();
    }

    private void displayAuthor() {
        Intent intent = new Intent(QuoteActivity.this, AuthorActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {

    }


}