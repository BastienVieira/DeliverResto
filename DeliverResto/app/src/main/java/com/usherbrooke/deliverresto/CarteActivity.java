package com.usherbrooke.deliverresto;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.TypePlatStrings;

import android.view.View;
import android.widget.Button;

import com.usherbrooke.deliverresto.entities.Panier;

public class CarteActivity extends NavActivity implements CarteFragment.OnItemSelectedListener {

    public static final String CARTE_FRAGMENT_TITLE = "Carte";
    public static final String CHOIX_PLAT_TITLE = "Choix des plats";

    private CarteFragment carteFragment;
    private ChoixPlatFragment choixPlatFragment;
    private CarteActivity carteActivity = this;

    public CarteFragment getCarteFragment() {
        return carteFragment;
    }

    public ChoixPlatFragment getChoixPlatFragment() {
        return choixPlatFragment;
    }

    public CarteActivity getCarteActivity() {
        return carteActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_carte);
        this.setViewLayout(R.layout.activity_carte);

        // Create a new Fragment to be placed in the activity layout
        carteFragment = new CarteFragment();
        choixPlatFragment = new ChoixPlatFragment();
//        setFragment(carteFragment, CARTE_FRAGMENT_TITLE);
        replaceFragment(carteFragment, CARTE_FRAGMENT_TITLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carte, menu);
        return true;
    }

//    public void setFragment(Fragment frag, String title) {
//        if (findViewById(R.id.fragment_container) != null) {
//            setTitle(title);
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag, title).commit();
//        }
//    }

    public void replaceFragment(Fragment newFragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment, title);
        transaction.addToBackStack(null);
        transaction.commit();

        setTitle(title);
    }

    public boolean isCarteFragmentDisplayed() {
        return this.getSupportFragmentManager().findFragmentByTag(CARTE_FRAGMENT_TITLE).isVisible();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_panier:
                onPanier();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onPanier() {
        Intent intent = new Intent(this, PanierActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(String typePlat) {
        switch (typePlat) {
            case TypePlatStrings.MENU:
                choixPlatFragment.setTypePlat(TypePlatStrings.MENU);
                replaceFragment(choixPlatFragment, CHOIX_PLAT_TITLE);
                break;

            case TypePlatStrings.ENTREE:
                choixPlatFragment.setTypePlat(TypePlatStrings.ENTREE);
                replaceFragment(choixPlatFragment, CHOIX_PLAT_TITLE);
                break;

            case TypePlatStrings.PLAT:
                choixPlatFragment.setTypePlat(TypePlatStrings.PLAT);
                replaceFragment(choixPlatFragment, CHOIX_PLAT_TITLE);
                break;

            case TypePlatStrings.DESSERT:
                choixPlatFragment.setTypePlat(TypePlatStrings.DESSERT);
                replaceFragment(choixPlatFragment, CHOIX_PLAT_TITLE);
                break;

            case TypePlatStrings.BOISSON:
                choixPlatFragment.setTypePlat(TypePlatStrings.BOISSON);
                replaceFragment(choixPlatFragment, CHOIX_PLAT_TITLE);
                break;

            default:
                replaceFragment(carteFragment, CARTE_FRAGMENT_TITLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isCarteFragmentDisplayed()) {
            if (DbHelper.getConnectedClient() != null)
                show();
            else
                this.finish();

        } else
            replaceFragment(carteFragment, CARTE_FRAGMENT_TITLE);
    }

    private void show() {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.are_you_sure_to_deco);
        Button bValider = (Button) d.findViewById(R.id.validerDecoButton);
        Button bAnnuler = (Button) d.findViewById(R.id.annulerDecoButton);
        bValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper.deconnectClient();
                d.dismiss();
                carteActivity.finish();
            }
        });
        bAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
}
