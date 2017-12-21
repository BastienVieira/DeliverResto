package com.usherbrooke.deliverresto.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usherbrooke.deliverresto.PanierActivity;
import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

import java.util.List;

/**
 * Created by vieir on 22/11/2017.
 */

public class PanierAdapter extends BaseAdapter {

    private Context context;
    private List<Plat> listePlats;

    public PanierAdapter(Context context, List<Plat> listePlats){
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
            view = (LinearLayout) inflater.inflate(R.layout.row_panier, viewGroup, false);
        }

        final Plat p =  listePlats.get(i);

        TextView txtQuantity = view.findViewById(R.id.textQuantity);
        txtQuantity.setText(Panier.getInstance().getHashMapPlats().get(p).toString());
        TextView txtPlat = view.findViewById(R.id.textPlat);
        txtPlat.setText(p.getNom());

        Button btnPlus = view.findViewById(R.id.numberPlusButton);
        btnPlus.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(context instanceof PanierActivity){
                    ((PanierActivity)context).onClickPlus(p);
                }
            }
        });

        Button btnMoins = view.findViewById(R.id.numberMoinsButton);
        btnMoins.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof PanierActivity){
                    ((PanierActivity)context).onClickMoins(p);
                }
            }
        });

        return view;
    }

    public void refreshListView(List<Plat> lstplats)
    {
        this.listePlats = lstplats;
        notifyDataSetChanged();
    }

    public List<Plat> getListePlats() {
        return listePlats;
    }
}
