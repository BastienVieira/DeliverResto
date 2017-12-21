package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Valentin on 01/12/2017.
 */

@Entity
public class CommandePlat {
    @Id
    private Long id;

    private Long commandeId;

    private Long platId;

    @Generated(hash = 1098274589)
    public CommandePlat(Long id, Long commandeId, Long platId) {
        this.id = id;
        this.commandeId = commandeId;
        this.platId = platId;
    }

    @Generated(hash = 786675749)
    public CommandePlat() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommandeId() {
        return this.commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public Long getPlatId() {
        return this.platId;
    }

    public void setPlatId(Long platId) {
        this.platId = platId;
    }
}
