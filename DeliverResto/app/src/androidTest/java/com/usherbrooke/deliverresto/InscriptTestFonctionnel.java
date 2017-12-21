package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;

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
 * Created by alexa on 05/12/2017.
 */
public class InscriptTestFonctionnel {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private String nom;
    private String prenom;
    private String courriel;
    private String mdp;
    private String tel;
    private String rue;
    private String cp;
    private String ville;
    private String province;
    private String indic;
    private String numCB;
    private String tit;
    private String annee;
    private String mois;
    private String crypt;

    @Before
    public void setUp() throws Exception {
        DbHelper.createDbHelper(mainActivityActivityTestRule.getActivity());
        nom = new String("DUPONT");
        prenom = new String("Antoine");
        tel = new String("1234567");
        courriel = new String("antoine.dupont@gmail.com");
        mdp = "password";
        rue = new String("1 rue Fraser");
        cp = new String("J1J2V2");
        ville = new String("Sherbrooke");
        province = new String("QC");
        indic = new String("yo");
        numCB = new String("123456789");
        tit = new String("MR.DUPONT");
        annee = new String("1998");
        mois = new String("4");
        crypt = new String("123");
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreate() throws Exception {
        onView(withId(R.id.inscriptionButton)).perform(click());
        onView(withId(R.id.nameEditText)).perform(typeText(nom));
        onView(withId(R.id.prenomEditText)).perform(typeText(prenom));
        onView(withId(R.id.telEditText)).perform(typeText(tel));
        onView(withId(R.id.courrielEditText)).perform(typeText(courriel));
        onView(withId(R.id.mdpEditText)).perform(typeText(mdp));
        onView(withId(R.id.mdpEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.ajoutAdresseFavoriteButton)).perform(click());
        onView(withId(R.id.adresseEditText)).perform(typeText(rue));
        onView(withId(R.id.cpEditText)).perform(typeText(cp));
        onView(withId(R.id.villeEditText)).perform(typeText(ville));
        onView(withId(R.id.provinceEditText)).perform(typeText(province));
        onView(withId(R.id.indicationEditText)).perform(typeText(indic));
        onView(withId(R.id.indicationEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.enregistrerAdresseButton)).perform(click());
        onView(withId(R.id.ajoutMoyenPaiementButton)).perform(click());
        onView(withId(R.id.numCBEditText)).perform(typeText(numCB));
        onView(withId(R.id.titulaireEditText)).perform(typeText(tit));
        onView(withId(R.id.moisExpirationEditText)).perform(typeText(mois));
        onView(withId(R.id.anneeExpirationEditText)).perform(typeText(annee));
        onView(withId(R.id.anneeExpirationEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.cryptogrammeEditText)).perform(typeText(crypt));
        onView(withId(R.id.cryptogrammeEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.enregistrerAdresseButton)).perform(click());
        onView(withId(R.id.terminerInscriptionButton)).perform(click());
        Client cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertEquals(cl.getNom(),nom);
        assertEquals(cl.getPrenom(),prenom);
        assertEquals(cl.getNumTelephone(),tel);
        assertEquals(cl.getCourriel(),courriel);
        assertEquals(cl.getAdresse().getRue(),rue);
        assertEquals(cl.getAdresse().getCodePostal(),cp);
        assertEquals(cl.getAdresse().getVille(),ville);
        assertEquals(cl.getAdresse().getProvince(),province);
        assertEquals(cl.getAdresse().getIndication(),indic);
        assertEquals(cl.getMoyenPaiement().getNumCB().toString(),numCB);
        assertEquals(cl.getMoyenPaiement().getTitulaire(),tit);
        assertEquals(String.valueOf(cl.getMoyenPaiement().getDateExpiration().getMonth()+1),mois);
        assertEquals(String.valueOf(cl.getMoyenPaiement().getDateExpiration().getYear()),annee);
        assertEquals(String.valueOf(cl.getMoyenPaiement().getCryptogramme()),crypt);
    }

}