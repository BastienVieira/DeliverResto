package com.usherbrooke.deliverresto.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Valentin on 03/11/2017.
 */

@Entity
public class MoyenPaiement {

    @Id(autoincrement = true)
    private Long id;

    private String paypalAccount;

    @Unique
    private Long numCB;

    private String titulaire;

    private Date dateExpiration;

    private Integer cryptogramme;

    @Generated(hash = 1343909987)
    public MoyenPaiement(Long id, String paypalAccount, Long numCB,
            String titulaire, Date dateExpiration, Integer cryptogramme) {
        this.id = id;
        this.paypalAccount = paypalAccount;
        this.numCB = numCB;
        this.titulaire = titulaire;
        this.dateExpiration = dateExpiration;
        this.cryptogramme = cryptogramme;
    }

    public MoyenPaiement() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalAccount() {
        return this.paypalAccount;
    }

    public void setPaypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
    }

    public Long getNumCB() {
        return this.numCB;
    }

    public void setNumCB(Long numCB) {
        this.numCB = numCB;
    }

    public String getTitulaire() {
        return this.titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public Date getDateExpiration() {
        return this.dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Integer getCryptogramme() {
        return this.cryptogramme;
    }

    public void setCryptogramme(Integer cryptogramme) {
        this.cryptogramme = cryptogramme;
    }

    public boolean isEmpty(){
        return  numCB.toString().isEmpty() || titulaire.isEmpty() || dateExpiration.toString().isEmpty() || cryptogramme.toString().isEmpty();
    }
}
