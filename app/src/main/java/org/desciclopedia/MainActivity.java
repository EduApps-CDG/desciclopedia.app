package org.desciclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.desciclopedia.util.Linux;
import static org.desciclopedia.util.NetworkUtils.runOnNetworkThread;

import java.io.File;

/**
 * Primeira tela do app (logo da Desciclopédia)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //seta o layout (tela) para a splashscreen
        setContentView(R.layout.activity_main);

        //funções definidas por mim:
        checaPrimeiraVez();
        criaPastas();
        carregaPaginaInicial();
    }

    private Activity self() {
        return this;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    /**
     *  Cria pastas dentro do CACHE (ou data/data, se preferir) caso
     *  elas não existam
     */
    public void criaPastas() {
        new File(Global.FILES + "../cache").mkdir();
        new File(Global.FILES + "Arquivo").mkdir();
        new File(Global.FILES + "Desciclopédia").mkdir();
        new File(Global.FILES + "Forum").mkdir();
        new File(Global.FILES + "Regra").mkdir();
        new File(Global.FILES + "Desnotícias").mkdir();
        new File(Global.FILES + "Predefinição").mkdir();
        new File(Global.FILES + "Especial").mkdir();
    }

    /**
     * checa se é a primeira vez em que o app é iniciado
     *
     * @TODO: completar.
     */
    public void checaPrimeiraVez() {
        System.out.println("MainActivity.checaPrimeiraVez() iniciado");

        Global.BG_COLOR = "#CCCCCC";
    }

    /**
     * carrega a página inicial da desciclopédia e abre ela.
     *
     * @TODO: trocar o artigo [[Sexo nasal]] por [[Página Principal]] (mas só depois de terminar o app)
     */
    public void carregaPaginaInicial() {
        runOnNetworkThread(new Runnable() {
            @Override
            public void run() {
                Global.CONTENT = Linux.curl(null,WikiHelper.internal("Sexo_nasal"));

                System.out.println(Global.CONTENT);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(self(), WikiActivity.class);
                        i.putExtra("page",Global.CONTENT);
                        startActivity(i);
                    }
                });
            }
        });
    }
}