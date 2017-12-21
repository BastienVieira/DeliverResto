package com.usherbrooke.deliverresto;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.components.CatPlatAdapter;
import com.usherbrooke.deliverresto.components.CategoriePlatComponent;
import com.usherbrooke.deliverresto.entities.Ingredient;
import com.usherbrooke.deliverresto.entities.Plat;
import com.usherbrooke.deliverresto.entities.TypePlat;
import com.usherbrooke.deliverresto.entities.TypePlatStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoixPlatFragment extends Fragment {

    private Context context;

    private String typePlat = null;
    private ListView catPlatListView;

    public ChoixPlatFragment() {
        // Required empty public constructor
    }

    public String getTypePlat() {
        return typePlat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choix_plat, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        catPlatListView = (ListView) view.findViewById(R.id.catPlatlistView);
        switch (typePlat) {
            case TypePlatStrings.MENU:
                catPlatListView.setAdapter(new CatPlatAdapter(getContext(), DbHelper.getPlatService().getPlatsByType(TypePlatStrings.MENU)));
                break;

            case TypePlatStrings.ENTREE:
                catPlatListView.setAdapter(new CatPlatAdapter(getContext(), DbHelper.getPlatService().getPlatsByType(TypePlatStrings.ENTREE)));
                break;

            case TypePlatStrings.PLAT:
                catPlatListView.setAdapter(new CatPlatAdapter(getContext(), DbHelper.getPlatService().getPlatsByType(TypePlatStrings.PLAT)));
                break;

            case TypePlatStrings.DESSERT:
                catPlatListView.setAdapter(new CatPlatAdapter(getContext(), DbHelper.getPlatService().getPlatsByType(TypePlatStrings.DESSERT)));
                break;

            case TypePlatStrings.BOISSON:
                catPlatListView.setAdapter(new CatPlatAdapter(getContext(), DbHelper.getPlatService().getPlatsByType(TypePlatStrings.BOISSON)));
                break;
        }
    }

    public void setTypePlat(String typePlat) {
        this.typePlat = typePlat;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnItemSelectedListener {
        public void onItemSelected(String typePlat);
    }
}
