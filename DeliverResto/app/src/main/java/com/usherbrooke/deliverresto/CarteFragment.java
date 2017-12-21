package com.usherbrooke.deliverresto;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.usherbrooke.deliverresto.entities.TypePlat;
import com.usherbrooke.deliverresto.entities.TypePlatStrings;

/**
 * A placeholder fragment containing a simple view.
 */
public class CarteFragment extends Fragment {

    private Button entreesButton;
    private Button platsButton;
    private Button dessertsButton;
    private Button menuButton;
    private Button boissonButton;

    private Context context;

    public CarteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carte, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        entreesButton = (Button) getView().findViewById(R.id.entreesButton);
        platsButton = (Button) getView().findViewById(R.id.platsButton);
        dessertsButton = (Button) getView().findViewById(R.id.dessertsButton);
        menuButton = (Button) getView().findViewById(R.id.menuButton);
        boissonButton = (Button) getView().findViewById(R.id.boissonsButton);

        entreesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof CarteActivity){
                    ((CarteActivity)context).onItemSelected(TypePlatStrings.ENTREE);
                }
            }
        });
        platsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof CarteActivity){
                    ((CarteActivity)context).onItemSelected(TypePlatStrings.PLAT);
                }
            }
        });
        dessertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof CarteActivity){
                    ((CarteActivity)context).onItemSelected(TypePlatStrings.DESSERT);
                }
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof CarteActivity){
                    ((CarteActivity)context).onItemSelected(TypePlatStrings.MENU);
                }
            }
        });
        boissonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof CarteActivity){
                    ((CarteActivity)context).onItemSelected(TypePlatStrings.BOISSON);
                }
            }
        });
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(String typePlat);
    }
}
