package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;
import android.util.Base64;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Client;

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
 * Created by alexa on 04/12/2017.
 */
public class InscriptionActivityTestIntegr {

    @Rule
    public ActivityTestRule<InscriptionActivity> inscriptionActivityActivityTestRule = new ActivityTestRule<>(InscriptionActivity.class);
    InscriptionActivity inscript;

    @Before
    public void setUp() throws Exception {
        inscript = inscriptionActivityActivityTestRule.getActivity();
        DbHelper.createDbHelper(inscript);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreate() throws Exception {
        //Initialisation
        String nom = new String("DUPONT");
        String prenom = new String("Antoine");
        String tel = new String("1234567");
        String courriel = new String("antoine.dupont@gmail.com");
        String mdp = "password";

        //Création des objets graphiques
        assertNotNull(inscript.getAjoutMoyenPaiementBouton());
        assertNotNull(inscript.getAjoutAdresseFavoriteBouton());
        assertNotNull(inscript.getTerminerInscriptionButton());
        assertNotNull(inscript.getNomEditText());
        assertNotNull(inscript.getPrenomEditText());
        assertNotNull(inscript.getTelEditText());
        assertNotNull(inscript.getCourrielEditText());
        assertNotNull(inscript.getMdpEditText());

        //Terminer Inscription champs vides
        onView(withId(R.id.terminerInscriptionButton)).perform(click());
        assertEquals(inscript.getNomEditText().getText().toString(),"");
        assertEquals(inscript.getPrenomEditText().getText().toString(),"");
        assertEquals(inscript.getTelEditText().getText().toString(),"");
        assertEquals(inscript.getCourrielEditText().getText().toString(),"");
        assertEquals(inscript.getMdpEditText().getText().toString(),"");

        //Terminer Inscription champs non vides
        onView(withId(R.id.nameEditText)).perform(typeText(nom));
        onView(withId(R.id.prenomEditText)).perform(typeText(prenom));
        onView(withId(R.id.telEditText)).perform(typeText(tel));
        onView(withId(R.id.courrielEditText)).perform(typeText(courriel));
        onView(withId(R.id.mdpEditText)).perform(typeText(mdp));
        onView(withId(R.id.mdpEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.terminerInscriptionButton)).perform(click());
        Client cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertEquals(cl.getNom(),nom);
        assertEquals(cl.getPrenom(),prenom);
        assertEquals(cl.getNumTelephone(),tel);
        assertEquals(cl.getCourriel(),courriel);
        assertEquals(new String(Base64.decode(cl.getMdp(), Base64.DEFAULT)),mdp);
    }


}