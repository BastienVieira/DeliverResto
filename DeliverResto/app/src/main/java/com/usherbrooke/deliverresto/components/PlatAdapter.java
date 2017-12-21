package com.usherbrooke.deliverresto.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.Ingredient;
import com.usherbrooke.deliverresto.entities.Plat;

import java.util.List;

/**
 * Created by Valentin on 19/11/2017.
 */

public class PlatAdapter extends BaseAdapter {

    private Context context;
    private List<Plat> listePlats;

    public PlatAdapter(Context context, List<Plat> listePlats){
        this.context=context;
        this.listePlats=listePlats;
    }


    @Override
    public int getCount() {
        return listePlats.size();
    }

    @Override
    public Object getItem(int position) {
        return listePlats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ){
            //We must create a View:
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (LinearLayout) inflater.inflate(R.layout.row_plat_component, parent, false);

        }
        Plat p = listePlats.get(position);

        PlatComponent cpc = convertView.findViewById(R.id.rowPlatComp);
        cpc.setupData(p);
        return convertView;

    }
}
