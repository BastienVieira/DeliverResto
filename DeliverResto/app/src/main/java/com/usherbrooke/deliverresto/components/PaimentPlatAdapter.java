package com.usherbrooke.deliverresto.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

import java.util.List;

/**
 * Created by vieir on 22/11/2017.
 */

public class PaimentPlatAdapter extends BaseAdapter {
    private Context context;
    private List<Plat> listePlats;

    public List<Plat> getListePlats() {
        return listePlats;
    }

    public PaimentPlatAdapter(Context context, List<Plat> listePlats){
        this.context=context;
        this.listePlats=listePlats;
    }

    @Override
    public int getCount() {
        return listePlats.size();
    }

    @Override
    public Object getItem(int i) {
        return listePlats.get(i);
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
            view = (LinearLayout) inflater.inflate(R.layout.row_paiement_plat, viewGroup, false);
        }
        Plat p = listePlats.get(i);

        TextView txtQuantity = view.findViewById(R.id.textQuantite);
        txtQuantity.setText("x" + Panier.getInstance().getHashMapPlats().get(p));
        TextView txtPlat = view.findViewById(R.id.txtPlat);
        txtPlat.setText(p.getNom());
        TextView txtPrix = view.findViewById(R.id.txtPrix);
        txtPrix.setText(p.getPrix().toString() + "$");



        return view;
    }
}
