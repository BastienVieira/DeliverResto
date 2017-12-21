package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Valentin on 01/12/2017.
 */

@Entity
public class PlatIngredient {
    @Id
    private Long id;

    private Long platId;

    private Long ingredientId;

    @Generated(hash = 1911350153)
    public PlatIngredient(Long id, Long platId, Long ingredientId) {
        this.id = id;
        this.platId = platId;
        this.ingredientId = ingredientId;
    }

    @Generated(hash = 729717456)
    public PlatIngredient() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlatId() {
        return this.platId;
    }

    public void setPlatId(Long platId) {
        this.platId = platId;
    }

    public Long getIngredientId() {
        return this.ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
