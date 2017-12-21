package com.usherbrooke.deliverresto.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vieir on 21/11/2017.
 */

public class Panier {
    private static final Panier ourInstance = new Panier();

    public static Panier getInstance() {
        return ourInstance;
    }

    private HashMap<Plat,Integer> hashMapPlats = new HashMap<>();

    private List<Plat> lstPlats = new ArrayList<>();

    private Commande currentCommande = new Commande();

    private Client client = new Client();

    private Adresse adrCommande = new Adresse();

    private Panier() {
    }

    public Adresse getAdrCommande() {
        return adrCommande;
    }

    public void setAdrCommande(Adresse adrCommande) {
        this.adrCommande = adrCommande;
    }

    public void addPlat(Plat unplat, int quantity){
        hashMapPlats.put(unplat,quantity);
        if(!lstPlats.contains(unplat)){
            lstPlats.add(unplat);
        }
    }

    public void resetCommande(){
        hashMapPlats = new HashMap<>();
        lstPlats = new ArrayList<>();
        currentCommande = new Commande();
        client = new Client();
        adrCommande = new Adresse();
    }

    public void removePlatbyOne(Plat unplat){
        if(hashMapPlats.containsKey(unplat)) {
            if(getHashMapPlats().get(unplat) != 1){
                addPlat(unplat, getHashMapPlats().get(unplat).intValue()-1);
            }
            else{
                hashMapPlats.remove(unplat);
                for (int i = 0; i < lstPlats.size(); i++) {
                    if(lstPlats.get(i).equals(unplat)){
                        lstPlats.remove(i);
                    }
                }
            }
        }
    }

    public void addPlatsToCommande(){
        Integer quantity;
        currentCommande.resetListePlat();

        for (Plat plat : lstPlats) {
            quantity = hashMapPlats.get(plat);
            for (int i = 0; i < quantity.intValue() ; i++) {
                currentCommande.getListePlat().add(plat);
            }
        }
    }

    public Client getClient() {
        return client;
    }

    public HashMap<Plat, Integer> getHashMapPlats() {
        return hashMapPlats;
    }

    public List<Plat> getLstPlats() {
        return lstPlats;
    }

    public Commande getCurrentCommande() {
        return currentCommande;
    }
}
