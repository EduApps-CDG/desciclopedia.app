package org.desciclopedia;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.intellij.lang.annotations.Language;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WikiHelper {


    public static String getContent(@Language("HTML") String page) {
        //TODO: Desfazer a gambiarra.
        String result = page.replaceFirst(".*(?=<div id=\"content\" class=\"mw-body\" role=\"main\">)","");
        return result.split("<div id=\"mw-navigation\">")[0];
    }

    public static String internal(String wikipage) {
        return Global.DOMAIN + "wiki/" + wikipage;
    }

    public static boolean isCached(String wikipage) {
        File page = new File(Global.FILES + wikipage.replace(":","/") + ".html");

        return page.exists();
    }

    public static String load(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = Global.CLIENT.newCall(request).execute();
            while (!response.isSuccessful()) {
                // O NADA TB;
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return Arrays.toString(e.getStackTrace());
        }
    }

    public static Bitmap loadImage(String url) {
        try {
        String nome = url.split("/")[url.split("/").length - 1];
        if (new File(Global.FILES + "Arquivo/" + nome).exists()) {
            return Picasso.get().load("file:///" + Global.FILES + "Arquivo/" + nome).get();
        } else {
            Linux.curl(new String[]{"-o " + Global.FILES + "Arquivo/" + nome}, url);
                return Picasso.get().load("file:///" + Global.FILES + "Arquivo/" + nome).get();
        }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getImageUrlByGambiarra(String code) {
        try {
            //TODO: Desfazer a gambiarra.
            String result = code.split(Pattern.quote("<div class=\"fullMedia\">"))[1].replaceFirst(Pattern.quote("<a href=\"//"), "").split(Pattern.quote("\""))[0];
            System.out.println(result);
            return "https://" + result;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(code);
            e.printStackTrace();
            return "http://images.uncyc.org/pt/4/49/Error.png";
        }
    }
}
