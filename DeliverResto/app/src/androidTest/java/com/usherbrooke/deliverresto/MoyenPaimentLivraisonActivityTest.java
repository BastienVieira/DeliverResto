package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.MoyenPaiement;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by alexa on 09/12/2017.
 */
public class MoyenPaimentLivraisonActivityTest {

    @Rule
    public ActivityTestRule<MoyenPaimentLivraisonActivity> moyenPaimentLivraisonActivityTestRule = new ActivityTestRule<>(MoyenPaimentLivraisonActivity.class);
    MoyenPaimentLivraisonActivity mpl;

    @Before
    public void setUp() throws Exception {
        mpl = moyenPaimentLivraisonActivityTestRule.getActivity();
        DbHelper.createDbHelper(mpl);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreate() throws Exception {
        assertNotNull(mpl.getBtnPaypal());
        assertNotNull(mpl.getBtnValider());
        assertNotNull(mpl.getBtnCancel());
        assertNotNull(mpl.getEdtNumCarte());
        assertNotNull(mpl.getEdtTitulaireCarte());
        assertNotNull(mpl.getEdtMoisExpiration());
        assertNotNull(mpl.getEdtAnneeExpiration());
        assertNotNull(mpl.getEdtCrypto());
    }

    @Test
    public void remplirChamps() throws Throwable {
        String nom = new String("DUPONT");
        String prenom = new String("Antoine");
        Client cl = new Client(null,nom,prenom);
        DbHelper.getClientService().save(cl);
        cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        MoyenPaiement mp = new MoyenPaiement(null,"antoine.dupont@gmail.com",1234L,"M.DUPONT",new Date(1933,10,12),122);
        cl = DbHelper.getClientService().addMoyenPaiement(mp,cl);
        DbHelper.setConnectedClient(cl);
        this.moyenPaimentLivraisonActivityTestRule.runOnUiThread(new Runnable() {
            public void run() {
                mpl.remplirChamps();
            }
        });
        assertEquals(mpl.getEdtNumCarte().getText().toString(),cl.getMoyenPaiement().getNumCB());
        assertEquals(mpl.getEdtTitulaireCarte().getText().toString(),cl.getMoyenPaiement().getTitulaire());
        assertEquals(mpl.getEdtMoisExpiration().getText().toString(),String.valueOf(cl.getMoyenPaiement().getDateExpiration().getMonth()));
        assertEquals(mpl.getEdtAnneeExpiration().getText().toString(),String.valueOf(cl.getMoyenPaiement().getDateExpiration().getYear()));
        assertEquals(mpl.getEdtCrypto().getText().toString(),cl.getMoyenPaiement().getCryptogramme());
        DbHelper.deconnectClient();
    }

}