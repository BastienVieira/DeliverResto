package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.TypePlat;
import com.usherbrooke.deliverresto.entities.TypePlatDao;

/**
 * Created by Valentin on 23/11/2017.
 */

public class TypePlatService {

    private TypePlatDao typePlatDao;

    public TypePlatService(TypePlatDao typePlatDao) {
        this.typePlatDao = typePlatDao;
    }

    public TypePlat getTypePlatByName(String nom){
        return typePlatDao.queryBuilder()
                .where(TypePlatDao.Properties.Nom.eq(nom))
                .unique();
    }
}
