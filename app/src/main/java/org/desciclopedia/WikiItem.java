package org.desciclopedia;

import android.view.View;

/**
 * Não lembro o que essa classe faz. mas são Getters e Setters
 */
public class WikiItem {
    private String type;
    private View view;

    public WikiItem(String type,View v) {
        this.type = type;
        this.view = v;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
