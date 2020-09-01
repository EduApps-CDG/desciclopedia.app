package org.desciclopedia;

import android.content.SharedPreferences;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class Global {
    public static final int BOOT_LOGO_TIME = 3000;
    public static final String DOMAIN = "https://desciclopedia.org/";
    public static String CONTENT = "NULL";
    public static String FILES;
    public static SharedPreferences CACHE;
    public static ArrayList<String> CURL_PARAMS = new ArrayList<String>();
    public static final OkHttpClient CLIENT = new OkHttpClient();

    public static class prefix {
        public static String H1 = "==";
    }
}
