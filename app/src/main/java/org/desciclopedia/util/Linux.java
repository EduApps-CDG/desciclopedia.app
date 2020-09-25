package org.desciclopedia.util;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * Essa classe eu (Eduardo) peguei emprestada de outro projeto meu, ela
 * serve pra executar alguns comandos Linux integrados no Android
 */
public class Linux {
    /**
     * Essa função executa o curl (a base para a conexão do app)
     * O curl faz um "download" da pagina web na ram (no nosso caso o conteúdo do artigo)
     *
     * @param params
     * @param url
     * @return
     */
    public static String curl(@Nullable String[] params, String url) {
        try {
            if (params == null) {
                params = new String[] {
                        ""
                };
            }
            String args = TextUtils.join(" ", params);
            String fullcommand = "";
            fullcommand = "curl " + args + " " + url + "";
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(fullcommand);
            System.out.println("Run: " + fullcommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String response = null;
            String output = "";
            for (int x = 0; (response = stdInput.readLine()) != null; x++) {
                output += response + "\n";
            }
            p.waitFor();
            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * O wget também faz o download de uma pagina, mas tem pequenas diferenças (nas quais eu não me lembro)
     *
     * @param params
     * @param url
     * @return
     */
    public static String wget(@Nullable String[] params, String url) {
        try {
            String args = TextUtils.join(" ", params);
            String fullcommand = "";
            fullcommand = "wget " + args + " \"" + url + "\"";
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(fullcommand);
            System.out.println("Run: " + fullcommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String response = null;
            String output = "";
            for (int x = 0; (response = stdInput.readLine()) != null; x++) {
                output += response + "\n";
            }
            p.waitFor();
            return output;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        } catch (IOException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
}
