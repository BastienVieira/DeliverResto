package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;
import android.util.Base64;

import com.usherbrooke.deliverresto.FinalActivity;
import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.CategoriePlatStrings;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.Commande;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;
import com.usherbrooke.deliverresto.entities.TypePlatStrings;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alexa on 05/12/2017.
 */
public class PanierTestUnit {

    @Rule
    public ActivityTestRule<FinalActivity> panierTestRule = new ActivityTestRule<>(FinalActivity.class);
    private Panier panier;
    Plat plat;
    Long idTypePlat;
    Long idCategoriePlat;

    @Before
    public void setUp() throws Exception {
        DbHelper.createDbHelper(panierTestRule.getActivity());
        panier = Panier.getInstance();
        panier.resetCommande();
        DbHelper.getCommandeService().save(panier.getCurrentCommande());
        idTypePlat = DbHelper.getTypePlatService().getTypePlatByName(TypePlatStrings.PLAT).getId();
        idCategoriePlat = DbHelper.getCategoriePlatService().getCategoriePlatByName(CategoriePlatStrings.BURGER).getId();
        plat = new Plat(null,"saucisses",new Float(10),idTypePlat,idCategoriePlat,null);
        DbHelper.getPlatService().save(plat);
        plat = DbHelper.getPlatService().getPlatByName("saucisses");
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void addPlat() throws Exception {
        panier.addPlat(plat,1);
        assertEquals(panier.getHashMapPlats().get(plat),new Integer(1));
        int i = 0, j = 0;
        for(j=0;j<panier.getLstPlats().size();j++) {
            if(panier.getLstPlats().get(j)==plat) i++;
        }
        assertEquals(i,1);
    }

    @Test
    public void removePlatbyOne() throws Exception {
        int nbPlats = 2;
        panier.addPlat(plat,1);
        panier.removePlatbyOne(plat);
        assertFalse(panier.getHashMapPlats().containsKey(plat));
        assertFalse(panier.getLstPlats().contains(plat));
        panier.addPlat(plat,nbPlats);
        panier.removePlatbyOne(plat);
        assertEquals(panier.getHashMapPlats().get(plat).intValue(),nbPlats-1);
        Plat newPlat = new Plat(null,"tarte",new Float(10),idTypePlat,idCategoriePlat,null);
        DbHelper.getPlatService().save(newPlat);
        newPlat = DbHelper.getPlatService().getPlatByName("tarte");
        panier.removePlatbyOne(newPlat);
        assertFalse(panier.getHashMapPlats().containsKey(newPlat));
        assertFalse(panier.getLstPlats().contains(newPlat));
    }

    @Test
    public void addPlatsToCommande() throws Exception {
        panier.addPlat(plat,1);
        Plat newPlat = new Plat(null,"tarte",new Float(10),idTypePlat,idCategoriePlat,null);
        panier.addPlat(newPlat,2);
        panier.addPlatsToCommande();
        List lstPlatsCommande = panier.getCurrentCommande().getListePlat();
        assertTrue(lstPlatsCommande.contains(plat));
        int j = 0;
        for (int i = 0; i < panier.getCurrentCommande().getListePlat().size() ; i++) {
            if(lstPlatsCommande.get(i)==newPlat) j++;
        }
        assertEquals(j,2);
    }

}