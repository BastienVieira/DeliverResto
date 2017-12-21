package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.AdresseDao;

/**
 * Created by Valentin on 23/11/2017.
 */

public class AdresseService {

    AdresseDao adresseDao;

    public AdresseService(AdresseDao adresseDao) {
        this.adresseDao = adresseDao;
    }

    public Adresse getAdresseByRueVille(String rue, String ville) {
        return adresseDao.queryBuilder()
                .where(AdresseDao.Properties.Rue.eq(rue),
                        AdresseDao.Properties.Ville.eq(ville))
                .unique();
    }

    public Adresse getAdresseById(Long id){
        return adresseDao.queryBuilder()
                .where(AdresseDao.Properties.Id.eq(id)).unique();
    }

    public void save(Adresse a){
        adresseDao.save(a);
    }

    public void deleteAllAdresse() {
        adresseDao.deleteAll();
    }
}
