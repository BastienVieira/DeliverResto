package com.usherbrooke.deliverresto.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.Magasin;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

import java.util.List;

/**
 * Created by vieir on 23/11/2017.
 */

public class MagasinAdapter extends BaseAdapter{
    private Context context;
    private List<Magasin> listeMagasins;

    public MagasinAdapter(Context context, List<Magasin> listeMagasins){
        this.context=context;
        this.listeMagasins=listeMagasins;
    }

    @Override
    public int getCount() {
        return listeMagasins.size();
    }

    @Override
    public Object getItem(int i) {
        return listeMagasins.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if( view == null ){
            //We must create a View:
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(R.layout.row_magasin, viewGroup, false);
        }
        Magasin m = listeMagasins.get(i);

        TextView txtName = view.findViewById(R.id.textNomMagasin);
        txtName.setText(m.getNom());

        TextView txtRue = view.findViewById(R.id.textRueMagasin);
        txtRue.setText(m.getAdresse().getRue());

        TextView txtVille = view.findViewById(R.id.textVille);
        txtVille.setText(m.getAdresse().getVille());

        TextView txtCPadnProvince = view.findViewById(R.id.txtCPandProvince);
        txtCPadnProvince.setText(m.getAdresse().getCodePostal() + ", " + m.getAdresse().getProvince());

        return view;
    }
}
