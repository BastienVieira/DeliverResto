package com.usherbrooke.deliverresto;

import android.support.test.rule.ActivityTestRule;

import com.usherbrooke.deliverresto.AjoutAdresseFavoriteActivity;
import com.usherbrooke.deliverresto.FinalActivity;
import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.*;

/**
 * Created by alexa on 04/12/2017.
 */
public class AdresseTestUnit {

    @Rule
    public ActivityTestRule<FinalActivity> AdresseTestRule = new ActivityTestRule<>(FinalActivity.class);
    private Adresse adr;
    private String rue;
    private String cp;
    private String ville;
    private String province;
    private String indic;

    @Before
    public void setUp() throws Exception {
        DbHelper.createDbHelper(AdresseTestRule.getActivity());
        rue = new String("1 rue Fraser");
        cp = new String("J1J2V2");
        ville = new String("Sherbrooke");
        province = new String("QC");
        indic = new String("yo");
        adr = new Adresse(null,rue,cp,ville,province,indic);
        DbHelper.getAdresseService().save(adr);
        adr = DbHelper.getAdresseService().getAdresseByRueVille(rue,ville);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(adr.getId());
    }

    @Test
    public void setId() throws Exception {
        Long newId = new Long(45);
        adr.setId(newId);
        DbHelper.getAdresseService().save(adr);
        Adresse newAdr = DbHelper.getAdresseService().getAdresseByRueVille(rue,ville);
        assertEquals(newAdr.getId(),newId);
    }

    @Test
    public void getRue() throws Exception {
        assertEquals(adr.getRue(),rue);
    }

    @Test
    public void setRue() throws Exception {
        String newRue = new String("40 rue Wilson");
        adr.setRue(newRue);
        DbHelper.getAdresseService().save(adr);
        Adresse newAdr = DbHelper.getAdresseService().getAdresseByRueVille(newRue,ville);
        assertEquals(newAdr.getRue(),newRue);
    }

    @Test
    public void getCodePostal() throws Exception {
        assertEquals(adr.getCodePostal(),cp);
    }

    @Test
    public void setCodePostal() throws Exception {
        String newCp = new String("J1J3V3");
        adr.setCodePostal(newCp);
        DbHelper.getAdresseService().save(adr);
        Adresse newAdr = DbHelper.getAdresseService().getAdresseByRueVille(rue,ville);
        assertEquals(newAdr.getCodePostal(),newCp);
    }

    @Test
    public void getVille() throws Exception {
        assertEquals(adr.getVille(),ville);
    }

    @Test
    public void setVille() throws Exception {
        String newVille = new String("Toronto");
        adr.setVille(newVille);
        DbHelper.getAdresseService().save(adr);
        Adresse newAdr = DbHelper.getAdresseService().getAdresseByRueVille(rue,newVille);
        assertEquals(newAdr.getVille(),newVille);
    }

    @Test
    public void getProvince() throws Exception {
        assertEquals(adr.getProvince(),province);
    }

    @Test
    public void setProvince() throws Exception {
        String newProvince = new String("OT");
        adr.setProvince(newProvince);
        DbHelper.getAdresseService().save(adr);
        Adresse newAdr = DbHelper.getAdresseService().getAdresseByRueVille(rue,ville);
        assertEquals(newAdr.getProvince(),newProvince);
    }

    @Test
    public void getIndication() throws Exception {
        assertEquals(adr.getIndication(),indic);
    }

    @Test
    public void setIndication() throws Exception {
        String newIndic = new String("salut");
        adr.setIndication(newIndic);
        DbHelper.getAdresseService().save(adr);
        Adresse newAdr = DbHelper.getAdresseService().getAdresseByRueVille(rue,ville);
        assertEquals(newAdr.getIndication(),newIndic);
    }

    @Test
    public void isEmpty() throws Exception {
        assertFalse(adr.isEmpty());
        adr = new Adresse(null,"","","","","");
        assertTrue(adr.isEmpty());
    }

}