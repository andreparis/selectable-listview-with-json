package com.example.andre.blue_gears;

/**
 * Created by Andre on 6/1/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Utils {

    private static int total;

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    private static void heapify(ArrayList<ItemListView> arr, int i)
    {
        int lft = i * 2;
        int rgt = lft + 1;
        int grt = i;

        if (lft <= total && arr.get(lft).getName().compareTo(arr.get(grt).getName()) > 0) grt = lft;
        if (rgt <= total && arr.get(rgt).getName().compareTo(arr.get(grt).getName()) > 0) grt = rgt;
        if (grt != i) {
            Collections.swap(arr, i, grt);
            heapify(arr, grt);
        }
    }

    public static void sort(ArrayList<ItemListView> arr)
    {
        total = arr.size() - 1;

        for (int i = total / 2; i >= 0; i--)
            heapify(arr, i);

        for (int i = total; i > 0; i--) {
            Collections.swap(arr, 0, i);
            total--;
            heapify(arr, 0);
        }
    }

    public static Bitmap convertByteToBitmap(byte[] imgIn) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(imgIn);
        Bitmap imgOut = BitmapFactory.decodeStream(imageStream);
        return imgOut;
    }

    public static byte[] convertBitMapToByte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy, HH:mm");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static boolean nonRepItem(ArrayList<ItemListView> list, ItemListView item) {
        boolean c = true;
        int i = 0;
        for (i = 0; i < list.size(); i++) {
            if (list.get(i).getName().compareTo(item.getName()) == 0 &&
                    list.get(i).getDescription().compareTo(item.getDescription()) == 0)
                c = false;
        }
        return c;
    }
}