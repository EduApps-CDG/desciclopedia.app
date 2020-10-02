package org.desciclopedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.desciclopedia.util.Linux;
import static org.desciclopedia.util.NetworkUtils.runOnNetworkThread;

public class WikiActivity extends Activity implements View.OnClickListener {
    public int quantidade_itens = 0;
    public WikiList wikiList = new WikiList();
    boolean status = false;
    ImageView HAMBURGUER_MENU;
    ImageView SETTINGS;
    DrawerArrowDrawable HAMBURGUER_IMAGE;
    DrawerLayout DRAWER;
    LinearLayout CONTENT;
    NavigationView NAVIGATION;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        setup();

        String texto = getIntent().getExtras().getString("page"); //codigo da pagina wiki
        String[] linhas = texto.split("\n"); //separa o texto em linhas

        wikificar(linhas);

        for (int x = 0; x < quantidade_itens;x++) {
            WikiItem y = wikiList.get(x);
            Log.i("UI Debug", "Item " + x + " do tipo \"" + y.getType() + "\"");

            if (y.getType() == "TextView") {
                CONTENT.addView((TextView) y.getView());
            } else if (y.getType() == "ImageView") {
                CONTENT.addView((ImageView) y.getView());
            }
        }
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            //ação de clique do botão para abrir a barra de navegção:
            case R.id.MENU_HAMBURGUER:
                DRAWER.openDrawer(GravityCompat.START);
                break;

            //ação de clique para o botão de configurações (dentro da navegação)
            case R.id.SETTINGS:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
        }
    }

    private Activity self() {
        return this;
    }

    /**
     * inicia as variáveis e configura os elementos
     */
    public void setup() {
        //inicia as variáveis
        HAMBURGUER_MENU = findViewById(R.id.MENU_HAMBURGUER);
        HAMBURGUER_IMAGE = new DrawerArrowDrawable(this);
        DRAWER = findViewById(R.id.DRAWER);
        CONTENT = findViewById(R.id.CONTENT);
        SETTINGS = findViewById(R.id.SETTINGS);

        //configura os elementos
        HAMBURGUER_MENU.setImageDrawable(HAMBURGUER_IMAGE);

        //adiciona eventos (toque, clique, etc)
        HAMBURGUER_MENU.setOnClickListener(this);
        SETTINGS.setOnClickListener(this);
    }

    /**
     * é aqui onde a mágica acontece, esta função transforma todos os textos recebidos em
     * elementos gráficos:
     *
     * @param linhas
     */
    public void wikificar(String[] linhas) {
        for (int x = 0; x < (linhas.length - 1); x++) {
            System.out.println("Linha " + x + ":\n\n" + linhas[x]);

            // === MINHA PICA ===
            if (linhas[x].startsWith("===") && linhas[x].endsWith("===")) {
                //cria um elemento de texto:
                TextView txt = new TextView(this);
                txt.setText(linhas[x].replaceAll("===","")); //apaga os ===
                txt.setTextSize(40); // muda o tamanho para 40

                //insere na tela:
                wikiList.add(quantidade_itens,new WikiItem("TextView",txt));
                quantidade_itens++;

                //== MINHA PICA ==
            } else if (linhas[x].startsWith("==") && linhas[x].endsWith("==")) {
                //cria um elemento de texto:
                TextView txt = new TextView(this);
                txt.setText(linhas[x].replaceAll("==","")); //apaga os ==
                txt.setTextSize(50); //muda o tamanho para 50

                //insere na tela:
                wikiList.add(quantidade_itens,new WikiItem("TextView",txt));
                quantidade_itens++;

                /**
                 * [[Arquivo:Minha pica.png]]
                 *
                 * @TODO: Resolver o problema (imagens não renderizando)
                 */
            } else if (linhas[x].startsWith("[[Arquivo:") && linhas[x].endsWith("]]")) {
                Log.i("DesBugger", linhas[x]);

                //pega os metadados da imagem (nome)
                final String[] metadados = linhas[x].replace("[[Arquivo:","").replace("]]","").split("\\|");
                final int y = quantidade_itens;

                //cria o elemento imagem
                final ImageView img = new ImageView(self());
                wikiList.add(y, new WikiItem("ImageView",img));

                quantidade_itens++;


                runOnNetworkThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("imagem " + metadados[0]);

                        //baixa a página da desciclopéda contendo a imagem (Arquivo)
                        String image_content = Linux.curl(null,"https://desciclopedia.org/wiki/Arquivo:" + metadados[0].replace(" ","_"));

                        //baixa a imagem por meio da página baixada anteriormente (pois precisamos de um link estático com apenas a imagem)
                        final Bitmap image = WikiHelper.loadImage(WikiHelper.getImageUrlByGambiarra(image_content));

                        //volta para o processo principal e mostra a imagem
                        runOnUiThread(new Runnable() {
                            @Override public void run() {
                                img.setImageBitmap(image);
                            }
                        });
                    }
                });
//
//                if (metadados[1] == "thumb" | metadados[1] == "miniaturadaimagem") {
//
//                }

                /**
                 * <!--Comentários-->
                 * @TODO: organizar melhor (não está funcionando direito)
                 */
            } else if (linhas[x].startsWith("<--")) {
                //procura pelo final do comentário e ignora-o
                for(int y = x;y < (linhas.length - 1);y++) {
                    if (linhas[y].endsWith("-->")) {
                        x = y;
                        y = linhas.length;
                    }
                }

                // texto normal (incluindo hiperlinks)
            } else {
                //cria o elemento:
                TextView txt = new TextView(this);
                txt.setText(linhas[x]);

                //adiciona a tela:
                wikiList.add(quantidade_itens,new WikiItem("TextView",txt));
                quantidade_itens++;
            }
        }
    }
}
