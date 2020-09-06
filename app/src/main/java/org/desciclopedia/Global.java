package org.desciclopedia;

import android.content.SharedPreferences;
import android.graphics.Color;

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

    public static long[] NOTIFICATION_PATTERN = new long[] {
      // Vibrar, esperar
            100, 200,
            300, 400,
            500, 400,
            300, 200,
            400, 000
    };

    public static boolean ENABLE_LED = true;
    public static boolean ENABLE_VIBRATOR = true;

    public static final String NOTIFY = "desciclo_notify";
    public static String NOTIFY_NAME = "Notificações da Desciclopédia";
    public static String NOTIFY_DESCRIPT = "Notificações normais da Desciclopédia, por exemplo uma edição na sua pagina de Discussão";
    public static  final String I_NOTIFY = "desciclo_important";
    public static String I_NOTIFY_NAME = "Notificações Importantes da Desciclopédia";
    public static String I_NOTIFY_DESCRIPT = "Notificações na quais você tem o dever de ler, como um concurso ou ser banido.";
    public static int NOTIFY_LIGHT = Color.RED;

    public static class prefix {
        public static String H1 = "==";
        public static String H2 = "===";
        public static String H3 = "====";
        public static String BOLD = "'''";
        public static String ITALIC = "''";
    }
}
