package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
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
    }

}