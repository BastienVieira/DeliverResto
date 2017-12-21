package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.TypePlatStrings;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by alexa on 05/12/2017.
 */
public class CarteActivityTest {

    @Rule
    public ActivityTestRule<CarteActivity> carteActivityTestRule = new ActivityTestRule<>(CarteActivity.class);
    CarteActivity carteActivity;

    @Before
    public void setUp() throws Exception {
        carteActivity = carteActivityTestRule.getActivity();
        DbHelper.createDbHelper(carteActivity);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void onCreate() throws Exception {
        assertNotNull(carteActivity.getCarteFragment());
        assertNotNull(carteActivity.getChoixPlatFragment());
        assertTrue(carteActivity.isCarteFragmentDisplayed());
        // Ligne ci-dessus teste également les méthodes replacefragment et une partie de isCarteFragmentDisplayed
    }

    @Test
    public void isCarteFragmentDisplayed() throws Exception {
        carteActivity.getChoixPlatFragment().setTypePlat(TypePlatStrings.PLAT);
        this.carteActivity.runOnUiThread(new Runnable() {
            public void run() {
                carteActivity.replaceFragment(carteActivity.getChoixPlatFragment(),CarteActivity.CHOIX_PLAT_TITLE);
            }
        });
        assertFalse(carteActivity.isCarteFragmentDisplayed());
    }

    @Test
    public void onItemSelected() throws Exception {
        onView(withId(R.id.entreesButton)).perform(click());
        assertTrue(carteActivity.getSupportFragmentManager().findFragmentByTag(CarteActivity.CHOIX_PLAT_TITLE).isVisible());
        assertEquals(carteActivity.getChoixPlatFragment().getTypePlat(), TypePlatStrings.ENTREE);
        pressBack();
        onView(withId(R.id.platsButton)).perform(click());
        assertTrue(carteActivity.getSupportFragmentManager().findFragmentByTag(CarteActivity.CHOIX_PLAT_TITLE).isVisible());
        assertEquals(carteActivity.getChoixPlatFragment().getTypePlat(), TypePlatStrings.PLAT);
        pressBack();
        onView(withId(R.id.dessertsButton)).perform(click());
        assertTrue(carteActivity.getSupportFragmentManager().findFragmentByTag(CarteActivity.CHOIX_PLAT_TITLE).isVisible());
        assertEquals(carteActivity.getChoixPlatFragment().getTypePlat(), TypePlatStrings.DESSERT);
        pressBack();
        onView(withId(R.id.boissonsButton)).perform(click());
        assertTrue(carteActivity.getSupportFragmentManager().findFragmentByTag(CarteActivity.CHOIX_PLAT_TITLE).isVisible());
        assertEquals(carteActivity.getChoixPlatFragment().getTypePlat(), TypePlatStrings.BOISSON);
        pressBack();
    }

    @Test
    public void onBackPressed() throws Exception {
        assertTrue(carteActivity.isCarteFragmentDisplayed());
        carteActivity.getChoixPlatFragment().setTypePlat(TypePlatStrings.PLAT);
        this.carteActivity.runOnUiThread(new Runnable() {
            public void run() {
                carteActivity.replaceFragment(carteActivity.getChoixPlatFragment(),CarteActivity.CHOIX_PLAT_TITLE);
            }
        });
        pressBack();
        assertTrue(carteActivity.isCarteFragmentDisplayed());
    }

}