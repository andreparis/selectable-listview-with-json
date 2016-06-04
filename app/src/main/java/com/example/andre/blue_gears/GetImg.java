package com.example.andre.blue_gears;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Andre on 6/2/2016.
 */
public class GetImg extends AsyncTask<String, Void, Bitmap> {

    private ImageView bmImage;
    private final int stub_id = R.drawable.no_image;
    private ItemListView item;

    public GetImg(ImageView bmImage, ItemListView item) {
        this.bmImage = bmImage;
        this.item = item;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (result == null)
            bmImage.setImageResource(stub_id);
        else {
            bmImage.setImageBitmap(result);
            item.setImg(Utils.convertBitMapToByte(result));
        }
    }
}
