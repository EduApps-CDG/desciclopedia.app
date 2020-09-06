package org.desciclopedia;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class WikiActivity extends Activity implements View.OnClickListener {
    public int quantidade_itens = 0;
    public WikiList wikiList = new WikiList();
    boolean status = false;
    ImageView HAMBURGUER_MENU;
    ImageView SETTINGS;
    DrawerArrowDrawable HAMBURGUER_IMAGE;
    DrawerLayout DRAWER;
    LinearLayout CONTENT;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        HAMBURGUER_MENU = findViewById(R.id.MENU_HAMBURGUER);
        HAMBURGUER_IMAGE = new DrawerArrowDrawable(this);
        DRAWER = findViewById(R.id.DRAWER);
        CONTENT = findViewById(R.id.CONTENT);
        SETTINGS = findViewById(R.id.SETTINGS);

        HAMBURGUER_MENU.setImageDrawable(HAMBURGUER_IMAGE);
        HAMBURGUER_MENU.setOnClickListener(this);

        String texto = getIntent().getExtras().getString("page");
        String[] linhas = texto.split("\n");

        for (int x = 0; x < (linhas.length - 1); x++) {
            System.out.println("Linha " + x + ":\n\n" + linhas[x]);
            if (linhas[x].startsWith("===") && linhas[x].endsWith("===")) {
                TextView txt = new TextView(this);
                txt.setText(linhas[x].replaceAll("===",""));
                txt.setTextSize(40);
                wikiList.add(quantidade_itens,new WikiItem("TextView",txt));
                quantidade_itens++;

            } else if (linhas[x].startsWith("==") && linhas[x].endsWith("==")) {
                TextView txt = new TextView(this);
                txt.setText(linhas[x].replaceAll("==",""));
                txt.setTextSize(50);
                wikiList.add(quantidade_itens,new WikiItem("TextView",txt));
                quantidade_itens++;

            } else if (linhas[x].startsWith("[[Arquivo:") && linhas[x].endsWith("]]")) {
                Log.i("DesBugger", linhas[x]);
                //http://images.uncyc.org/pt/thumb/2/20/Homo_Erectus_Maximus.png/331px-Homo_Erectus_Maximus.png
                final String[] metadados = linhas[x].replace("[[Arquivo:","").replace("]]","").split("\\|");
                final int y = quantidade_itens;
                final ImageView img = new ImageView(self());
                wikiList.add(y, new WikiItem("ImageView",img));
                quantidade_itens++;
                new Thread() {
                    @Override
                    public void run() {
                        System.out.println("imagem " + metadados[0]);
                        String image_content = Linux.curl(null,"https://desciclopedia.org/wiki/Arquivo:" + metadados[0].replace(" ","_"));

                        final Bitmap image = WikiHelper.loadImage(WikiHelper.getImageUrlByGambiarra(image_content));

                      runOnUiThread(new Runnable() {
                          @Override public void run() {
                              img.setImageBitmap(image);
                          }
                      });
                    }
                }.start();
//
//                if (metadados[1] == "thumb" | metadados[1] == "miniaturadaimagem") {
//
//                }
            } else {
                TextView txt = new TextView(this);
                txt.setText(linhas[x]);
                wikiList.add(quantidade_itens,new WikiItem("TextView",txt));
                quantidade_itens++;
            }
        }

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
            case R.id.MENU_HAMBURGUER:
                DRAWER.openDrawer(GravityCompat.START);
                break;
            case R.id.SETTINGS:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
        }
    }

    private Activity self() {
        return this;
    }
}
