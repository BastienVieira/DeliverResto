package com.usherbrooke.deliverresto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Client;

public class InscriptionActivity extends AppCompatActivity {

    private Button ajoutMoyenPaiementBouton;
    private Button ajoutAdresseFavoriteBouton;
    private Button terminerInscriptionButton;

    private EditText nomEditText;
    private EditText prenomEditText;
    private EditText telEditText;
    private EditText courrielEditText;
    private EditText mdpEditText;

    public Button getAjoutMoyenPaiementBouton() {
        return ajoutMoyenPaiementBouton;
    }

    public Button getAjoutAdresseFavoriteBouton() {
        return ajoutAdresseFavoriteBouton;
    }

    public Button getTerminerInscriptionButton() {
        return terminerInscriptionButton;
    }

    public EditText getNomEditText() {
        return nomEditText;
    }

    public EditText getPrenomEditText() {
        return prenomEditText;
    }

    public EditText getTelEditText() {
        return telEditText;
    }

    public EditText getCourrielEditText() {
        return courrielEditText;
    }

    public EditText getMdpEditText() {
        return mdpEditText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        //Titre de l'actiivty
        setTitle("Inscription");

        DbHelper.resetSubscribingClient();
        DbHelper.setSubscribingClient(new Client());

        //region Récupération des composents (findViewById)
        ajoutMoyenPaiementBouton = (Button) findViewById(R.id.ajoutMoyenPaiementButton);
        ajoutAdresseFavoriteBouton = (Button) findViewById(R.id.ajoutAdresseFavoriteButton);
        terminerInscriptionButton = (Button) findViewById(R.id.terminerInscriptionButton);

        nomEditText = (EditText) findViewById(R.id.nameEditText);
        prenomEditText = (EditText) findViewById(R.id.prenomEditText);
        telEditText = (EditText) findViewById(R.id.telEditText);
        courrielEditText = (EditText) findViewById(R.id.courrielEditText);
        mdpEditText = (EditText) findViewById(R.id.mdpEditText);
        //endregion

        //region Ajout des listener aux boutons
        ajoutMoyenPaiementBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAjoutMoyenDePaiement(v);
            }
        });

        ajoutAdresseFavoriteBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAjoutAdresseFavorite(v);
            }
        });

        terminerInscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTerminerInscription(v);
            }
        });

        //endregion
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(DbHelper.getSubscribingMoyenPaiement() == null){
            ajoutMoyenPaiementBouton.setVisibility(View.VISIBLE);
        }else {
            ajoutMoyenPaiementBouton.setVisibility(View.GONE);
        }

        if(DbHelper.getSubscribingAdresse() == null){
            ajoutAdresseFavoriteBouton.setVisibility(View.VISIBLE);
        }else {
            ajoutAdresseFavoriteBouton.setVisibility(View.GONE);
        }
    }

        //region Button Listener

    private void onAjoutMoyenDePaiement(View v){
        //Start another activity
        DbHelper.setSubscribingMoyenPaiement(null);
        Intent intent = new Intent(this, AjoutMoyenPaiementActivity.class);
        startActivity(intent);
    }

    private void onAjoutAdresseFavorite(View v){
        //Start another activity
        DbHelper.setSubscribingAdresse(null);
        Intent intent = new Intent(this, AjoutAdresseFavoriteActivity.class);
        startActivity(intent);
    }

    protected boolean isValidEmail(String s) {
        return !s.trim().isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    private void onTerminerInscription(View v){
        if(nomEditText.getText().toString().trim().matches("")){
            Toast.makeText(v.getContext(), "Nom manquant", Toast.LENGTH_SHORT).show();
        } else if(prenomEditText.getText().toString().trim().matches("")){
            Toast.makeText(v.getContext(), "Prénom manquant", Toast.LENGTH_SHORT).show();
        } else if(telEditText.getText().toString().trim().matches("")){
            Toast.makeText(v.getContext(), "Numéro de téléphone manquant", Toast.LENGTH_SHORT).show();
        } else if(!isValidEmail(courrielEditText.getText().toString())){
            Toast.makeText(v.getContext(), "Courriel manquant", Toast.LENGTH_SHORT).show();
        } else if(mdpEditText.getText().toString().trim().matches("")){
            Toast.makeText(v.getContext(), "Mot de passe manquant", Toast.LENGTH_SHORT).show();
        }else {
            //Start another activity

            DbHelper.getSubscribingClient().setNom(nomEditText.getText().toString());
            DbHelper.getSubscribingClient().setPrenom(prenomEditText.getText().toString());
            DbHelper.getSubscribingClient().setNumTelephone(telEditText.getText().toString());
            DbHelper.getSubscribingClient().setCourriel(courrielEditText.getText().toString());
            DbHelper.getSubscribingClient().setMdp(Base64.encode(mdpEditText.getText().toString().getBytes(), Base64.DEFAULT));

            DbHelper.saveSubscribingClient();
            DbHelper.resetSubscribingClient();

            Toast.makeText(this, "Inscription terminée", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    //endregion
}
