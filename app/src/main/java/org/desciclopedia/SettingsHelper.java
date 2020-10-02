package org.desciclopedia;

import android.app.Activity;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe de ajuda para as configurações dinâmicas
 */
public class SettingsHelper {
    /**
     * pega o JSON das configurações
     *
     * @param act
     * @return
     */
    @Language("JSON") public static String getSettings(Activity act) {
        BufferedReader reader = null;
        String setting = "";
        try {
            reader = new BufferedReader(
                    new InputStreamReader(act.getAssets().open("setting_tree.json"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                setting += mLine + "\n";
            }
        } catch (IOException e) {
            //log the exception
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Setting: \n" + setting);
        return setting;
    }
}
