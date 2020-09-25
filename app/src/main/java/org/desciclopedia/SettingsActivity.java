package org.desciclopedia;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Atividade de Configurações
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    public int profundity = 0;
    ImageView HAMBURGUER_MENU;
    ImageView ACTION;
    TextView TITLE;
    ListView LIST;
    JSONObject json;
    ArrayList<SettingsItem> LIST_VALUES = new ArrayList<SettingsItem>();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        postSettings();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.MENU_HAMBURGUER:
                goBack();
                break;

            case R.id.ACTION_BUTTON:
                settingSearch();
                break;
        }
    }

    /**
     * volta um nivel de configuração
     */
    public void goBack() {
        if (profundity <= 0) {
            this.onDestroy();
        } else {
            //TODO: Go back to the previous section
            profundity--;
        }
    }

    /**
     * função para uma futura barra de pesquisa
     */
    public void settingSearch() {

    }

    /**
     * cria dinamicamente uma lista com todas as configurações
     */
    public void postSettings() {
        try {
            json = new JSONObject(SettingsHelper.getSettings(this)).getJSONObject("Configurações");
            ImageView img = new ImageView(this);
            System.out.println("json lenght" + json.length());

            for (int x = 1; x < (json.length() - 1);x++) {

                String js_tits = json.names().getString(x);
                JSONObject js_obj = json.getJSONObject(js_tits);
                String js_desc = "";
                try {
                    js_desc = js_obj.getString("description");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String js_type = js_obj.getString("type");

                if (js_type.equals("login")) {
                    img.setImageResource(R.drawable.baseline_account_circle_black_48);

                }

                LIST_VALUES.add(new SettingsItem(js_tits,js_desc,js_type,img));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * inicia as variáveis
     */
    public void setup() {
        HAMBURGUER_MENU = findViewById(R.id.MENU_HAMBURGUER);
        ACTION = findViewById(R.id.ACTION_BUTTON);
        TITLE = findViewById(R.id.TITLE);
        LIST = findViewById(R.id.SETTING_LIST);

        TITLE.setText("Configurações");
        HAMBURGUER_MENU.setImageResource(R.drawable.ic_action_return);
        ACTION.setImageResource(R.drawable.ic_action_search);

        LIST.setAdapter(new SettingsAdapter(this,LIST_VALUES));
    }
}