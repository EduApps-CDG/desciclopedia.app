package org.desciclopedia;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    public int profundity = 0;
    ImageView HAMBURGUER_MENU;
    ImageView ACTION;
    TextView TITLE;
    JSONObject json;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        HAMBURGUER_MENU = findViewById(R.id.MENU_HAMBURGUER);
        ACTION = findViewById(R.id.ACTION_BUTTON);
        TITLE = findViewById(R.id.TITLE);
        try {
            json = new JSONObject(SettingsHelper.getSettings(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TITLE.setText("Configurações");
        HAMBURGUER_MENU.setImageResource(R.drawable.ic_action_return);
        ACTION.setImageResource(R.drawable.ic_action_search);
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

    public void goBack() {
        if (profundity <= 0) {
            this.onDestroy();
        } else {
            //TODO: Go back to the previous section
            profundity--;
        }
    }

    public void settingSearch() {

    }
}