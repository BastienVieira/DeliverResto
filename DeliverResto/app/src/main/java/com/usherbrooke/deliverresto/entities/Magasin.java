package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by vieir on 23/11/2017.
 */

@Entity
public class Magasin {

    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String nom;

    private Long adresseId;
    @ToOne(joinProperty = "adresseId")
    private Adresse adresse;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 896990552)
    private transient MagasinDao myDao;

    @Generated(hash = 1606797184)
    private transient Long adresse__resolvedKey;


    @Generated(hash = 1458396132)
    public Magasin(Long id, String nom, Long adresseId) {
        this.id = id;
        this.nom = nom;
        this.adresseId = adresseId;
    }

    @Generated(hash = 1631513691)
    public Magasin() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public Long getAdresseId() {
        return this.adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1155339275)
    public Adresse getAdresse() {
        Long __key = this.adresseId;
        if (adresse__resolvedKey == null || !adresse__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AdresseDao targetDao = daoSession.getAdresseDao();
            Adresse adresseNew = targetDao.load(__key);
            synchronized (this) {
                adresse = adresseNew;
                adresse__resolvedKey = __key;
            }
        }
        return adresse;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1849962847)
    public void setAdresse(Adresse adresse) {
        synchronized (this) {
            this.adresse = adresse;
            adresseId = adresse == null ? null : adresse.getId();
            adresse__resolvedKey = adresseId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1820405764)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMagasinDao() : null;
    }


}
