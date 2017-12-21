package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Valentin on 01/12/2017.
 */
@Entity
public class MenuPlat {

    @Id
    private Long id;

    private Long menuId;

    private Long platId;

    @Generated(hash = 360725340)
    public MenuPlat(Long id, Long menuId, Long platId) {
        this.id = id;
        this.menuId = menuId;
        this.platId = platId;
    }

    @Generated(hash = 1395663135)
    public MenuPlat() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getPlatId() {
        return this.platId;
    }

    public void setPlatId(Long platId) {
        this.platId = platId;
    }
}
