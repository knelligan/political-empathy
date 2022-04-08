package io.politicalempathy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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

    /* SurfaceView for plotting */
    private SurfaceView surfaceView;

    /* SurfaceHolderfor plotting */
    private SurfaceHolder surfaceHolder;

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

        //update surface value
        surfaceView = findViewById(R.id.surfaceView);

        //get the holder for surface
        surfaceHolder = surfaceView.getHolder();


        //generate scores by accessing stored values
        scoreCalculations();

        //plot the value on the compass
        plotValue();

    }

    public void scoreCalculations(){
        //this needs to be updated/replaced with scores from db-------------------------------------------------------
        //calculate the average of the econ scores
        econScoreValue = -2;


        //calculate the average of the social scores------------------------------------
        socScoreValue = -3;

        //researched bitmap info here: https://itecnote.com/tecnote/android-getting-bitmap-from-custom-surfaceview/
        //create the bitmap used for plotting
        bitmap = Bitmap.createBitmap(surfaceView.getWidth(), surfaceView.getHeight(), Bitmap.Config.ARGB_8888);

        //researched canvas here: https://medium.com/over-engineering/getting-started-with-drawing-on-the-android-canvas-621cf512f4c7
        //create the canvas used for plotting
        Canvas canvas = new Canvas(bitmap);

        //captures strokes etc used in the drawing/painting
        Paint paint = new Paint();

        //set the color of the painted object
        paint.setColor(Color.BLACK);

        //flag to ensure your drawing has smooth edges
        paint.setAntiAlias(true);

        //set the drawing stroke/width
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(12);

        //generate center points on the canvas to start from
        Point plotPoint = translatePoint(econScoreValue, socScoreValue);

        //get translated point values
        int x = plotPoint.x;
        int y = plotPoint.y;

        //canvas starts at top left point in the map
        //draw point method researched here: https://www.tabnine.com/code/java/methods/android.graphics.Canvas/drawPoint
        canvas.drawPoint(x, y, paint);

    }

    public Point translatePoint(int x, int y){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int centerX = width/2;
        int centerY = height/2;

        return new Point(centerX + x, centerY + y);
    }

    public void plotValue(){

    }

}