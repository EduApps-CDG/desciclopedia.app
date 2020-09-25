package org.desciclopedia.util;

import android.os.Handler;

/**
 * Utilitário para viágem temporal
 */
public class TimeUtils {
    /**
     * Viaja para o futuro
     *
     * @param function o que você quer fazer quando chegar lá
     * @param millis quanto tempo você quer ir para o futuro (tempo atual + millis)
     */
    public static void wait(Runnable function, int millis) {
        Handler h = new Handler();
        h.postDelayed(function,millis);
    }

    /**
     * modifica um texto de um banco de dados para retornar o ano da data
     *
     * @param SQL
     * @return
     */
    public static int getYearBySQL(String SQL) {
        //eu ia fazer de outro jeito:
        //bota um if
        //if menor que tanto, divide só a data
        //se for maior divide a data e a hora blz

        //formato de data: 0123-45-67 (10 caracteres)
        String[] sqldividido = SQL.split("-");
        // divide em 3; ANO = 0, MÊS = 1 e DIA = 2

        return Integer.parseInt(sqldividido[0]);
    }

    /**
     * modifica um texto de um banco de dados para retornar o mês da data
     *
     * @param SQL
     * @return
     */
    public static int getMonthBySQL(String SQL) {
        //eu ia fazer de outro jeito:
        String[] sqldividido = SQL.split("-");
        // divide em 3; ANO = 0, MÊS = 1 e DIA = 2

        return Integer.parseInt(sqldividido[1]);
    }

    /**
     * modifica um texto de um banco de dados para retornar o dia da data
     *
     * @param SQL
     * @return
     */
    public static int getDayBySQL(String SQL) {
        //eu ia fazer de outro jeito:
        String[] sqldividido = SQL.split("-");
        // divide em 3; ANO = 0, MÊS = 1 e DIA = 2
        if (sqldividido[2].contains(" ") /*Separador de horas*/) {
            String[] sqldivididodividido = sqldividido[2].split(" ");
            return Integer.parseInt(sqldivididodividido[0]);
        } else {
            return Integer.parseInt(sqldividido[2]);
        }
    }

    /**
     * modifica um texto de um banco de dados para retornar a hora da data
     *
     * @param SQL
     * @return
     */
    public static int getHoursBySQL(String SQL) {
        //eu ia fazer de outro jeito:
        String[] sqldividido = SQL.split("-");
        // divide em 3; ANO = 0, MÊS = 1 e DIA = 2
        if (sqldividido[2].contains(" ") /*Separador de horas*/) {
            String[] dia = sqldividido[2].split(" ");
            String[] tempo = dia[1 /*Só no chute*/].split(":");
            /**
             * tempo[0] = Hora
             * tempo[1] = Minuto
             * tempo[2] = Segundo
             */
            return Integer.parseInt(tempo[0]);
        } else {
            return 00;
        }
    }

    /**
     * modifica um texto de um banco de dados para retornar o minuto da data
     *
     * @param SQL
     * @return
     */
    public static int getMinutesBySQL(String SQL) {
        //eu ia fazer de outro jeito:
        String[] sqldividido = SQL.split("-");
        // divide em 3; ANO = 0, MÊS = 1 e DIA = 2
        if (sqldividido[2].contains(" ") /*Separador de horas*/) {
            String[] dia = sqldividido[2].split(" ");
            String[] tempo = dia[1 /*Só no chute*/].split(":");
            /**
             * tempo[0] = Hora
             * tempo[1] = Minuto
             * tempo[2] = Segundo
             */
            return Integer.parseInt(tempo[1]);
        } else {
            return 00;
        }
    }

    /**
     * modifica um texto de um banco de dados para retornar os segundos da data
     *
     * @param SQL
     * @return
     */
    public static int getSecondsBySQL(String SQL) {
        //eu ia fazer de outro jeito:
        String[] sqldividido = SQL.split("-");
        // divide em 3; ANO = 0, MÊS = 1 e DIA = 2
        if (sqldividido[2].contains(" ") /*Separador de horas*/) {
            String[] dia = sqldividido[2].split(" ");
            String[] tempo = dia[1 /*Só no chute*/].split(":");
            /**
             * tempo[0] = Hora
             * tempo[1] = Minuto
             * tempo[2] = Segundo
             */
            return Integer.parseInt(tempo[2]);
        } else {
            return 00;
        }
    }
}
