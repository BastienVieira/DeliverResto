package com.usherbrooke.deliverresto;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.components.PaimentPlatAdapter;
import com.usherbrooke.deliverresto.components.PanierAdapter;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by alexa on 10/12/2017.
 */
public class PanierActivityTest {

    @Rule
    public ActivityTestRule<CarteActivity> panierActivityTestRule = new ActivityTestRule<>(CarteActivity.class);
    CarteActivity carte;
    Plat plat;
    PanierActivity panier;
    public ActivityTestRule<PanierActivity> panierActivity = new ActivityTestRule<>(PanierActivity.class);


    @Before
    public void setUp() throws Exception {
        carte = panierActivityTestRule.getActivity();
        DbHelper.createDbHelper(carte);
        Panier.getInstance().resetCommande();
        Panier.getInstance().getClient().setNom("DUPONT");
        Panier.getInstance().getClient().setPrenom("Antoine");
        plat = DbHelper.getPlatService().getPlatByName("Cannibale");
        Panier.getInstance().addPlat(plat,2);
        DbHelper.getCommandeService().saveCommandeFromPanier();
        panier = panierActivity.getActivity();
        Intent intent = new Intent(InstrumentationRegistry.getContext(),PanierActivity.class);
        panier = panierActivity.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreate() throws Exception {
        PanierAdapter panierAdapter = (PanierAdapter) panier.getLstPlat().getAdapter();
        for (int i = 0; i < Panier.getInstance().getLstPlats().size(); i++) {
            assertEquals(panierAdapter.getListePlats().get(i).getNom(),Panier.getInstance().getLstPlats().get(i).getNom());
        }
    }

    @Test
    public void onClickMoins() throws Throwable {
        int i =0;
        this.panierActivityTestRule.runOnUiThread(new Runnable() {
            public void run() {
                panierActivity.getActivity().onClickMoins(plat);
            }
        });
        assertEquals(Panier.getInstance().getHashMapPlats().get(plat),new Integer(1));
        for (Plat _plat : Panier.getInstance().getLstPlats() ) {
            if(_plat.getNom()== plat.getNom()) i++;
        }
        assertEquals(i,1);
    }

    @Test
    public void onClickPlus() throws Throwable {
        this.panierActivityTestRule.runOnUiThread(new Runnable() {
            public void run() {
                panierActivity.getActivity().onClickPlus(plat);
            }
        });
        assertEquals(Panier.getInstance().getHashMapPlats().get(plat),new Integer(3));
    }

}