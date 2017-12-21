package com.usherbrooke.deliverresto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.MoyenPaiement;

import java.util.Date;

public class AjoutMoyenPaiementActivity extends AppCompatActivity {

    private Button enregistrerAdresseButton;

    private EditText numCBEditText;
    private EditText titulaireEditText;
    private EditText moisExpirationEditText;
    private EditText anneeExpirationEditText;
    private EditText cryptogrammeEditText;

    private RadioGroup choixPaiementRadioButton;

    private RadioButton choixCBRadioButton;
    private RadioButton choixPayPalRadioButton;

    public Button getEnregistrerAdresseButton() {
        return enregistrerAdresseButton;
    }

    public EditText getNumCBEditText() {
        return numCBEditText;
    }

    public EditText getTitulaireEditText() {
        return titulaireEditText;
    }

    public EditText getMoisExpirationEditText() {
        return moisExpirationEditText;
    }

    public EditText getAnneeExpirationEditText() {
        return anneeExpirationEditText;
    }

    public EditText getCryptogrammeEditText() {
        return cryptogrammeEditText;
    }

    public RadioGroup getChoixPaiementRadioButton() {
        return choixPaiementRadioButton;
    }

    public RadioButton getChoixCBRadioButton() {
        return choixCBRadioButton;
    }

    public RadioButton getChoixPayPalRadioButton() {
        return choixPayPalRadioButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_ajout_moyen_paiement);

        //Titre de l'actiivty
        setTitle("Ajout d'un Moyen de paiement");

        //region Récupération des composents (findViewById)
        enregistrerAdresseButton = (Button) findViewById(R.id.enregistrerAdresseButton);

        numCBEditText = (EditText) findViewById(R.id.numCBEditText);
        titulaireEditText = (EditText) findViewById(R.id.titulaireEditText);
        moisExpirationEditText = (EditText) findViewById(R.id.moisExpirationEditText);
        anneeExpirationEditText = (EditText) findViewById(R.id.anneeExpirationEditText);
        cryptogrammeEditText = (EditText) findViewById(R.id.cryptogrammeEditText);

        choixPaiementRadioButton = (RadioGroup) findViewById(R.id.choixPaiementRadioButton);

        choixCBRadioButton = (RadioButton) findViewById(R.id.choixCBRadioButton);
        choixPayPalRadioButton = (RadioButton) findViewById(R.id.choixPayPalRadioButton);
        //endregion

        //region Ajout des listener aux boutons

        enregistrerAdresseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnregistrerMoyenPaiement(v);
            }
        });

        //endregion

        //region Remplissage vue

        if (DbHelper.getConnectedClient() != null && !DbHelper.getConnectedClient().isEmptyMoyenPaiement()) {
            setViewDataClient(DbHelper.getConnectedClient());
        }

        //endregion
    }

    //region Button/RadioButton Listener

    private void onEnregistrerMoyenPaiement(View v) {
        if(numCBEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Numéro de la carte manquant", Toast.LENGTH_SHORT).show();
        } else if(titulaireEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Titualire manquant", Toast.LENGTH_SHORT).show();
        } else if(moisExpirationEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mois d'expiration manquant", Toast.LENGTH_SHORT).show();
        } else if(anneeExpirationEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Année d'expiration manquante", Toast.LENGTH_SHORT).show();
        } else if(cryptogrammeEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Cryptogramme manquant", Toast.LENGTH_SHORT).show();
        } else {

            if (DbHelper.getConnectedClient() != null) {
                if(DbHelper.getConnectedClient().isEmptyMoyenPaiement()){
                    Date dateExpiration = new Date(Integer.parseInt(anneeExpirationEditText.getText().toString()),Integer.parseInt(moisExpirationEditText.getText().toString()),0);
                    DbHelper.getMoyenPaiementService().save(new MoyenPaiement(
                            null,
                            "",
                            Long.parseLong(numCBEditText.getText().toString()),titulaireEditText.getText().toString(),
                            dateExpiration,
                            Integer.parseInt(cryptogrammeEditText.getText().toString())
                    ));
                    MoyenPaiement mp = DbHelper.getMoyenPaiementService().getMoyenPaiementByNumCarte(Long.parseLong(numCBEditText.getText().toString()));
                    if(mp != null){
                        DbHelper.getConnectedClient().setMoyenPaiement(mp);
                        DbHelper.getConnectedClient().setMoyenPaiementId(mp.getId());
                        DbHelper.getClientService().save(DbHelper.getConnectedClient());
                        Toast.makeText(this, "Moyen de paiement enregistré", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Erreur sauvegarde moyen de paiement", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    MoyenPaiement mp = DbHelper.getMoyenPaiementService().getMoyenPaiementById(DbHelper.getConnectedClient().getMoyenPaiementId());
                    mp.setNumCB(Long.parseLong(numCBEditText.getText().toString()));
                    mp.setTitulaire(titulaireEditText.getText().toString());
                    mp.setDateExpiration(new Date(Integer.parseInt(anneeExpirationEditText.getText().toString()),Integer.parseInt(moisExpirationEditText.getText().toString()),0));
                    mp.setCryptogramme(Integer.parseInt(cryptogrammeEditText.getText().toString()));
                    DbHelper.getMoyenPaiementService().save(mp);
                    DbHelper.getConnectedClient().setMoyenPaiement(DbHelper.getMoyenPaiementService().getMoyenPaiementByNumCarte(mp.getNumCB()));
                    Toast.makeText(this, "Modification de votre moyen de paiement enregistré", Toast.LENGTH_SHORT).show();
                }
            }else {
                Date dateExpiration = new Date(Integer.parseInt(anneeExpirationEditText.getText().toString()),Integer.parseInt(moisExpirationEditText.getText().toString()),0);
                DbHelper.setSubscribingMoyenPaiement(new MoyenPaiement(
                        null,
                        "",
                        Long.parseLong(numCBEditText.getText().toString()),titulaireEditText.getText().toString(),
                        dateExpiration,
                        Integer.parseInt(cryptogrammeEditText.getText().toString())
                ));
                Toast.makeText(this, "Moyen de paiement enregistrée", Toast.LENGTH_SHORT).show();
            }
            //Start another activity
           this.finish();
        }
    }

    //endregion

    //region Fonctions supplementaires

    protected void setViewDataClient(Client c){
        MoyenPaiement mp = DbHelper.getMoyenPaiementService().getMoyenPaiementById(c.getMoyenPaiementId());
        if(mp != null){
            numCBEditText.setText(String.valueOf(mp.getNumCB()));
            titulaireEditText.setText(mp.getTitulaire());
            moisExpirationEditText.setText(mp.getDateExpiration().getMonth()+1);
            anneeExpirationEditText.setText(mp.getDateExpiration().getYear());
            cryptogrammeEditText.setText(String.valueOf(mp.getCryptogramme()));
        }
    }

    //endregion
}
