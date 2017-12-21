package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.CategoriePlatDao;
import com.usherbrooke.deliverresto.entities.Ingredient;
import com.usherbrooke.deliverresto.entities.Plat;
import com.usherbrooke.deliverresto.entities.PlatDao;
import com.usherbrooke.deliverresto.entities.PlatIngredient;
import com.usherbrooke.deliverresto.entities.PlatIngredientDao;
import com.usherbrooke.deliverresto.entities.TypePlatDao;

import java.util.List;

/**
 * Created by Valentin on 23/11/2017.
 */

public class PlatService {

    private PlatDao platDao;
    private TypePlatDao typePlatDao;
    private CategoriePlatDao categoriePlatDao;
    private IngredientService ingredientService;
    private PlatIngredientDao platIngredientDao;

    public PlatService(PlatDao platDao, TypePlatDao typePlatDao, CategoriePlatDao categoriePlatDao, IngredientService ingredientService, PlatIngredientDao platIngredientDao) {
        this.platDao = platDao;
        this.typePlatDao = typePlatDao;
        this.categoriePlatDao = categoriePlatDao;
        this.ingredientService = ingredientService;
        this.platIngredientDao=platIngredientDao;
    }

    public void addIngredientsToPlat(Plat p, String... ingredients) {
        for (String ingredient : ingredients) {
            Ingredient i = ingredientService.getIngredientByName(ingredient);
            p.getListeIngredients().add(i);
            platIngredientDao.save(new PlatIngredient(null, p.getId(), i.getId()));
        }
        platDao.save(p);
    }

    public List<Plat> getPlatsByType(String type) {
        return platDao.queryDeep(
                "WHERE T0." + TypePlatDao.Properties.Nom.columnName + " = ? ",
                type
        );
    }

//    public List<Plat> getPlatsByCategorie(String cat){
//
//    }

    public Plat getPlatByName(String name) {
        return platDao.queryBuilder()
                .where(PlatDao.Properties.Nom.eq(name))
                .unique();
    }

    public void save(Plat p){
        platDao.save(p);
    }


}
