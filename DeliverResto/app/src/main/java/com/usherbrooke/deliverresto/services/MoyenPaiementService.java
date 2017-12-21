package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.MoyenPaiement;
import com.usherbrooke.deliverresto.entities.MoyenPaiementDao;

/**
 * Created by Valentin on 23/11/2017.
 */

public class MoyenPaiementService {

    private MoyenPaiementDao moyenPaiementDao;

    public MoyenPaiementService(MoyenPaiementDao moyenPaiementDao) {
        this.moyenPaiementDao = moyenPaiementDao;
    }

    public void save(MoyenPaiement moyenPaiement){
        moyenPaiementDao.save(moyenPaiement);
    }

    public MoyenPaiement getMoyenPaiementByNumCarte(Long num){
        return moyenPaiementDao.queryBuilder()
                .where(MoyenPaiementDao.Properties.NumCB.eq(num)).unique();
    }

    public MoyenPaiement getMoyenPaiementById(Long id){
        return moyenPaiementDao.queryBuilder()
                .where(MoyenPaiementDao.Properties.Id.eq(id)).unique();
    }

    public void deletAllMoyenPaiement() {
        moyenPaiementDao.deleteAll();
    }

}
