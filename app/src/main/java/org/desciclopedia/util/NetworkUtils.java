package org.desciclopedia.util;

import android.os.AsyncTask;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    /**
     * cria um processo para conexão com a internet
     * @param r
     */
    public static void runOnNetworkThread(Runnable r) {
        new Thread(r).start();
    }

    /**
     * Converte texto wikificado para página em html, onde o código interno será processado melhor
     *
     * @param wiki
     */
    public static String HTMLize(String wiki) {
        @Language("HTML") String page = "<!DOCTYPE html>" +
                "<html>" +
                    "<head>" +
                     //    Processor.css +
                    "</head>" +

                    "<body>" +
                        "<div id='body'>" +
                            wiki +
                        "</div>" +
                        Processor.js +
                    "</body>" +
                "</html>";

        return page;
    }
}
