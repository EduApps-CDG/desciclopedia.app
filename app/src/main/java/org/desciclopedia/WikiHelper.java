package org.desciclopedia;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.desciclopedia.util.Linux;
import org.intellij.lang.annotations.Language;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import okhttp3.Request;
import okhttp3.Response;

public class WikiHelper {

    /**
     * Não usado.
     *
     * @param page
     * @return
     */
//    public static String getContent(@Language("HTML") String page) {
//        //TODO: Desfazer a gambiarra.
//        String result = page.replaceFirst(".*(?=<div id=\"content\" class=\"mw-body\" role=\"main\">)","");
//        return result.split("<div id=\"mw-navigation\">")[0];
//    }

    /**
     * converte [[links]] em links internos (apenas texto). Ex:
     * https://desciclopedia.org/index.php?title=Desciclopédia&action=raw
     * é equivalente a [[Desciclopédia]]
     *
     * @param wikipage
     * @return
     */
    public static String internal(@Nullable String wikipage) {
        //previne erros (transforma null em "")
        if (wikipage == null) {
            wikipage = "Página_principal";
        }

        return Global.DOMAIN + "index.php?title=" + wikipage + "&action=raw";
    }

    public static String internalJS(@Nullable String wikipage) {
        //previne erros (transforma null em "")
        if (wikipage == null) {
            wikipage = "";
        }

        return Global.DOMAIN + "wiki/" + wikipage;
    }

    /**
     * Checa se um artigo está no cache
     *
     * @param wikipage
     * @return
     */
    public static boolean isCached(String wikipage) {
        File page = new File(Global.FILES + wikipage.replace(":","/") + ".html");

        return page.exists();
    }

    /**
     * baixa uma imagem e manda para o cache
     *
     * @param url
     * @return
     */
    public static Bitmap loadImage(String url) {
        try {
            //pega o nome da imagem com extensão
            String nome = url.split("/")[url.split("/").length - 1];

            //se o arquivo já tiver cache:
            if (new File(Global.FILES + "Arquivo/" + nome).exists()) {
                // apenas retorna o arquivo
                return Picasso.get().load(deUrlize("file:///" + Global.FILES + "Arquivo/" + nome)).get();
            } else { //se não...
                //baixa o arquivo no cache:
                Linux.curl(new String[]{
                        "-o " + Global.FILES + "Arquivo/" + deUrlizeCURL(nome)
                }, url);

                //retorna o arquivo:
                return Picasso.get().load("file:///" + Global.FILES + "Arquivo/" + deUrlize(nome)).get();
            }
        } catch (IOException e) {
            //caso algo aconteça
            e.printStackTrace();
            return null;
        }
    }

    /**
     * pega a url de uma imagem por meio de gambiarras,
     * na qual consiste em achar a tag <img> no código fonte
     * da Desciclopédia
     *
     * @param code
     * @return
     */
    public static String getImageUrlByGambiarra(String code) {
        try {
            //TODO: (Opcional) Desfazer a gambiarra.

            /**
             * procura o padrão de código de arquivos da Desciclopédia
             * na qual todas as paginas de prefixo "Arquivo" tem.
             */
            String result = code.split(Pattern.quote("<div class=\"fullMedia\">"))[1].replaceFirst(Pattern.quote("<a href=\"//"), "").split(Pattern.quote("\""))[0];

            //teste: imprime a url da imagem no console
            System.out.println(result);

            //retorna o url da imagem:
            return "https://" + result;
        } catch (ArrayIndexOutOfBoundsException e) {
            //caso aconteça um erro...
            System.out.println(code);
            e.printStackTrace();

            //retorna uma imagem especial (que eu fiz pro app)
            return "https://images.uncyc.org/pt/4/49/Error.png";
        }
    }

    /**
     * Decodifica a url. Ex: "%C3%A9" se torna "é" (algumas imagens não funcionam sem isso)
     *
     * @param url
     * @return
     */
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

    /**
     * Decodifica a url, mas de uma forma específica para o comando Linux.curl() (note os 4 "\")
     *
     * @param url
     * @return
     */
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
