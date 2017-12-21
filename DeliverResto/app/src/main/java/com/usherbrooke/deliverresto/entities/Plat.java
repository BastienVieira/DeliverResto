package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;


/**
 * Created by Valentin on 03/11/2017.
 */

@Entity()
public class Plat {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Unique
    private String nom;

    @NotNull
    private Float prix;

    private Long typeId;

    @NotNull
    @ToOne(joinProperty = "typeId")
    private TypePlat type;

    private Long categorieId;

    @NotNull
    @ToOne(joinProperty = "categorieId")
    private CategoriePlat categorie;

    @Transient
    private Boolean isReady = false;

    private Integer drawable;

    @ToMany
    @JoinEntity(
            entity = PlatIngredient.class,
            sourceProperty = "platId",
            targetProperty = "ingredientId"
    )
    private List<Ingredient> listeIngredients;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 232047184)
    private transient PlatDao myDao;

    @Generated(hash = 506996655)
    private transient Long type__resolvedKey;

    @Generated(hash = 1461567194)
    private transient Long categorie__resolvedKey;

    public Plat() {
    }

    @Generated(hash = 1240597191)
    public Plat(Long id, @NotNull String nom, @NotNull Float prix, Long typeId, Long categorieId,
            Integer drawable) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.typeId = typeId;
        this.categorieId = categorieId;
        this.drawable = drawable;
    }

    public Boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1969774094)
    public List<Ingredient> getListeIngredients() {
        if (listeIngredients == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IngredientDao targetDao = daoSession.getIngredientDao();
            List<Ingredient> listeIngredientsNew = targetDao
                    ._queryPlat_ListeIngredients(id);
            synchronized (this) {
                if (listeIngredients == null) {
                    listeIngredients = listeIngredientsNew;
                }
            }
        }
        return listeIngredients;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1139880734)
    public synchronized void resetListeIngredients() {
        listeIngredients = null;
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

    public Float getPrix() {
        return this.prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 612815834)
    public TypePlat getType() {
        Long __key = this.typeId;
        if (type__resolvedKey == null || !type__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TypePlatDao targetDao = daoSession.getTypePlatDao();
            TypePlat typeNew = targetDao.load(__key);
            synchronized (this) {
                type = typeNew;
                type__resolvedKey = __key;
            }
        }
        return type;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1110919380)
    public void setType(TypePlat type) {
        synchronized (this) {
            this.type = type;
            typeId = type == null ? null : type.getId();
            type__resolvedKey = typeId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 873449744)
    public CategoriePlat getCategorie() {
        Long __key = this.categorieId;
        if (categorie__resolvedKey == null || !categorie__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriePlatDao targetDao = daoSession.getCategoriePlatDao();
            CategoriePlat categorieNew = targetDao.load(__key);
            synchronized (this) {
                categorie = categorieNew;
                categorie__resolvedKey = __key;
            }
        }
        return categorie;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 175000911)
    public void setCategorie(CategoriePlat categorie) {
        synchronized (this) {
            this.categorie = categorie;
            categorieId = categorie == null ? null : categorie.getId();
            categorie__resolvedKey = categorieId;
        }
    }

    public long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getCategorieId() {
        return this.categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    @Override
    public String toString() {
        return nom;
    }

    public Integer getDrawable() {
        return this.drawable;
    }

    public void setDrawable(Integer drawable) {
        this.drawable = drawable;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1134682946)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlatDao() : null;
    }

}
