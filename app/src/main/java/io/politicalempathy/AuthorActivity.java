package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AuthorActivity extends AppCompatActivity {
    /* ImageView for the political compass image used */
    private ImageView authorImage;

    /* Textview for the economic score */
    private TextView authName;

    /* Textview for the social score */
    private TextView authJob;

    /* Textview for the social score */
    private TextView authAlign;

    //create button interactivity for register button
    private Button nextQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        //TextView value updates
        authName = findViewById(R.id.authorname);
        authJob = findViewById(R.id.authorjob);
        authAlign = findViewById(R.id.authoralignment);

        //update image value
        authorImage = findViewById(R.id.authorimage);

        setAuthorText();

        setAuthorImage();

        //create interactive response for take again button
        nextQuote = (Button) findViewById(R.id.nextquotebutton);
        nextQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //increment quote counter to show next quote
                DbQuery.globalQuoteCounter++;
                //go back to quote activity
                startActivity(new Intent(AuthorActivity.this, QuoteActivity.class));
            }
        });



    }
    public void setAuthorText(){
        //set author name
        String name = DbQuery.globalQuoteList.get(DbQuery.globalQuoteCounter).getAuthor();
        authName.setText(name);

        //set author job
        String job = DbQuery.globalQuoteList.get(DbQuery.globalQuoteCounter).getJob();
        authJob.setText(job);

        //set author align
        String align = DbQuery.globalQuoteList.get(DbQuery.globalQuoteCounter).getBias();
        authAlign.setText(align);


    }

    public void setAuthorImage(){

        String name = DbQuery.globalQuoteList.get(DbQuery.globalQuoteCounter).getAuthor();

        switch (DbQuery.globalQuoteCounter) {
            case 0:
                //display roosevelt picture
                authorImage.setImageResource(R.drawable.troosevelt);
                break;
            case 1:
                //display obama picture
                authorImage.setImageResource(R.drawable.obama);
                break;
            case 2:
                //display kennedy picture
                authorImage.setImageResource(R.drawable.jfk);
                break;
            case 3:
                //display paul picture
                authorImage.setImageResource(R.drawable.paul2);
                break;
            case 4:
                //display clinton picture
                authorImage.setImageResource(R.drawable.clinton2);
                break;
            case 5:
                //display rubio picture
                authorImage.setImageResource(R.drawable.rubio2);
                break;
            case 6:
                //display lincoln picture
                authorImage.setImageResource(R.drawable.lincoln);
                break;
            case 7:
                //display sanders picture
                authorImage.setImageResource(R.drawable.sanders2);
                break;
            case 8:
                //display carter picture
                authorImage.setImageResource(R.drawable.carter2);
                break;
            case 9:
                //display reagan picture
                authorImage.setImageResource(R.drawable.reagan);
                break;
            default:
        }
    }

}