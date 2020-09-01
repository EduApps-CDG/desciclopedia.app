package org.desciclopedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.File;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Global.CACHE = getSharedPreferences("org.desciclopedia.settings",MODE_PRIVATE);
        Global.FILES = getFilesDir() + "/";

        if (Global.CACHE.getBoolean("do_cache",true)) {
            Global.CURL_PARAMS.add("-o " + Global.FILES + "{{wikipage}}");
            System.out.println(Global.CACHE.getBoolean("do_cache",true));
        }

        criaPastas();

        new Thread() {
            @Override
            public void run() {
                Global.CONTENT = Linux.curl(null,"https://desciclopedia.org/index.php?title=Desciclop%C3%A9dia&action=raw");

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
        }.start();
    }

    private Activity self() {
        return this;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    public void criaPastas() {
        new File(Global.FILES + "Arquivo").mkdir();
        new File(Global.FILES + "Desciclopédia").mkdir();
        new File(Global.FILES + "Forum").mkdir();
        new File(Global.FILES + "Regra").mkdir();
        new File(Global.FILES + "Desnotícias").mkdir();
    }
}