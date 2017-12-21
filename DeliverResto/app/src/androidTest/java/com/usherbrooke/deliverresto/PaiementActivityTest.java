package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.components.PaimentPlatAdapter;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.Commande;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by alexa on 09/12/2017.
 */
public class PaiementActivityTest {

    @Rule
    public ActivityTestRule<PaiementActivity> paiementActivityActivityTestRule = new ActivityTestRule<>(PaiementActivity.class);
    PaiementActivity paiement;
    Client cl;

    @Before
    public void setUp() throws Exception {
        paiement = paiementActivityActivityTestRule.getActivity();
        DbHelper.createDbHelper(paiement);
        Panier.getInstance().resetCommande();
        Panier.getInstance().getClient().setNom("DUPONT");
        Panier.getInstance().getClient().setPrenom("Antoine");
        Plat plat = DbHelper.getPlatService().getPlatByName("Cannibale");
        Panier.getInstance().addPlat(plat,2);
        DbHelper.getCommandeService().saveCommandeFromPanier();
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreate() throws Exception {
        assertNotNull(paiement.getLstPlat());
        assertNotNull(paiement.getLstPlat().getAdapter());
        PaimentPlatAdapter paiementPlatAdapter = (PaimentPlatAdapter) paiement.getLstPlat().getAdapter();
        for (int i = 0; i < Panier.getInstance().getLstPlats().size(); i++) {
            assertEquals(paiementPlatAdapter.getListePlats().get(i).getNom(),Panier.getInstance().getLstPlats().get(i).getNom());
        }
        assertEquals(paiement.getSum(),19f,0);
        assertEquals(paiement.getTxtTotal().getText().toString(),"19.0$");
    }

    @Test
    public void boutonHeureTest() throws Exception {
        onView(withId(R.id.setClockButton)).perform(click());
        assertTrue(paiement.getEditHour().isEnabled());
        assertFalse(paiement.getRdButtonTot().isChecked());
    }

    @Test
    public void btnLivrerTest() throws Exception {
        onView(withId(R.id.livrerButton)).perform(click());
        int heure1 = Panier.getInstance().getCurrentCommande().getHeureDeLivraison().getHours();
        int heure2 = Calendar.getInstance(TimeZone.getTimeZone("GMT-6:00")).getTime().getHours();
        int minute1 = Panier.getInstance().getCurrentCommande().getHeureDeLivraison().getMinutes();
        int minute2 = Calendar.getInstance(TimeZone.getTimeZone("GMT-6:00")).getTime().getMinutes();
        assertEquals(minute1,minute2);
        assertEquals(heure1,heure2);
        pressBack();
        onView(withId(R.id.setClockButton)).perform(click());
        onView(withId(R.id.hourValiderButton)).perform(click());
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-6:00"));
        cal.set(Calendar.HOUR_OF_DAY, new Integer(12));
        cal.set(Calendar.MINUTE, new Integer(30));
        heure1 = Panier.getInstance().getCurrentCommande().getHeureDeLivraison().getHours();
        minute1 = Panier.getInstance().getCurrentCommande().getHeureDeLivraison().getMinutes();
    }

    @Test
    public void btnEmporter() throws Exception {
        onView(withId(R.id.emporterButton)).perform(click());
        int heure1 = Panier.getInstance().getCurrentCommande().getHeureDeLivraison().getHours();
        int heure2 = Calendar.getInstance(TimeZone.getTimeZone("GMT-6:00")).getTime().getHours();
        int minute1 = Panier.getInstance().getCurrentCommande().getHeureDeLivraison().getMinutes();
        int minute2 = Calendar.getInstance(TimeZone.getTimeZone("GMT-6:00")).getTime().getMinutes();
        assertEquals(minute1,minute2);
        assertEquals(heure1,heure2);
        pressBack();
        onView(withId(R.id.setClockButton)).perform(click());
        onView(withId(R.id.hourValiderButton)).perform(click());
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-6:00"));
        cal.set(Calendar.HOUR_OF_DAY, new Integer(12));
        cal.set(Calendar.MINUTE, new Integer(30));
    }

}