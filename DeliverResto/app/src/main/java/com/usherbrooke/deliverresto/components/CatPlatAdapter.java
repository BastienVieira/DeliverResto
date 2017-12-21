package com.usherbrooke.deliverresto.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.CategoriePlat;
import com.usherbrooke.deliverresto.entities.Ingredient;
import com.usherbrooke.deliverresto.entities.Plat;
import com.usherbrooke.deliverresto.entities.TypePlat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Valentin on 20/11/2017.
 */

public class CatPlatAdapter  extends BaseAdapter {

    private Context context;
    private List<Plat> listePlats;
    private List<CategoriePlat> listCatPlat = new ArrayList<>();

    public CatPlatAdapter(Context context, List<Plat> listePlats) {
        this.context = context;
        this.listePlats = listePlats;

        for (Plat plat : listePlats) {
            if(!listCatPlat.contains(plat.getCategorie())){
                listCatPlat.add(plat.getCategorie());
            }
        }
    }

    @Override
    public int getCount() {
        return listCatPlat.size();
    }

    @Override
    public Object getItem(int position) {
        return listCatPlat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //We must create a View:
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (LinearLayout) inflater.inflate(R.layout.row_cat_plat_component, parent, false);
        }

        List<Plat> listPlatByCat = new ArrayList<>();
        for (Plat plat : listePlats) {
            if(plat.getCategorie().equals(listCatPlat.get(position))){
                listPlatByCat.add(plat);
            }
        }

        CategoriePlatComponent cpc = (CategoriePlatComponent) convertView.findViewById(R.id.rowCatPlatComp);
        cpc.setupData(listCatPlat.get(position).getNom(), listPlatByCat);
        return convertView;

    }
}