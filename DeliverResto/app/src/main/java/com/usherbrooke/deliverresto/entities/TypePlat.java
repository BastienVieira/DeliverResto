package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Valentin on 23/11/2017.
 */

@Entity
public class TypePlat {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String nom;

    @Generated(hash = 286080062)
    public TypePlat(Long id, @NotNull String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Generated(hash = 3235297)
    public TypePlat() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}


//public enum TypePlat {
//    ENTREE,
//    PLAT,
//    DESERT,
//    BOISSON,
//    MENU
//}
