package io.politicalempathy;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private TextView appName;

    //create a list based on database menu
    public static List<String> menuList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //access the app name field on the splashpage to set the font
        appName = findViewById(R.id.appName);

        //set the typeface to a special font Barlow
        Typeface typeface = ResourcesCompat.getFont(this, R.font.barlowboldx);
        appName.setTypeface(typeface);

        //create animation for logo text
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.logofontanim);
        appName.setAnimation(anim);

        //Access a cloud firestore instance from activity
        DbQuery.globalFirestore = FirebaseFirestore.getInstance();

        //set the quoteCounter value to 0
        DbQuery.globalQuoteCounter = 0;

        //create a thread/runnable to make the splash screen launch
        new Thread(new Runnable() {
            @Override
            public void run() {

                //load data from the database
                //loadData();

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //start the splash screen activity
                //adapted code from here: https://medium.com/geekculture/implementing-the-perfect-splash-screen-in-android-295de045a8dc
                //startActivity(new Intent(SplashActivity.this, CalcActivity.class));


                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                SplashActivity.this.finish();
            }
        }).start();
    }

}