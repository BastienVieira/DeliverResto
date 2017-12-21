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
public class EtatCommande {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String etat;

    public EtatCommande() {
    }

    @Generated(hash = 1756302482)
    public EtatCommande(Long id, @NotNull String etat) {
        this.id = id;
        this.etat = etat;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
