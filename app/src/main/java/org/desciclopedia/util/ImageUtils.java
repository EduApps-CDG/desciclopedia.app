package org.desciclopedia.util;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ImageUtils {
    private String[] urls;
    private int x = 0;

    public ImageUtils(String[] urls) {
        this.urls = urls;
    }

    public String[] getUrls() {
        return urls;
    }

    public Bitmap getImage(int index) throws IOException {
        x = index;
        return Picasso.get().load(urls[index]).get();
    }

    public Bitmap getActualImage() throws IOException {
        return Picasso.get().load(urls[x]).get();
    }

    public void next() {x++;}
    public void previous() {x--;}

    public int getX() {
        return x;
    }

    public int getLength() {
        return urls.length;
    }
}
