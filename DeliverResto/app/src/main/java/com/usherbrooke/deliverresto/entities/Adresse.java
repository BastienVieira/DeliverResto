package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Valentin on 03/11/2017.
 */

@Entity
public class Adresse {

    @Id(autoincrement = true)
    private Long id;

    private String rue;

    private String codePostal;

    private String ville;

    private String province;

    private String indication;

    @Generated(hash = 196583319)
    public Adresse(Long id, String rue, String codePostal, String ville,
            String province, String indication) {
        this.id = id;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.province = province;
        this.indication = indication;
    }

    public Adresse() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRue() {
        return this.rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIndication() {
        return this.indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public boolean isEmpty(){
        return rue.isEmpty() || codePostal.isEmpty() || ville.isEmpty() || province.isEmpty() || indication.isEmpty();
    }
}
