package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    /* Textview for the economic score */
    private TextView econScore;

    /* Textview for the social score */
    private TextView socScore;

    /* ImageView for the political compass image used */
    private ImageView compass;

//    /* SurfaceView for plotting */
//    private SurfaceView surfaceView;
//
//    /* SurfaceHolderfor plotting */
//    private SurfaceHolder surfaceHolder;

    /* Economic score value (x val) */
    private int econScoreValue;

    /* Social score value (y val) */
    private int socScoreValue;

    /* Bitmap object used for drawing */
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        //researched plotting values here: https://programmerworld.co/android/how-to-plot-arrays-x-and-y-coordinates-on-axes-in-your-android-app-without-using-dependencies-or-libraries/

        //TextView value updates
        econScore = findViewById(R.id.economic_bias_score);
        socScore = findViewById(R.id.social_bias_score);

        //update image value
        compass = findViewById(R.id.politicalchart);
        
        //generate scores by accessing stored values
        scoreCalculations();

        //plot the value on the compass
        plotValue();

    }

    public void scoreCalculations() {
        //this needs to be updated/replaced with scores from db-------------------------------------------------------
        //calculate the average of the econ scores
        //x = econ
        econScoreValue = -3;

        //calculate the average of the social scores------------------------------------
        //y = soc
        socScoreValue = 7;

        //researched bitmap info here: https://itecnote.com/tecnote/android-getting-bitmap-from-custom-surfaceview/
        //create the bitmap used for plotting
        //bitmap = Bitmap.createBitmap(surfaceView.getWidth(), surfaceView.getHeight(), Bitmap.Config.ARGB_8888);
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


        //this is to test the drawing---------------------------------------------------------------------------------------------------
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

        //define rectangles in compass
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

        //paint.setColor(Color.WHITE);
        //draw x axis
        canvas.drawLine(0, 500, 1000, 500, paint);

        //draw y axis
        canvas.drawLine(500, 0, 500, 1000, paint);

        //draw border of compass
        //canvas.drawRect(0, 0, 1000, 1000, paint);

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
        //correct for 2, 5

        if (x < 0) {

            x = 500 - (Math.abs(x) * 50);

        } else {
            x = 500 + (Math.abs(x) * 50);
        }

        if (y < 0) {
            System.out.println("y is less than 0");
            y = 500 + (Math.abs(y) * 50);
        } else {
            y = 500 - (Math.abs(y) * 50);
        }
        System.out.println("x: " + x);
        System.out.println("y: " + y);


        //get the width and height of the bitmap to find the center points
        int width = 1000;
        int height = 1000;

//        //divide the width/heigth by 2 to find the midpoint/center of each axis
//        //centerX and centerY are the origin point
//        int centerX = width/2;
//        System.out.println(centerX);
//        int centerY = height/2;
//System.out.println(centerY);
//        x = centerX + x;
//        System.out.println(x);
//        y = centerY + y;
//        System.out.println(y);


        //these values may still be inaccurate, might need to create another method to calculate x/y as they fit into 300dp map need to see how they move-------------------

        //return a point value that represents the change in x and change in y
        //from the origin point
        return new Point(x, y);
    }


    public void plotValue() {

    }

}