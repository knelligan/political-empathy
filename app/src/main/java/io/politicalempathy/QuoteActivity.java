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

    //list of all quotes used in the program------------------------------------------replace this reference with global variable
    //private List<Quote> quoteList;


    //current number of the quote being displayed in the series
    private int quoteCounter;

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

        quoteNum.setText(DbQuery.globalQuoteList.get(0).getQuoteID());
        quoteText.setText(DbQuery.globalQuoteList.get(0).getQuoteText());

        //initialize quote counter
        quoteCounter = 0;
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
        //save the value to make calculations later
        //------------------------------------------------------- fix this later
        saveChoice(selection);
    }

    private void saveChoice(int userChoice) {
        //save the value in the file
        //-------------------------------------------------------fix later

        //ths is how it would work
        DbQuery.addResponse(quoteCounter, userChoice, new CompleteListener() {
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
        nextQuote();
    }

    private void setData(int questionNumber){

    }

    private void nextQuote() {
        //if (quoteCounter < quoteList.size() - 1) {
        if (quoteCounter < DbQuery.globalQuoteList.size() - 1) {

            //update the quote number
            quoteCounter++;

            //update the quote number at the top
            quoteNum.setText("Quote " + String.valueOf(quoteCounter + 1));

            //update quote text
            //quoteText.setText(quoteList.get(quoteCounter).getQuoteText());
            quoteText.setText(DbQuery.globalQuoteList.get(quoteCounter).getQuoteText());

        } else {
            // Go to calculations of political compass
            // create a class to get calculations based on answers so far, then call
            //the following method
            Intent intent = new Intent(QuoteActivity.this, CalcActivity.class);
            startActivity(intent);
            QuoteActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

    public void setQuoteCounter(int quoteCounter) {
        this.quoteCounter = quoteCounter;
    }

    public int getQuoteCounter() {
        return quoteCounter;
    }

}