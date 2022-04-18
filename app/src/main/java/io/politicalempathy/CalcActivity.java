package io.politicalempathy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalcActivity extends AppCompatActivity {

    /* Textview for summary of results */
    private TextView summaryResults;

    /* Textview for the economic score */
    private TextView econScore;

    /* Textview for the social score */
    private TextView socScore;

    /* ImageView for the political compass image used */
    private ImageView compass;

    /* Economic score value (x val) */
    private int econScoreValue;

    /* Social score value (y val) */
    private int socScoreValue;

    /* Bitmap object used for drawing */
    private Bitmap bitmap;

    //create button interactivity for register button
    private Button takeAgain, submitResults;

    //share the cardview of the results.
    private CardView resultsView;

    //store the summary results for sharing
    private String shareSummaryData;

    //lets us know the user was asked to share
    private boolean shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        //set default shared state
        shared = false;

        //cardview instance of results
        resultsView = findViewById(R.id.cardView);

        //create interactive response for take again button
        takeAgain = (Button) findViewById(R.id.takeagain);
        submitResults = (Button) findViewById(R.id.submit_final_screen);
        takeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear response list to store fresh results
                DbQuery.globalResponseList.clear();

                //reset the quote counter to 0 to display survey from start
                DbQuery.globalQuoteCounter = 0;
                startActivity(new Intent(CalcActivity.this, QuoteActivity.class));
            }
        });
        submitResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click is working for submit");
                if (!shared) {
                    dialogShareRequest();
                } else {
                    //clear response list to store fresh results
                    DbQuery.globalResponseList.clear();

                    //reset the quote counter to 0 to display survey from start
                    DbQuery.globalQuoteCounter = 0;

                    startActivity(new Intent(CalcActivity.this, ThankYouActivity.class));
                }
                //startActivity(new Intent(CalcActivity.this, ThankYouActivity.class));
            }
        });


        //researched plotting values here: https://programmerworld.co/android/how-to-plot-arrays-x-and-y-coordinates-on-axes-in-your-android-app-without-using-dependencies-or-libraries/

        //TextView value updates
        econScore = findViewById(R.id.economic_bias_score);
        socScore = findViewById(R.id.social_bias_score);
        summaryResults = findViewById(R.id.summaryresults);

        //update image value
        compass = findViewById(R.id.politicalchart);

        //generate scores by accessing stored values
        scoreCalculations();

        //plot the value on the compass
        plotValue();

        //create summary results
        writeSummaryResults();


    }

    public void scoreCalculations() {
        //get the current response in the list
        Response response;

        //set intial count of econ/social to 0
        //set initial score total of econ/social to 0
        int econCount = 0;
        double econTotal = 0;
        int socCount = 0;
        double socTotal = 0;

        //get each response of the users from the locally stored response arraylist
        for (int i = 0; i < DbQuery.globalResponseList.size(); i++) {

            //local variable for the current response of the user in the arraylist to
            //to grab each response from
            response = DbQuery.globalResponseList.get(i);
            if (response.getType().equals("Economic")) {
                //type is economic
                econCount++;
                econTotal += response.getResponseValue();
            } else {
                //type is social
                socCount++;
                socTotal += response.getResponseValue();
            }
        }
        //here is where i will store the econ/soc in user class----------------------------------------------------------------------------------------------
        //calculate econ score by finding the average of the
        //economic responses
        double avgEcon = Math.round(econTotal / econCount);
        //LoginActivity.currentlyLoggedInUser.setEconScore(avgEcon);


        //calculate the social score by finding the average of
        //the social responses
        double avgSoc = Math.round(socTotal / socCount);
        //LoginActivity.currentlyLoggedInUser.setSocialScore(avgSoc);


        //cast the score value to int to make the score easier to understand/read

        econScoreValue = (int) avgEcon;
        socScoreValue = (int) avgSoc;


    }


    public void plotValue() {
        //this needs to be updated/replaced with scores from db-------------------------------------------------------
        //calculate the average of the econ scores
        //x = econ
        //econScoreValue = -3;

        //calculate the average of the social scores------------------------------------
        //y = soc
        //socScoreValue = 7;

        //researched bitmap info here: https://itecnote.com/tecnote/android-getting-bitmap-from-custom-surfaceview/
        //create the bitmap used for plotting
        bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);

        //researched canvas here: https://medium.com/over-engineering/getting-started-with-drawing-on-the-android-canvas-621cf512f4c7
        //create the canvas used for plotting
        Canvas canvas = new Canvas(bitmap);

        //captures strokes etc used in the drawing/painting
        Paint paint = new Paint();

        //set the color of the painted object
        paint.setColor(Color.GREEN);

        //flag to ensure your drawing has smooth edges
        paint.setAntiAlias(true);

        //set the color of the painted object
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(12);

        //researched filled rects here: https://stackoverflow.com/questions/13545792/drawing-a-filled-rectangle-with-a-border-in-android
        //set fill color
        Paint fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);

        //border color
        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(10);

        //define smaller rectangles in compass
        Rect leftUp = new Rect(0, 0, 500, 500);
        Rect rightUp = new Rect(500, 0, 1000, 500);
        Rect leftDown = new Rect(0, 500, 500, 1000);
        Rect rightDown = new Rect(500, 500, 1000, 1000);

        //draw upper left rect
        fillPaint.setColor(Color.rgb(233, 185, 212));
        canvas.drawRect(leftUp, fillPaint);
        canvas.drawRect(leftUp, strokePaint);

        //draw upper right rect
        fillPaint.setColor(Color.rgb(115, 195, 249));
        canvas.drawRect(rightUp, fillPaint);
        canvas.drawRect(rightUp, strokePaint);

        //draw lower left rect
        fillPaint.setColor(Color.rgb(180, 255, 254));
        canvas.drawRect(leftDown, fillPaint);
        canvas.drawRect(leftDown, strokePaint);

        //draw lower right rect
        fillPaint.setColor(Color.rgb(216, 189, 255));
        canvas.drawRect(rightDown, fillPaint);
        canvas.drawRect(rightDown, strokePaint);

        //draw x axis
        canvas.drawLine(0, 500, 1000, 500, paint);

        //draw y axis
        canvas.drawLine(500, 0, 500, 1000, paint);


        //set the drawing stroke/width
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(40);
        paint.setColor(Color.RED);

        //generate center points on the canvas to start from
        Point plotPoint = translatePoint(econScoreValue, socScoreValue);
        //Point plotPoint = new Point(7,15);

        //get translated point values
        int x = plotPoint.x;
        int y = plotPoint.y;

        //canvas starts at top left point in the map
        //draw point method researched here: https://www.tabnine.com/code/java/methods/android.graphics.Canvas/drawPoint
        canvas.drawPoint(x, y, paint);


        //compass is the imageview
        compass.setImageBitmap(bitmap);


        //show score values in textView
        econScore.setText("Economic Bias:  " + econScoreValue);
        socScore.setText("Social Bias:         " + socScoreValue);
    }

    public Point translatePoint(int x, int y) {

        //the point starts (0, 0) in the upper left corner.
        //begin calculations from the center instead by adding to
        //or subtracting from the midpoint (500,500) and using a
        //multiplier of 50 to account for proportional movement based
        // on grid-size (1000 x 1000)

        if (x < 0) {
            //if x is negative, we want to subtract from origin value of 500
            //we take absolute value of x because there would be no
            //negative plot points
            x = 500 - (Math.abs(x) * 50);
        } else {
            //if x is non-negative, we simply add to the midpoint of 500
            x = 500 + (Math.abs(x) * 50);
        }

        //y values less than zero are calculated similar to the
        //x values but the equations are flipped for negative
        //numbers and positive numbers
        if (y < 0) {
            //if y is negative, we add to the origin value of 500
            //the negative y values are lower on the map so we must
            //add to the start point of 500 to push the point downward
            y = 500 + (Math.abs(y) * 50);
        } else {
            //if y is non-negative, we subtract from the midpoint to
            //push the point upward.
            y = 500 - (Math.abs(y) * 50);
        }

        //return a point value that represents the change in x and change in y
        //from the origin point
        return new Point(x, y);
    }

    public void writeSummaryResults() {
        boolean econModerate;
        boolean socialModerate;
        boolean overallModerate;

        if (econScoreValue > -5 && econScoreValue < 5) {
            econModerate = true;
        } else {
            econModerate = false;
        }
        if (socScoreValue > -5 && socScoreValue < 5) {
            socialModerate = true;
        } else {
            socialModerate = false;
        }

        if (econModerate && socialModerate) {
            overallModerate = true;
        } else {
            overallModerate = false;
        }

        String summary = "Summary: Your economic views are ";

        //perfectly moderate-----------------------------------------
        if (econScoreValue == 0) {
            summary += "evenly balanced between left and right, and ";

            //left leaning-----------------------------------------
        } else if (econScoreValue < 0 && econScoreValue >= -2) {
            summary += "barely left-leaning and ";
        } else if (econScoreValue < -2 && econScoreValue >= -5) {
            //{-5 to > 1} moderate left economic
            summary += "moderately left-leaning and ";
        } else if (econScoreValue < -5 && econScoreValue >= -8) {
            //{-9 to -6} moderate left economic
            summary += "strongly left-leaning and ";
        } else if (econScoreValue < -8) {
            summary += "extremely left-leaning and ";

            //right leaning -----------------------------------------
        } else if (econScoreValue > 0 && econScoreValue <= 2) {
            summary += "barely right-leaning and ";
        } else if (econScoreValue > 2 && econScoreValue <= 5) {
            //{-5 to > 1} moderate left economic
            summary += "moderately right-leaning and ";
        } else if (econScoreValue > 5 && econScoreValue <= 8) {
            //{-9 to -6} moderate left economic
            summary += "strongly right-leaning and ";
        } else if (econScoreValue > 8) {
            summary += "extremely right-leaning and ";
        }

        //add social score preface
        summary += "your social views are ";

        //perfectly moderate-----------------------------------------
        if (socScoreValue == 0) {
            summary += "evenly balanced between authoritarian and libertarian.";

            //left leaning-----------------------------------------
        } else if (socScoreValue < 0 && socScoreValue >= -2) {
            summary += "slightly libertarian-leaning.";
        } else if (socScoreValue < -2 && socScoreValue >= -5) {
            //{-5 to > 1} moderate libertarian
            summary += "moderately libertarian.";
        } else if (socScoreValue < -5 && socScoreValue >= -8) {
            //{-9 to -6} moderate libertarian
            summary += "strongly libertarian.";
        } else if (socScoreValue < -8) {
            summary += "extremely libertarian.";

            //right leaning -----------------------------------------
        } else if (socScoreValue > 0 && socScoreValue <= 2) {
            summary += "slightly authoritarian.";
        } else if (socScoreValue > 2 && socScoreValue <= 5) {
            //{-5 to > 1} moderate authoritarian
            summary += "moderately authoritarian.";
        } else if (socScoreValue > 5 && socScoreValue <= 8) {
            //{-9 to -6} moderate authoritarian
            summary += "strongly authoritarian.";
        } else if (socScoreValue > 8) {
            summary += "extremely authoritarian.";
        }

        //display a brief summary of results above the compass
        summaryResults.setText(summary);
        shareSummaryData = summary;
        System.out.println(summary);

    }


    private void dialogShareRequest() {
        //researched this here: https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android

        //set shared question flag to true
        shared = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Share Results");
        builder.setMessage("Would you like to share your survey results?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //clear response list to store fresh results
                DbQuery.globalResponseList.clear();

                //reset the quote counter to 0 to display survey from start
                DbQuery.globalQuoteCounter = 0;

                // proceed to share action
                shareResults();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //clear response list to store fresh results
                DbQuery.globalResponseList.clear();

                //reset the quote counter to 0 to display survey from start
                DbQuery.globalQuoteCounter = 0;

                startActivity(new Intent(CalcActivity.this, ThankYouActivity.class));
                // proceed to submit intent action
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void shareResults() {
        //researched here: https://stackoverflow.com/questions/30196965/how-to-take-a-screenshot-of-a-current-activity-and-then-share-it
//researched here: https://www.includehelp.com/android/share-something-on-social-media.aspx

        Context cardviewContext = resultsView.getContext();

        //Bitmap cardBitMap = getBitmapFromView(resultsView);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");

        String shareMessage = "Here are my results from Political Empathy: ";
        shareMessage += shareSummaryData + " ";
        shareMessage += "Download now from: https://politicalempathy.io/";

        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        startActivity(Intent.createChooser(intent, "Share results using"));
    }
}

//
////        Bitmap bitmap = Bitmap.createBitmap(resultsView.getWidth(), resultsView.getHeight(),
////                Bitmap.Config.ARGB_8888);
////        if (resultsView instanceof CardView) {
////            bitmap = ((CardView) resultsView).getBitmap();
////        } else {
////            Canvas c = new Canvas(bitmap);
////            resultsView.draw(c);
////        }
//
//        //new Share(cardviewContext).shareIntent(cardBitMap, "msg", "randomstring");
//
//    }
//
////    public static Bitmap getBitmapFromView(View view) {
//////        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//////        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
//////                Bitmap.Config.ARGB_8888);
//////        Canvas canvas = new Canvas(bitmap);
//////        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//////        view.draw(canvas);
//////        return bitmap;
////
//////researched here: https://stackoverflow.com/questions/61308719/converting-a-cardview-to-an-image-bitmap
////        int totalHeight = view.getHeight();
////        int totalWidth = view.getWidth();
////        float percent = 0.7f;//use this value to scale bitmap to specific size
////
////        Bitmap canvasBitmap = Bitmap.createBitmap(totalWidth,totalHeight, Bitmap.Config.ARGB_8888);
////        Canvas canvas = new Canvas(canvasBitmap);
////        canvas.scale(percent, percent);
////        view.draw(canvas);
////
////        return canvasBitmap;
////    }
//}