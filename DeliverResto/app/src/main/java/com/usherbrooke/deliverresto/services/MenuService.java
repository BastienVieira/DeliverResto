package com.usherbrooke.deliverresto.services;

import com.usherbrooke.deliverresto.entities.MenuDao;

/**
 * Created by Valentin on 23/11/2017.
 */

public class MenuService {

    private MenuDao menuDao;

    public MenuService(MenuDao menuDao) {
        this.menuDao = menuDao;
    }
}
