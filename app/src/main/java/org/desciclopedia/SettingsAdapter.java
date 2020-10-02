package org.desciclopedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Adaptador de configurações dinamicas (veja o arquivo JSON na pasta assets)
 */
public class SettingsAdapter extends ArrayAdapter<SettingsItem> {
    public SettingsAdapter(Context context, ArrayList<SettingsItem> arrayList) {
        super(context, 0, arrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        int layout = 0;
        SettingsItem currentItem = getItem(position);
        System.out.println(currentItem.getType());
        if (currentItem.getType().equals("login")) {
            layout = R.layout.setting;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        layout, parent, false);
            }
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Deixarei o login pra depois",Toast.LENGTH_LONG).show();
                }
            });
        } else {
            layout = R.layout.setting;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        layout, parent, false);
            }
        }

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    layout, parent, false);
        }

        TextView tit = (TextView)listItemView.findViewById(R.id.TITLE);
        tit.setText(currentItem.getTitle());

        TextView desc = (TextView)listItemView.findViewById(R.id.DESCRIPTION);
        desc.setText(currentItem.getDescription());

        LinearLayout obj = listItemView.findViewById(R.id.DILDO);
        obj.removeAllViews();
        //obj.addView(currentItem.getActor());

        return listItemView;
    }
}
