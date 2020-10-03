package org.desciclopedia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.desciclopedia.util.ImageUtils;
import org.desciclopedia.util.JSUtils;
import org.desciclopedia.util.Linux;
import org.desciclopedia.util.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

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

            if (y.getType() == "WebView") {
                CONTENT.addView((WebView) y.getView());
            } else if (y.getType() == "ImageView") {
                CONTENT.addView((ImageView) y.getView());
            } else if (y.getType() == "TextView") {
                CONTENT.addView((TextView) y.getView());
            } else if (y.getType() == "View") {
                CONTENT.addView(y.getView());
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
    @SuppressLint("JavascriptInterface")
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


                ArrayList<String> urls = new ArrayList<String>();
                final ArrayList<String> descs = new ArrayList<String>();

                //pega os metadados da imagem (nome)
                final String[] metadados = linhas[x].replace("[[Arquivo:","").replace("]]","").split("\\|");

                final int y = quantidade_itens;
                View v = getLayoutInflater().inflate(R.layout.image_slider, null);
                final ImageView limg = v.findViewById(R.id.IMG);
                ImageButton next = v.findViewById(R.id.IMG_NEXT);
                ImageButton back = v.findViewById(R.id.IMG_BACK);
                final TextView desc = v.findViewById(R.id.IMG_DESC);

                if (linhas[x+1].startsWith("[[Arquivo:")) {
                    for (int z = x; z < linhas.length;z++) {
                        final String[] metadados2 = linhas[z].replace("[[Arquivo:","").replace("]]","").split("\\|");
                        System.out.println("imagem: " + metadados2[0]);
                        urls.add(Global.DOMAIN + "wiki/Arquivo:" + metadados2[0]);

                        for (int xxx = 0; xxx < metadados2.length;xxx++) {
                            if (metadados2[xxx].contains(" ")&& xxx != 0) {
                                descs.add(metadados2[xxx]);
                            }
                        }

                        if (!linhas[z].startsWith("[[Arquivo:")) {
                            z = linhas.length + 1;
                        }
                    }

                } else {
                    urls.add(Global.DOMAIN + "wiki/Arquivo:" + metadados[0]);
                    ((ImageButton) v.findViewById(R.id.IMG_BACK)).setVisibility(View.GONE);
                    ((ImageButton) v.findViewById(R.id.IMG_NEXT)).setVisibility(View.GONE);
                }

                String[] fiu = new String[urls.size()];
                fiu = urls.toArray(fiu);
                final ImageUtils iu = new ImageUtils(fiu);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnNetworkThread(new Runnable() {
                            @Override
                            public void run() {
                                iu.next();
                                try {
                                    limg.setImageBitmap(iu.getActualImage());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                desc.setText(descs.get(iu.getX()));
                            }
                        });
                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnNetworkThread(new Runnable() {
                            @Override
                            public void run() {
                                iu.previous();
                                try {
                                    limg.setImageBitmap(iu.getActualImage());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                desc.setText(descs.get(iu.getX()));
                            }
                        });
                    }
                });

                runOnNetworkThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            limg.setImageBitmap(iu.getImage(0));
                            desc.setText(descs.get(iu.getX()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                wikiList.add(y, new WikiItem("View", v));

                //cria o elemento imagem
//                final ImageView img = limg;

                quantidade_itens++;

//                runOnNetworkThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("imagem " + metadados[0]);
//
//                        //baixa a página da desciclopéda contendo a imagem (Arquivo)
//                        String image_content = Linux.curl(null,"https://desciclopedia.org/wiki/Arquivo:" + metadados[0].replace(" ","_"));
//
//                        //baixa a imagem por meio da página baixada anteriormente (pois precisamos de um link estático com apenas a imagem)
//                        final Bitmap image = WikiHelper.loadImage(WikiHelper.getImageUrlByGambiarra(image_content));
//
//                        //volta para o processo principal e mostra a imagem
//                        runOnUiThread(new Runnable() {
//                            @Override public void run() {
//                                img.setImageBitmap(image);
//                            }
//                        });
//                    }
//                });
//
//                if (metadados[1] == "thumb" | metadados[1] == "miniaturadaimagem") {
//
//                }
                // texto normal (incluindo hiperlinks)
                // {{porra}}
            } else if (linhas[x].startsWith("{{") && linhas[x].endsWith("}}")) {
//                runOnNetworkThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
                //cria o elemento:
                String pre = linhas[x].replaceFirst("\\{\\{","").replaceFirst("(?s)(.*)\\}\\}","");
                String result = Linux.curl(null,WikiHelper.internal(pre));
                WebView txt = new WebView(this);
                txt.getSettings().setJavaScriptEnabled(true);
                txt.addJavascriptInterface(new JSUtils(),"JSUtils");

                txt.loadData(NetworkUtils.HTMLize(result),"text/html","utf-8");

                //adiciona a tela:
                wikiList.add(quantidade_itens,new WikiItem("WebView",txt));
                quantidade_itens++;
            } else {
                //cria o elemento:
                WebView txt = new WebView(this);
                txt.getSettings().setJavaScriptEnabled(true);
                txt.addJavascriptInterface(new JSUtils(),"JSUtils");

                txt.loadData(NetworkUtils.HTMLize(linhas[x]),"text/html","utf-8");

                //adiciona a tela:
                wikiList.add(quantidade_itens,new WikiItem("WebView",txt));
                quantidade_itens++;
            }
        }
    }
}
