package org.desciclopedia;

import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.ArrayList;

/**
 * Variáveis Globais (usadas por toda a parte do app).
 * Estas variáveis é muito importante tê-las em uma classe específica, pois pode se tornar uma configuração.
 */
public class Global {
    //o tempo em que a Logo da Desciclopédia aparecera na tela (milis)
    public static final int BOOT_LOGO_TIME = 3000;

    //o domínio da Desciclopédia
    public static final String DOMAIN = "https://desciclopedia.org/";

    //o conteúdo do artigo
    public static String CONTENT = "NULL";

    //o local do cache (será setado depois)
    public static String FILES;

    //o cache em si
    public static SharedPreferences CACHE;

    //parâmetros do comando Linux.curl (feito para cache de artigos)
    public static ArrayList<String> CURL_PARAMS = new ArrayList<String>();

    //Padrão de vibração para a notificação
    public static long[] NOTIFICATION_PATTERN = new long[] {
      // Vibrar, esperar
            100, 200,
            300, 400,
            500, 400,
            300, 200,
            400, 000
    };

    //ativador de LED na notificação
    public static boolean ENABLE_LED = true;
    //ativador do Vibrador
    public static boolean ENABLE_VIBRATOR = true;

    //parâmetros da notificação (para as configurações do android Oreo+)
    public static final String NOTIFY = "desciclo_notify"; //canal de notificação
    public static String NOTIFY_NAME = "Notificações da Desciclopédia"; //nome do canal
    public static String NOTIFY_DESCRIPT = "Notificações normais da Desciclopédia, por exemplo uma edição na sua pagina de Discussão"; //descrição do canal
    public static  final String I_NOTIFY = "desciclo_important"; //canal de notificação importante
    public static String I_NOTIFY_NAME = "Notificações Importantes da Desciclopédia"; //nome
    public static String I_NOTIFY_DESCRIPT = "Notificações na quais você tem o dever de ler, como um concurso ou ser banido."; //descrição

    /**
     * Cor da LED de notificação (se suportado)
     * Alguns dispositivos possuem LED, mas com apenas uma cor (o caso do meu)
     * e outros nem sequer possuem LED
     */
    public static int NOTIFY_LIGHT = Color.RED;

    //padrões de um artigo:
    public static class prefix {
        public static String H1 = "==";
        public static String H2 = "===";
        public static String H3 = "====";
        public static String BOLD = "'''";
        public static String ITALIC = "''";
    }
}
