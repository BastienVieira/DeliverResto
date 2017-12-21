package com.usherbrooke.deliverresto;

import android.hardware.camera2.DngCreator;
import android.support.test.rule.ActivityTestRule;
import android.util.Base64;

import com.usherbrooke.deliverresto.AjoutAdresseFavoriteActivity;
import com.usherbrooke.deliverresto.FinalActivity;
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
 * Created by alexa on 04/12/2017.
 */
public class ClientTestUnit {

    @Rule
    public ActivityTestRule<FinalActivity> ClientTestRule = new ActivityTestRule<>(FinalActivity.class);
    String nom;
    String prenom;
    String courriel;
    byte [] mdp;
    String numTel;
    Client cl;
    MoyenPaiement paiement;

    @Before
    public void setUp() throws Exception {
        DbHelper.createDbHelper(ClientTestRule.getActivity());
        nom = new String ("DUPONT");
        prenom = new String("Antoine");
        courriel = new String("antoine.dupont@gmail.com");
        mdp = Base64.encode("password".getBytes(),Base64.DEFAULT);
        numTel = new String("1234567");
        cl = new Client(null,nom,prenom);
        cl.setCourriel(courriel);
        cl.setMdp(mdp);
        cl.setNumTelephone(numTel);
        DbHelper.getClientService().save(cl);
        cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
    }

    @After
    public void tearDown() throws Exception {
        DbHelper.deleteData();
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(cl.getId());
    }

    @Test
    public void setId() throws Exception {
        Long newId = new Long(45);
        cl.setId(newId);
        DbHelper.getClientService().save(cl);
        Client newCl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertEquals(newCl.getId(),newId);
    }

    @Test
    public void getNom() throws Exception {
        assertEquals(cl.getNom(),nom);
    }

    @Test
    public void setNom() throws Exception {
        String newNom = new String("MARTIN");
        cl.setNom(newNom);
        DbHelper.getClientService().save(cl);
        Client newCl = DbHelper.getClientService().getClientByNomPrenom(newNom,prenom);
        assertEquals(newCl.getNom(),newNom);
    }

    @Test
    public void getPrenom() throws Exception {
        assertEquals(cl.getNom(),nom);
    }

    @Test
    public void setPrenom() throws Exception {
        String newPrenom = new String("Martin");
        cl.setPrenom(newPrenom);
        DbHelper.getClientService().save(cl);
        Client newCl = DbHelper.getClientService().getClientByNomPrenom(nom,newPrenom);
        assertEquals(newCl.getPrenom(),newPrenom);
    }

    @Test
    public void getCourriel() throws Exception {
        assertEquals(cl.getCourriel(),courriel);
    }

    @Test
    public void setCourriel() throws Exception {
        String newCourriel = new String("martin.matin@gmail.com");
        cl.setCourriel(newCourriel);
        DbHelper.getClientService().save(cl);
        Client newCl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertEquals(newCl.getCourriel(),newCourriel);
    }

    @Test
    public void getMdp() throws Exception {
        assertEquals(cl.getMdp(),mdp);
    }

    @Test
    public void setMdp() throws Exception {
        byte [] newMdp = Base64.encode("motdepasse".getBytes(),Base64.DEFAULT);
        cl.setMdp(newMdp);
        DbHelper.getClientService().save(cl);
        Client newCl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertEquals(newCl.getMdp(),newMdp);
    }

    @Test
    public void getNumTelephone() throws Exception {
        assertEquals(cl.getNumTelephone(),numTel);
    }

    @Test
    public void setNumTelephone() throws Exception {
        String newTel = new String("9876543");;
        cl.setNumTelephone(newTel);
        DbHelper.getClientService().save(cl);
        Client newCl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertEquals(newCl.getNumTelephone(),newTel);
    }

    @Test
    public void isEmptyInscripiton() throws Exception {
        assertFalse(cl.isEmptyInscripiton());
        cl.setCourriel("");
        DbHelper.getClientService().save(cl);
        cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertTrue(cl.isEmptyInscripiton());
        cl = new Client();
        assertTrue(cl.isEmptyInscripiton());
    }

    @Test
    public void isEmptyMoyenPaiement() throws Exception {
        assertTrue(cl.isEmptyMoyenPaiement());
        MoyenPaiement paiement = new MoyenPaiement(null,"antoine.dupont@gmail.fr",new Long(1234),"MR.DUPONT",new Date(450L),new Integer(123));
        cl = DbHelper.getClientService().addMoyenPaiement(paiement,cl);
        assertFalse(cl.isEmptyMoyenPaiement());
        cl.getMoyenPaiement().setTitulaire("");
        DbHelper.getClientService().save(cl);
        assertTrue(cl.isEmptyMoyenPaiement());
    }

    @Test
    public void isEmptyAdresse() throws Exception {
        assertTrue(cl.isEmptyAdresse());
        Adresse adr = new Adresse(null,"1 rue Fraser","J1J2V2","Sherbrooke","QC","yo");
        cl = DbHelper.getClientService().addAdresse(adr,cl);
        assertFalse(cl.isEmptyAdresse());
        cl.getAdresse().setProvince("");
        DbHelper.getClientService().save(cl);
        cl = DbHelper.getClientService().getClientByNomPrenom(nom,prenom);
        assertTrue(cl.isEmptyAdresse());
    }

    @Test
    public void setAndgetAdresseId() throws Exception {
        Adresse adr = new Adresse(null,"1 rue Fraser","J1J2V2","Sherbrooke","QC","yo");
        cl = DbHelper.getClientService().addAdresse(adr,cl);
        adr = DbHelper.getAdresseService().getAdresseByRueVille("1 rue Fraser","Sherbrooke");
        assertEquals(adr.getId(),cl.getAdresseId());
    }

    @Test
    public void setAndgetMoyenPaiementId() throws Exception {
        MoyenPaiement paiement = new MoyenPaiement(null,"antoine.dupont@gmail.fr",new Long(5678),"MR.DUPONT",new Date(450L),new Integer(123));
        cl = DbHelper.getClientService().addMoyenPaiement(paiement,cl);
        paiement = DbHelper.getMoyenPaiementService().getMoyenPaiementByNumCarte(new Long(5678));
        assertEquals(paiement.getId(),cl.getMoyenPaiementId());
    }

    @Test
    public void setAndgetAdresse() throws Exception {
        Adresse adr = new Adresse(null,"1 rue Fraser","J1J2V2","Sherbrooke","QC","yo");
        cl = DbHelper.getClientService().addAdresse(adr,cl);
        assertEquals(adr,cl.getAdresse());
    }

    @Test
    public void setAndgetMoyenPaiement() throws Exception {
        MoyenPaiement paiement = new MoyenPaiement(null,"antoine.dupont@gmail.fr",new Long(9101112),"MR.DUPONT",new Date(450L),new Integer(123));
        cl = DbHelper.getClientService().addMoyenPaiement(paiement,cl);
        assertEquals(paiement,cl.getMoyenPaiement());
    }

    @Test
    public void delete() throws Exception {
        cl.delete();
        assertNull(DbHelper.getClientService().getClientByNomPrenom(nom,prenom));
    }

    @Test
    public void refresh() throws Exception {
        Adresse adr = new Adresse(null,"1 rue Fraser","J1J2V2","Sherbrooke","QC","yo");
        cl = DbHelper.getClientService().addAdresse(adr,cl);
        adr = new Adresse(null,"2 rue Wilson","J1J3V3","Toronto","OT","salut");
        cl.setAdresseId(adr.getId());
        cl.setAdresse(adr);
        cl.refresh();
        assertNotEquals(adr,cl.getAdresse());
    }

}