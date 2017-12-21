package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.CategoriePlat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexa on 05/12/2017.
 */
public class CategoriePlatTestUnit {
    @Rule
    public ActivityTestRule<FinalActivity> categoriePlatTestRule = new ActivityTestRule<>(FinalActivity.class);
    private String nom;
    private CategoriePlat typePlat;

    @Before
    public void setUp() throws Exception {
        DbHelper.createDbHelper(categoriePlatTestRule.getActivity());
        nom = new String("Boissons");
        typePlat = new CategoriePlat(null,nom);
        DbHelper.getCategoriePlatService().save(typePlat);
        typePlat = DbHelper.getCategoriePlatService().getCategoriePlatByName(nom);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(typePlat.getId());
    }

    @Test
    public void setId() throws Exception {
        Long newId = new Long(45);
        typePlat.setId(newId);
        DbHelper.getCategoriePlatService().save(typePlat);
        CategoriePlat newCat = DbHelper.getCategoriePlatService().getCategoriePlatByName(nom);
        assertEquals(newCat.getId(),newId);
    }

    @Test
    public void getNom() throws Exception {
        assertEquals(typePlat.getNom(),nom);
    }

    @Test
    public void setNom() throws Exception {
        String newNom = new String("Dessert");
        typePlat.setNom(newNom);
        DbHelper.getCategoriePlatService().save(typePlat);
        CategoriePlat newCat = DbHelper.getCategoriePlatService().getCategoriePlatByName(nom);
        assertEquals(typePlat.getNom(),newNom);
    }

}