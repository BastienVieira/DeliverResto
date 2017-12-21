package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;
import android.util.Base64;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by alexa on 09/12/2017.
 */
public class CommandeALivrerActivityTest {

    @Rule
    public ActivityTestRule<CommandeALivrerActivity> commandeALivrerActivityTestRule = new ActivityTestRule<>(CommandeALivrerActivity.class);
    private CommandeALivrerActivity commandeALivrer;
    private String rue, ville, cp, province, indication, nom, prenom;

    @Before
    public void setUp() throws Exception {
        commandeALivrer = commandeALivrerActivityTestRule.getActivity();
        DbHelper.createDbHelper(commandeALivrer);
        rue = new String("1 rue Fraser");
        ville = new String("Sherbrooke");
        cp = new String("J1J2V2");
        province = new String("QC");
        indication = new String("yo");
        prenom = new String("Antoine");
        nom = new String("DUPONT");
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreate() throws Exception {
        //Vérification des éléments graphiques non nuls
        assertNotNull(commandeALivrer.getTxtAdresse());
        assertNotNull(commandeALivrer.getTxtVille());
        assertNotNull(commandeALivrer.getTxtCP());
        assertNotNull(commandeALivrer.getTxtProvince());
        assertNotNull(commandeALivrer.getTxtIndication());
        assertNotNull(commandeALivrer.getTxtNom());
        assertNotNull(commandeALivrer.getTxtPrenom());
        assertNotNull(commandeALivrer.getPositionButton());
        assertNotNull(commandeALivrer.getValiderButton());
        assertNotNull(commandeALivrer.getAnnulerButton());
    }

    @Test
    public void remplirChamps() throws Throwable {
        Client cl = new Client(null,nom,prenom);
        DbHelper.getClientService().save(cl);
        cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        Adresse adr = new Adresse(null,rue,ville,cp,province,indication);
        cl = DbHelper.getClientService().addAdresse(adr,cl);
        DbHelper.setConnectedClient(cl);
        this.commandeALivrerActivityTestRule.runOnUiThread(new Runnable() {
            public void run() {
                commandeALivrer.remplirChamps();
            }
        });
        assertEquals(commandeALivrer.getTxtAdresse().getText().toString(),cl.getAdresse().getRue());
        assertEquals(commandeALivrer.getTxtVille().getText().toString(),cl.getAdresse().getVille());
        assertEquals(commandeALivrer.getTxtCP().getText().toString(),cl.getAdresse().getCodePostal());
        assertEquals(commandeALivrer.getTxtProvince().getText().toString(),cl.getAdresse().getProvince());
        assertEquals(commandeALivrer.getTxtNom().getText().toString(),cl.getNom());
        assertEquals(commandeALivrer.getTxtPrenom().getText().toString(),cl.getPrenom());
        DbHelper.deconnectClient();
    }

}