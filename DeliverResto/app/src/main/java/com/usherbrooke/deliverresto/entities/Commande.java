package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Valentin on 03/11/2017.
 */
@Entity
public class Commande {

    @Id(autoincrement = true)
    private Long id;

    private Long adresseId;
    @ToOne(joinProperty = "adresseId")
    private Adresse adresse;

    private Long clientId;
    @ToOne(joinProperty = "clientId")
    private Client client;

    private Long magasinId;
    @ToOne(joinProperty = "magasinId")
    private Magasin magasin;

    @ToMany
    @JoinEntity(
            entity = CommandePlat.class,
            sourceProperty = "commandeId",
            targetProperty = "platId"
    )
    private List<Plat> listePlat;

    private Date heureDeLivraison;

    @Transient
    private Boolean bAEmporter;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1380010678)
    private transient CommandeDao myDao;

    @Generated(hash = 1606797184)
    private transient Long adresse__resolvedKey;

    @Generated(hash = 1636229693)
    private transient Long client__resolvedKey;

    @Generated(hash = 199684451)
    private transient Long magasin__resolvedKey;

    @Generated(hash = 320794920)
    public Commande() {
    }

    @Generated(hash = 1678822604)
    public Commande(Long id, Long adresseId, Long clientId, Long magasinId,
            Date heureDeLivraison) {
        this.id = id;
        this.adresseId = adresseId;
        this.clientId = clientId;
        this.magasinId = magasinId;
        this.heureDeLivraison = heureDeLivraison;
    }

    public Boolean getbAEmporter() {
        return bAEmporter;
    }

    public void setbAEmporter(Boolean bAEmporter) {
        this.bAEmporter = bAEmporter;
    }

    public Date getHeureDeLivraison() {
        return this.heureDeLivraison;
    }

    public void setHeureDeLivraison(Date heureDeLivraison) {
        this.heureDeLivraison = heureDeLivraison;
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 379745032)
    public Client getClient() {
        Long __key = this.clientId;
        if (client__resolvedKey == null || !client__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClientDao targetDao = daoSession.getClientDao();
            Client clientNew = targetDao.load(__key);
            synchronized (this) {
                client = clientNew;
                client__resolvedKey = __key;
            }
        }
        return client;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1756624635)
    public void setClient(Client client) {
        synchronized (this) {
            this.client = client;
            clientId = client == null ? null : client.getId();
            client__resolvedKey = clientId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 968035089)
    public List<Plat> getListePlat() {
        if (listePlat == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlatDao targetDao = daoSession.getPlatDao();
            List<Plat> listePlatNew = targetDao._queryCommande_ListePlat(id);
            synchronized (this) {
                if (listePlat == null) {
                    listePlat = listePlatNew;
                }
            }
        }
        return listePlat;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1309337890)
    public synchronized void resetListePlat() {
        listePlat = null;
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

    public Long getAdresseId() {
        return this.adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMagasinId() {
        return this.magasinId;
    }

    public void setMagasinId(Long magasinId) {
        this.magasinId = magasinId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2133962962)
    public Magasin getMagasin() {
        Long __key = this.magasinId;
        if (magasin__resolvedKey == null || !magasin__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MagasinDao targetDao = daoSession.getMagasinDao();
            Magasin magasinNew = targetDao.load(__key);
            synchronized (this) {
                magasin = magasinNew;
                magasin__resolvedKey = __key;
            }
        }
        return magasin;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 527029980)
    public void setMagasin(Magasin magasin) {
        synchronized (this) {
            this.magasin = magasin;
            magasinId = magasin == null ? null : magasin.getId();
            magasin__resolvedKey = magasinId;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 937119593)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommandeDao() : null;
    }

}

//    public void addListePlat(List<Plat> lstPalt) {
//        for (Plat plat : lstPalt) {
//            this.listePlat.add(plat);
//        }
//    }

//    public void setHeureDeLivraison(Date heureDeLivraison) {


//    public boolean isbAEmporter() {
//        return bAEmporter;
//    }
//
//    public void setbAEmporter(boolean bAEmporter) {
//        this.bAEmporter = bAEmporter;
//    }
//
//    public Magasin getMagasin() {
//        return magasin;
//    }
//
//    public void setMagasin(Magasin magasin) {
//        this.magasin = magasin;
//    }
