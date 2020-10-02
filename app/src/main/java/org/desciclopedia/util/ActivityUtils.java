package org.desciclopedia.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Utilitários para Activitys no Android
 */
public class ActivityUtils {
    /**
     * Inicia uma Activity (reduz o numero de linhas apenas)
     * @param act
     * @param next
     */
    public static void startActivity(Activity act, Class next) {
        Intent i = new Intent(act,next);
        act.startActivity(i);
    }

    /**
     * Caso o Android seja novo (Marshmallow+), o android irá
     * pedir permissão em uma caixa de dialogo, porém se você
     * executar esta função em um android velho, o app irá crashar.
     * Esta função resolve o problema
     *
     * @param act
     * @param permission
     */
    public static void requestPermission(Activity act, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //se o android for igual ou maior que Marshmallow
            act.requestPermissions(new String[]{permission}, 1);
        } else {
            System.out.println("não é necessário pedir permissão em Androids anteriores a 6.0");
        }
    }

    /**
     * Caso o Android seja novo (Marshmallow+), o android irá
     * pedir permissão em uma caixa de dialogo, porém se você
     * executar esta função em um android velho, o app irá crashar.
     * Esta função resolve o problema
     *
     * @param act
     * @param permission
     */
    public static void requestPermission(Activity act, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //se o android for igual ou maior que Marshmallow
            act.requestPermissions(permissions, 1);
        } else {
            System.out.println("não é necessário pedir permissões em Androids anteriores a 6.0");
        }
    }
}
