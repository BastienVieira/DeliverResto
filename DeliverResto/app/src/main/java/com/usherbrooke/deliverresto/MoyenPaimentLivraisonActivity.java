package com.usherbrooke.deliverresto;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.MoyenPaiement;
import com.usherbrooke.deliverresto.entities.Panier;

import java.util.Date;

public class MoyenPaimentLivraisonActivity extends AppCompatActivity {

    private Button btnValider;
    private Button btnCancel;
    private Button btnPaypal;

    private EditText edtNumCarte;
    private EditText edtTitulaireCarte;
    private EditText edtMoisExpiration;
    private EditText edtAnneeExpiration;
    private EditText edtCrypto;

    public Button getBtnValider() {
        return btnValider;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public Button getBtnPaypal() {
        return btnPaypal;
    }

    public EditText getEdtNumCarte() {
        return edtNumCarte;
    }

    public EditText getEdtTitulaireCarte() {
        return edtTitulaireCarte;
    }

    public EditText getEdtMoisExpiration() {
        return edtMoisExpiration;
    }

    public EditText getEdtAnneeExpiration() {
        return edtAnneeExpiration;
    }

    public EditText getEdtCrypto() {
        return edtCrypto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyen_paiment_livraison);
        setTitle("Moyen de Paiement");

        btnPaypal = (Button) findViewById(R.id.PayerPaypalButton);
        btnPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Do something with Paypal
            }
        });

        btnCancel = (Button) findViewById(R.id.annulerPaimentButton);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClick();
            }
        });

        btnValider = (Button) findViewById(R.id.validerPaimentButton);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onValidateClick();
            }
        });


        edtNumCarte = (EditText) findViewById(R.id.numCBEditText);
        edtTitulaireCarte = (EditText) findViewById(R.id.titulaireEditText);
        edtMoisExpiration = (EditText) findViewById(R.id.moisExpirationEditText);
        edtAnneeExpiration = (EditText) findViewById(R.id.anneeExpirationEditText);
        edtCrypto = (EditText) findViewById(R.id.cryptogrammeEditText);

        //TODO Set EditText with FakeDB real informations
        remplirChamps();
    }

    public void remplirChamps(){
        Client c = DbHelper.getConnectedClient();
        if(c != null && c.getMoyenPaiement() != null) {
            edtNumCarte.setText(c.getMoyenPaiement().getNumCB().toString());
            edtTitulaireCarte.setText(c.getMoyenPaiement().getTitulaire());
            edtMoisExpiration.setText(c.getMoyenPaiement().getDateExpiration().getMonth()+1);
            edtAnneeExpiration.setText(c.getMoyenPaiement().getDateExpiration().getYear());
            edtCrypto.setText(c.getMoyenPaiement().getCryptogramme().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);

        return true;
    }

    private boolean checkEmpty() {
        if(edtNumCarte.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Numéro de carte manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(edtTitulaireCarte.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Titulaire de carte manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(edtMoisExpiration.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Mois d'expiration manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(edtMoisExpiration.getText().length()!=2){
            Toast.makeText(getApplicationContext(), "Erreur saisie Mois", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(edtAnneeExpiration.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Année d'expiration manquante", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(edtAnneeExpiration.getText().length()!=4){
            Toast.makeText(getApplicationContext(), "Erreur saisie Année", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(edtCrypto.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Cryptographie manquante", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(edtCrypto.getText().length()!=3){
            Toast.makeText(getApplicationContext(), "Erreur saisie Cryptographie", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void setPaimentPanier() {
        Date dateExpiration = new Date(Integer.parseInt(edtAnneeExpiration.getText().toString()),Integer.parseInt(edtMoisExpiration.getText().toString()),0);
        DbHelper.getMoyenPaiementService().save(new MoyenPaiement(null,"",
                        Long.parseLong(edtNumCarte.getText().toString()),
                        edtTitulaireCarte.getText().toString(),
                        dateExpiration,
                        Integer.parseInt(edtCrypto.getText().toString())));

        Panier.getInstance().getClient().setMoyenPaiement(DbHelper.getMoyenPaiementService().getMoyenPaiementByNumCarte( Long.parseLong(edtNumCarte.getText().toString())));
    }

    private void onValidateClick() {
        if(!checkEmpty()){
            DbHelper.getCommandeService().saveCommandeFromPanier();
            Panier.getInstance().resetCommande();
            setPaimentPanier();
            Intent intent = new Intent(this, FinalActivity.class);
            startActivity(intent);
            //TODO send Panier to BDD
        }
    }

    private void onCancelClick(){
        NavUtils.navigateUpFromSameTask(this);
    }

    private void onPaypalClick(){
        //TODO Real Task;
//        Intent intent = new Intent(this, FinalActivity.class);
//        startActivity(intent);
    }
}
