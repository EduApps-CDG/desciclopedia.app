package org.desciclopedia;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

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

    public static String internal(@Nullable String wikipage) {
        //https://desciclopedia.org/index.php?title=Desciclop%C3%A9dia&action=raw
        if (wikipage == null) {
            wikipage = "";
        }
        return Global.DOMAIN + "index.php?title=" + wikipage + "&feed=atom&action=raw";
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
            return Picasso.get().load(deUrlize("file:///" + Global.FILES + "Arquivo/" + nome)).get();
        } else {
            Linux.curl(new String[]{
                    "-o " + Global.FILES + "Arquivo/" + deUrlizeCURL(nome)
            }, url);
            return Picasso.get().load("file:///" + Global.FILES + "Arquivo/" + deUrlize(nome)).get();
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

    public static String deUrlize(String url) {
        return url.replaceAll("%C3%A9","é")
                  .replaceAll("%C3%A1","á")
                  .replaceAll("%C3%AD","í")
                  .replaceAll("%C3%B3","ó")
                  .replaceAll("%C3%BA","ú")
                  .replaceAll("%C3%A3","ã")
                  .replaceAll("%C3%B5","õ")
                  .replaceAll("%C3%A0","à")
                  .replaceAll("%C3%A8","è")
                  .replaceAll("%C3%AC","ì")
                  .replaceAll("%C3%B2","ò")
                  .replaceAll("%C3%B9","ù")
                  .replaceAll("%C3%A2","â")
                  .replaceAll("%C3%AA","ê")
                  .replaceAll("%C3%AE","î")
                  .replaceAll("%C3%B4","ô")
                  .replaceAll("%C3%BB","û")
                  .replaceAll("_"," ");
    }

    public static String deUrlizeCURL(String url) {
        return url.replaceAll("%C3%A9","\\\\é")
                .replaceAll("%C3%A1","\\\\á")
                .replaceAll("%C3%AD","\\\\í")
                .replaceAll("%C3%B3","\\\\ó")
                .replaceAll("%C3%BA","\\\\ú")
                .replaceAll("%C3%A3","\\\\ã")
                .replaceAll("%C3%B5","\\\\õ")
                .replaceAll("%C3%A0","\\\\à")
                .replaceAll("%C3%A8","\\\\è")
                .replaceAll("%C3%AC","\\\\ì")
                .replaceAll("%C3%B2","\\\\ò")
                .replaceAll("%C3%B9","\\\\ù")
                .replaceAll("%C3%A2","\\\\â")
                .replaceAll("%C3%AA","\\\\ê")
                .replaceAll("%C3%AE","\\\\î")
                .replaceAll("%C3%B4","\\\\ô")
                .replaceAll("%C3%BB","\\\\û")
                .replaceAll("_","\\\\ ");
    }
}
