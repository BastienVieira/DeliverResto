package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.AdresseDao;
import com.usherbrooke.deliverresto.entities.Magasin;
import com.usherbrooke.deliverresto.entities.MagasinDao;

import java.util.List;

/**
 * Created by Valentin on 01/12/2017.
 */

public class MagasinService {

    private MagasinDao magasinDao;
    private AdresseDao adresseDao;

    public MagasinService(MagasinDao magasinDao, AdresseDao adresseDao) {
        this.magasinDao = magasinDao;
        this.adresseDao=adresseDao;
    }

    public List<Magasin> getAllMagasins(){
        return magasinDao.loadAll();
    }

    public Magasin getMagasinByName(String name){
        return magasinDao.queryBuilder()
                .where(MagasinDao.Properties.Nom.eq(name)).unique();
    }
}
