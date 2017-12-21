package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.CategoriePlat;
import com.usherbrooke.deliverresto.entities.CategoriePlatDao;

/**
 * Created by Valentin on 23/11/2017.
 */

public class CategoriePlatService {

    private CategoriePlatDao categoriePlatDao;

    public CategoriePlatService(CategoriePlatDao categoriePlatDao) {
        this.categoriePlatDao = categoriePlatDao;
    }

    public CategoriePlat getCategoriePlatByName(String nom){
        return categoriePlatDao.queryBuilder()
                .where(CategoriePlatDao.Properties.Nom.eq(nom))
                .unique();
    }

    public void save (CategoriePlat categoriePlat) {
        categoriePlatDao.save(categoriePlat);
    }

}
