package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.ClientDao;
import com.usherbrooke.deliverresto.entities.MoyenPaiement;

import android.util.Base64;

import java.util.Arrays;

/**
 * Created by Valentin on 23/11/2017.
 */

public class ClientService {

    ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Boolean connectClient(String courriel, String mdp) {
        Client c = clientDao.queryBuilder()
                .where(ClientDao.Properties.Courriel.eq(courriel)).unique();

        if ((c != null) && (Arrays.equals(c.getMdp(), Base64.encode(mdp.getBytes(), Base64.DEFAULT)))) {
            DbHelper.setConnectedClient(c);
            return true;
        } else {
            return false;
        }
    }

    public Client getClientByNomPrenom(String nom, String prenom){
        return clientDao.queryBuilder()
                .where(ClientDao.Properties.Nom.eq(nom),
                        ClientDao.Properties.Prenom.eq(prenom)).unique();
    }

    public void save(Client c){
        clientDao.save(c);
    }

    public void deleteAllClient() {
        clientDao.deleteAll();
    }

    public Client addAdresse(Adresse adr,Client cl) {
        DbHelper.getAdresseService().save(adr);
        adr=DbHelper.getAdresseService().getAdresseByRueVille(adr.getRue(),adr.getVille());
        cl.setAdresseId(adr.getId());
        cl.setAdresse(adr);
        DbHelper.getClientService().save(cl);
        return DbHelper.getClientService().getClientByNomPrenom(cl.getNom(),cl.getPrenom());
    }

    public Client addMoyenPaiement(MoyenPaiement paiement, Client cl) {
        DbHelper.getMoyenPaiementService().save(paiement);
        paiement=DbHelper.getMoyenPaiementService().getMoyenPaiementByNumCarte(paiement.getNumCB());
        cl.setMoyenPaiementId(paiement.getId());
        cl.setMoyenPaiement(paiement);
        DbHelper.getClientService().save(cl);
        return DbHelper.getClientService().getClientByNomPrenom(cl.getNom(),cl.getPrenom());
    }

}
