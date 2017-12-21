package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.Ingredient;
import com.usherbrooke.deliverresto.entities.IngredientDao;

/**
 * Created by Valentin on 23/11/2017.
 */

public class IngredientService {

    private IngredientDao ingredientDao;

    public IngredientService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public Ingredient getIngredientByName(String nom){
        return ingredientDao.queryBuilder()
                .where(IngredientDao.Properties.Nom.eq(nom))
                .unique();
    }
}
