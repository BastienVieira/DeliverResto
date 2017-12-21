package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;
import android.util.Base64;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexa on 03/12/2017.
 */
public class AjoutAdresseFavoriteActivityTest {

    @Rule
    public ActivityTestRule<AjoutAdresseFavoriteActivity> ajoutAdresseActivityRule = new ActivityTestRule<>(AjoutAdresseFavoriteActivity.class);
    Adresse adr;
    Client cl;
    AjoutAdresseFavoriteActivity addAdr;
    String nom;
    String prenom;

    @Before
    public void setUp() throws Exception {
        addAdr = ajoutAdresseActivityRule.getActivity();
        DbHelper.createDbHelper(addAdr);
        nom = new String ("DUPONT");
        prenom = new String("Antoine");
        cl = new Client(null,nom,prenom);
        DbHelper.getClientService().save(cl);
        cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreateTest() throws Exception {
        assertNotNull(addAdr.getEnregistrerAdresseButton());
        assertNotNull(addAdr.getAdresseEditText());
        assertNotNull(addAdr.getCpEditText());
        assertNotNull(addAdr.getVilleEditText());
        assertNotNull(addAdr.getProvinceEditText());
        assertNotNull(addAdr.getIndicationEditText());
    }

    public void onCreateTestConnecte() throws Exception {
        adr = new Adresse(null,"1 rue Fraser","J1J2V2","Sherbrooke","QC","yo");
        cl = DbHelper.getClientService().addAdresse(adr,cl);
        DbHelper.setConnectedClient(cl);
        assertNotEquals(addAdr.getAdresseEditText().getText().toString(), "");
        assertNotEquals(addAdr.getCpEditText().getText().toString(), "");
        assertNotEquals(addAdr.getVilleEditText().getText().toString(), "");
        assertNotEquals(addAdr.getProvinceEditText().getText().toString(), "");
        assertNotEquals(addAdr.getIndicationEditText().getText().toString(), "");
    }

    @Test
    public void onCreateTestDeconnecte() throws Exception {
        assertEquals(addAdr.getAdresseEditText().getText().toString(), "");
        assertEquals(addAdr.getCpEditText().getText().toString(), "");
        assertEquals(addAdr.getVilleEditText().getText().toString(), "");
        assertEquals(addAdr.getProvinceEditText().getText().toString(), "");
        assertEquals(addAdr.getIndicationEditText().getText().toString(), "");
    }

    @Test
    public void onCreateTestAdrEmpty() throws Exception {
        DbHelper.setConnectedClient(cl);
        assertEquals(addAdr.getAdresseEditText().getText().toString(), "");
        assertEquals(addAdr.getCpEditText().getText().toString(), "");
        assertEquals(addAdr.getVilleEditText().getText().toString(), "");
        assertEquals(addAdr.getProvinceEditText().getText().toString(), "");
        assertEquals(addAdr.getIndicationEditText().getText().toString(), "");
    }

    @Test
    public void setViewDataClient() throws Throwable {
        adr = new Adresse(null,"1 rue Fraser","J1J2V2","Sherbrooke","QC","yo");
        cl = DbHelper.getClientService().addAdresse(adr,cl);
        this.ajoutAdresseActivityRule.runOnUiThread(new Runnable() {
            public void run() {
                ajoutAdresseActivityRule.getActivity().setViewDataClient(cl);
            }
        });
        assertEquals(ajoutAdresseActivityRule.getActivity().getAdresseEditText().getText().toString(),adr.getRue());
        assertEquals(ajoutAdresseActivityRule.getActivity().getCpEditText().getText().toString(),adr.getCodePostal());
        assertEquals(ajoutAdresseActivityRule.getActivity().getVilleEditText().getText().toString(),adr.getVille());
        assertEquals(ajoutAdresseActivityRule.getActivity().getProvinceEditText().getText().toString(),adr.getProvince());
        assertEquals(ajoutAdresseActivityRule.getActivity().getIndicationEditText().getText().toString(),adr.getIndication());
    }

}