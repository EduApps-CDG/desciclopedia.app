package org.desciclopedia;

import android.view.View;

public class SettingsItem {
    private String title;
    private String description;
    private View actor;
    private String type;

    public SettingsItem(String title, String description,String type, View actor) {
        this.title = title;
        this.description = description;
        this.actor = actor;
        this.type = type;
    }

    public SettingsItem(String title,String type, View actor) {
        this.title = title;
        this.description = "";
        this.actor = actor;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public View getActor() {
        return actor;
    }

    public void setActor(View actor) {
        this.actor = actor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
