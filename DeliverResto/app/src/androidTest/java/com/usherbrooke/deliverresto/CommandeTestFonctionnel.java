package com.usherbrooke.deliverresto;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.CategoriePlatStrings;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.Commande;
import com.usherbrooke.deliverresto.entities.CommandeEtatStrings;
import com.usherbrooke.deliverresto.entities.IngredientStrings;
import com.usherbrooke.deliverresto.entities.MoyenPaiement;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;
import com.usherbrooke.deliverresto.entities.TypePlatStrings;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Created by alexa on 10/12/2017.
 */
public class CommandeTestFonctionnel {

    @Rule
    public ActivityTestRule<CarteActivity> carteActivityTestRule = new ActivityTestRule<>(CarteActivity.class);
    private Plat plat;
    public ActivityTestRule<PanierActivity> panierActivity = new ActivityTestRule<>(PanierActivity.class);
    PanierActivity panier;

    @Before
    public void setUp() throws Exception {
        DbHelper.createDbHelper(carteActivityTestRule.getActivity());
        DbHelper.deleteData();
        DbHelper.createTypesPlat(TypePlatStrings.ENTREE, TypePlatStrings.PLAT, TypePlatStrings.DESSERT, TypePlatStrings.BOISSON, TypePlatStrings.MENU);
        DbHelper.createCategoriesPlat(CategoriePlatStrings.SALADE, CategoriePlatStrings.SOUPE, CategoriePlatStrings.BURGER, CategoriePlatStrings.PIZZA, CategoriePlatStrings.DESSERT, CategoriePlatStrings.BIERE, CategoriePlatStrings.SODA);
        DbHelper.createEtatsCommande(CommandeEtatStrings.A_PREPARER, CommandeEtatStrings.PRETE, CommandeEtatStrings.EN_LIVRAISON_NON_PAYEE, CommandeEtatStrings.EN_LIVRAISON_PAYEE, CommandeEtatStrings.TERMINEE);//todo
        DbHelper.createMagasins();
        DbHelper.createClient();
        DbHelper.createIngredients();
        DbHelper.createPlat("Maori", 7.5f, TypePlatStrings.ENTREE, CategoriePlatStrings.SALADE, R.drawable.entrees_background,
                IngredientStrings.LAITUE, IngredientStrings.OIGNONS, IngredientStrings.CAROTTES);
        DbHelper.createPlat("Cerf", 12.5f, TypePlatStrings.PLAT, CategoriePlatStrings.BURGER, R.drawable.entrees_background,
                IngredientStrings.CERF, IngredientStrings.TOMATES, IngredientStrings.LAITUE);
        DbHelper.createPlat("Brownie", 6.5f, TypePlatStrings.DESSERT, CategoriePlatStrings.DESSERT, R.drawable.entrees_background,
                IngredientStrings.CHOCOLAT);
        DbHelper.createPlat("Griffon Rousse", 6.5f, TypePlatStrings.BOISSON, CategoriePlatStrings.BIERE, R.drawable.entrees_background);
        Panier.getInstance().resetCommande();
        DbHelper.getCommandeService().save(Panier.getInstance().getCurrentCommande());
        plat = DbHelper.getPlatService().getPlatByName("Maori");
        Panier.getInstance().addPlat(plat,1);
        plat = DbHelper.getPlatService().getPlatByName("Cerf");
        Panier.getInstance().addPlat(plat,1);
        plat = DbHelper.getPlatService().getPlatByName("Brownie");
        Panier.getInstance().addPlat(plat,1);
        plat = DbHelper.getPlatService().getPlatByName("Griffon Rousse");
        Panier.getInstance().addPlat(plat,1);
        Panier.getInstance().addPlatsToCommande();
        DbHelper.getCommandeService().save(Panier.getInstance().getCurrentCommande());
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
        onView(withId(R.id.commanderButton)).perform(click());
        onView(withId(R.id.livrerButton)).perform(click());
        onView(withId(R.id.nomEditText)).perform(typeText("DUPONT"));
        onView(withId(R.id.prenomEditText)).perform(typeText("Antoine"));
        onView(withId(R.id.adresseEditText)).perform(typeText("1 rue Fraser"));
        onView(withId(R.id.cpEditText)).perform(typeText("J1J2V2"));
        onView(withId(R.id.villeEditText)).perform(typeText("Sherbrooke"));
        onView(withId(R.id.villeEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.provinceEditText)).perform(typeText("QC"));
        onView(withId(R.id.provinceEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.indicationEditText)).perform(typeText("yo"));
        onView(withId(R.id.indicationEditText)).perform(closeSoftKeyboard());
        onView(withId(R.id.validerLivraisonButton)).perform(click());
        onView(withId(R.id.numCBEditText)).perform(typeText("12345678912345678"));
        onView(withId(R.id.titulaireEditText)).perform(typeText("M.DUPONT"));
        onView(withId(R.id.moisExpirationEditText)).perform(typeText("06"));
        onView(withId(R.id.anneeExpirationEditText)).perform(typeText("2020"));
        onView(withId(R.id.cryptogrammeEditText)).perform(typeText("123"));
        onView(withId(R.id.cryptogrammeEditText)).perform(closeSoftKeyboard());
        assertEquals(Panier.getInstance().getCurrentCommande().getListePlat().get(0).getNom(),"Maori");
        assertEquals(Panier.getInstance().getCurrentCommande().getListePlat().get(1).getNom(),"Cerf");
        assertEquals(Panier.getInstance().getCurrentCommande().getListePlat().get(2).getNom(),"Brownie");
        assertEquals(Panier.getInstance().getCurrentCommande().getListePlat().get(3).getNom(),"Griffon Rousse");
        assertEquals(Panier.getInstance().getClient().getNom(),"DUPONT");
        assertEquals(Panier.getInstance().getClient().getPrenom(),"Antoine");
        onView(withId(R.id.validerPaimentButton)).perform(click());
        Adresse adr = DbHelper.getAdresseService().getAdresseByRueVille("1 rue Fraser","Sherbrooke");
        assertNotNull(adr);
        assertEquals(adr.getCodePostal(),"J1J2V2");
        assertEquals(adr.getProvince(),"QC");
        assertEquals(adr.getIndication(),"yo");
        MoyenPaiement mp = DbHelper.getMoyenPaiementService().getMoyenPaiementByNumCarte(12345678912345678L);
        assertNotNull(mp);
        assertEquals(mp.getTitulaire(),"M.DUPONT");
        assertEquals(mp.getDateExpiration().getMonth(),5);
        assertEquals(mp.getDateExpiration().getYear(),2020);
        assertEquals(mp.getCryptogramme(),new Integer(123));
        onView(withId(R.id.finalOKButton)).perform(click());
    }

}