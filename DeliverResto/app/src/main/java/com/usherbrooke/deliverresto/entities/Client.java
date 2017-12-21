package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Arrays;

/**
 * Created by Valentin on 03/11/2017.
 */

@Entity
public class Client {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @Unique
    private String courriel;

    private byte[] mdp;
    //API 26
    //    byte[] encodedBytes = Base64.getEncoder().encode("Test".getBytes());
    //System.out.println("encodedBytes " + new String(encodedBytes));
    //    byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
    //System.out.println("decodedBytes " + new String(decodedBytes));

    //API 19
    //Base64.encode(mdp.getBytes(), Base64.DEFAULT)
    //Base64.decode(value, Base64.DEFAULT)

    private String numTelephone;

    private Long adresseId;
    @ToOne(joinProperty = "adresseId")
    private Adresse adresse;

    private Long moyenPaiementId;
    @ToOne(joinProperty = "moyenPaiementId")
    private MoyenPaiement moyenPaiement;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 883866064)
    private transient ClientDao myDao;

    @Generated(hash = 1606797184)
    private transient Long adresse__resolvedKey;

    @Generated(hash = 1753558640)
    private transient Long moyenPaiement__resolvedKey;

    @Generated(hash = 979672196)
    public Client(Long id, @NotNull String nom, @NotNull String prenom, String courriel,
            byte[] mdp, String numTelephone, Long adresseId, Long moyenPaiementId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.mdp = mdp;
        this.numTelephone = numTelephone;
        this.adresseId = adresseId;
        this.moyenPaiementId = moyenPaiementId;
    }

    public Client(Long id, @NotNull String nom, @NotNull String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Client() {
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

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCourriel() {
        return this.courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public byte[] getMdp() {
        return this.mdp;
    }

    public void setMdp(byte[] mdp) {
        this.mdp = mdp;
    }

    public String getNumTelephone() {
        return this.numTelephone;
    }

    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }

    public boolean isEmptyInscripiton(){
        if(!(nom == null || prenom == null || courriel == null || mdp == null))
            return nom.isEmpty() || prenom.isEmpty() || courriel.isEmpty() || Arrays.toString(mdp).isEmpty();
        return true;
    }

    public boolean isEmptyMoyenPaiement(){
        if(moyenPaiement != null)
            return moyenPaiement.isEmpty();
        return true;
    }

    public boolean isEmptyAdresse(){
        if(adresse != null)
            return adresse.isEmpty();
        return true;
    }

    public Long getAdresseId() {
        return this.adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    public Long getMoyenPaiementId() {
        return this.moyenPaiementId;
    }

    public void setMoyenPaiementId(Long moyenPaiementId) {
        this.moyenPaiementId = moyenPaiementId;
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
    @Generated(hash = 988379979)
    public MoyenPaiement getMoyenPaiement() {
        Long __key = this.moyenPaiementId;
        if (moyenPaiement__resolvedKey == null || !moyenPaiement__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MoyenPaiementDao targetDao = daoSession.getMoyenPaiementDao();
            MoyenPaiement moyenPaiementNew = targetDao.load(__key);
            synchronized (this) {
                moyenPaiement = moyenPaiementNew;
                moyenPaiement__resolvedKey = __key;
            }
        }
        return moyenPaiement;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 263029765)
    public void setMoyenPaiement(MoyenPaiement moyenPaiement) {
        synchronized (this) {
            this.moyenPaiement = moyenPaiement;
            moyenPaiementId = moyenPaiement == null ? null : moyenPaiement.getId();
            moyenPaiement__resolvedKey = moyenPaiementId;
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
    @Generated(hash = 2049572258)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getClientDao() : null;
    }
}
