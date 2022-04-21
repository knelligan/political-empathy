package io.politicalempathy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class Share {
    private final Context context;

    public Share(Context context) {
        this.context = context;
    }
    //researched this here:https://stackoverflow.com/questions/30196965/how-to-take-a-screenshot-of-a-current-activity-and-then-share-it
    //attempted to implement this method but it would require additional editing and research

    public void shareIntent(Bitmap bitmap, String msg, String name) {
        Uri uri = screenshotForShare(bitmap, name);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("*/*");
        context.startActivity(Intent.createChooser(intent, "Share Via").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    // Retrieving the url to share
    private Uri screenshotForShare(Bitmap bitmap, String name) {
        File imagefolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, name+".png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(context, "io.politicalempathy.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(context, "Share failed " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }
}
