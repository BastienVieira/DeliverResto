package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.Commande;
import com.usherbrooke.deliverresto.entities.CommandeDao;
import com.usherbrooke.deliverresto.entities.Panier;

/**
 * Created by Valentin on 23/11/2017.
 */

public class CommandeService {

    private CommandeDao commandeDao;

    public CommandeService(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    public void save(Commande commande) {
        commandeDao.save(commande);
    }

    public void saveCommandeFromPanier() {
        Client c;
        if (DbHelper.getClientService().getClientByNomPrenom(Panier.getInstance().getClient().getNom(), Panier.getInstance().getClient().getPrenom()) == null) {
            DbHelper.getClientService().save(Panier.getInstance().getClient());
        }
        c = DbHelper.getClientService().getClientByNomPrenom(Panier.getInstance().getClient().getNom(), Panier.getInstance().getClient().getPrenom());
        Panier.getInstance().getCurrentCommande().setClient(c);
        DbHelper.getAdresseService().save(Panier.getInstance().getAdrCommande());
        Adresse adr = DbHelper.getAdresseService().getAdresseByRueVille(Panier.getInstance().getAdrCommande().getRue(),Panier.getInstance().getAdrCommande().getVille());
        Panier.getInstance().getCurrentCommande().setAdresse(adr);
        Panier.getInstance().getCurrentCommande().setAdresseId(adr.getId());
        DbHelper.getCommandeService().save(Panier.getInstance().getCurrentCommande());
        Panier.getInstance().addPlatsToCommande();
        DbHelper.getCommandeService().save(Panier.getInstance().getCurrentCommande());
    }
}
