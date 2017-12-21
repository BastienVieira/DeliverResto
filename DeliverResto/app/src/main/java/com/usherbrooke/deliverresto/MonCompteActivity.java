package com.usherbrooke.deliverresto;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.MoyenPaiement;

import java.util.Arrays;
import java.util.Date;

public class MonCompteActivity extends AppCompatActivity {

    //region Variables composants

    private Menu menu;

    private RelativeLayout infosMoyenPaiementLayout;
    private RelativeLayout infosAdresseLayout;
    private RelativeLayout infosUtilisateursLayout;

    private Button ajoutMoyenPaiementBouton;
    private Button ajoutAdresseFavoriteBouton;

    private EditText nomEditText;
    private EditText prenomEditText;
    private EditText telEditText;
    private EditText courrielEditText;
    private EditText mdpEditText;
    private EditText adresseEditText;
    private EditText cpEditText;
    private EditText villeEditText;
    private EditText provinceEditText;
    private EditText indicationEditText;
    private EditText numCBEditText;
    private EditText titulaireEditText;
    private EditText moisExpirationEditText;
    private EditText anneeExpirationEditText;
    private EditText cryptogrammeEditText;

    private TextView adresseFavoriteTextview;
    private TextView moyenPaiementTextview;


    private RadioGroup choixPaiementRadioButton;

    private RadioButton choixCBRadioButton;
    private RadioButton choixPayPalRadioButton;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_compte);

        //Titre de l'actiivty
        setTitle("Mon Compte");

        //region Récupération des composents (findViewById)
        ajoutMoyenPaiementBouton = (Button) findViewById(R.id.ajoutMoyenPaiementButton);
        ajoutAdresseFavoriteBouton = (Button) findViewById(R.id.ajoutAdresseFavoriteButton);

        infosMoyenPaiementLayout = (RelativeLayout) findViewById(R.id.infosMoyenPaiement);
        infosAdresseLayout = (RelativeLayout) findViewById(R.id.infosAdresseLayout);
        infosUtilisateursLayout = (RelativeLayout) findViewById(R.id.infosUtilisateursLayout);


        nomEditText = (EditText) findViewById(R.id.nameEditText);
        prenomEditText = (EditText) findViewById(R.id.prenomEditText);
        telEditText = (EditText) findViewById(R.id.telEditText);
        courrielEditText = (EditText) findViewById(R.id.courrielEditText);
        mdpEditText = (EditText) findViewById(R.id.mdpEditText);

        adresseEditText = (EditText) findViewById(R.id.adresseEditText);
        cpEditText = (EditText) findViewById(R.id.cpEditText);
        villeEditText = (EditText) findViewById(R.id.villeEditText);
        provinceEditText = (EditText) findViewById(R.id.provinceEditText);
        indicationEditText = (EditText) findViewById(R.id.indicationEditText);

        numCBEditText = (EditText) findViewById(R.id.numCBEditText);
        titulaireEditText = (EditText) findViewById(R.id.titulaireEditText);
        moisExpirationEditText = (EditText) findViewById(R.id.moisExpirationEditText);
        anneeExpirationEditText = (EditText) findViewById(R.id.anneeExpirationEditText);
        cryptogrammeEditText = (EditText) findViewById(R.id.cryptogrammeEditText);

        adresseFavoriteTextview = (TextView) findViewById(R.id.adresseFavoriteTextview);
        moyenPaiementTextview = (TextView) findViewById(R.id.moyenPaiementTextview);

        choixPaiementRadioButton = (RadioGroup) findViewById(R.id.choixPaiementRadioButton);

        choixCBRadioButton = (RadioButton) findViewById(R.id.choixCBRadioButton);
        choixPayPalRadioButton = (RadioButton) findViewById(R.id.choixPayPalRadioButton);

        //endregion

        //region Ajout des listener aux boutons

        ajoutMoyenPaiementBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAjoutMoyenDePaiement();
            }
        });

        ajoutAdresseFavoriteBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAjoutAdresseFavorite();
            }
        });

        //endregion


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!DbHelper.getConnectedClient().isEmptyMoyenPaiement()) {
            infosMoyenPaiementLayout.setVisibility(View.VISIBLE);
            ajoutMoyenPaiementBouton.setVisibility(View.GONE);
            moyenPaiementTextview.setVisibility(View.VISIBLE);

        } else {
            infosMoyenPaiementLayout.setVisibility(View.GONE);
            ajoutMoyenPaiementBouton.setVisibility(View.VISIBLE);
            moyenPaiementTextview.setVisibility(View.GONE);
        }

        if (!DbHelper.getConnectedClient().isEmptyAdresse()) {
            infosAdresseLayout.setVisibility(View.VISIBLE);
            ajoutAdresseFavoriteBouton.setVisibility(View.GONE);
            adresseFavoriteTextview.setVisibility(View.VISIBLE);
        } else {
            infosAdresseLayout.setVisibility(View.GONE);
            ajoutAdresseFavoriteBouton.setVisibility(View.VISIBLE);
            adresseFavoriteTextview.setVisibility(View.GONE);
        }

        if (DbHelper.getConnectedClient() != null) {
            setViewDataClient(DbHelper.getConnectedClient());
        }
    }

    //region Button Listener

    private void onAjoutMoyenDePaiement() {
        Intent intent = new Intent(getApplicationContext(), AjoutMoyenPaiementActivity.class);
        startActivity(intent);

        if (!DbHelper.getConnectedClient().isEmptyMoyenPaiement()) {
            infosMoyenPaiementLayout.setVisibility(View.VISIBLE);
            ajoutMoyenPaiementBouton.setVisibility(View.GONE);
            moyenPaiementTextview.setVisibility(View.VISIBLE);
        }
    }

    private void onAjoutAdresseFavorite() {
        Intent intent = new Intent(getApplicationContext(), AjoutAdresseFavoriteActivity.class);
        startActivity(intent);

        if (!DbHelper.getConnectedClient().isEmptyAdresse()) {
            infosAdresseLayout.setVisibility(View.VISIBLE);
            ajoutAdresseFavoriteBouton.setVisibility(View.GONE);
            adresseFavoriteTextview.setVisibility(View.VISIBLE);
        }
    }


//endregion

//region Fonctions supplémentaires

    private void enabledComponent(Boolean enable) {
        if (enable) {
            nomEditText.setEnabled(true);
            prenomEditText.setEnabled(true);
            telEditText.setEnabled(true);
            courrielEditText.setEnabled(true);
            mdpEditText.setEnabled(true);
            adresseEditText.setEnabled(true);
            cpEditText.setEnabled(true);
            villeEditText.setEnabled(true);
            provinceEditText.setEnabled(true);
            indicationEditText.setEnabled(true);
            numCBEditText.setEnabled(true);
            titulaireEditText.setEnabled(true);
            moisExpirationEditText.setEnabled(true);
            anneeExpirationEditText.setEnabled(true);
            cryptogrammeEditText.setEnabled(true);
            choixCBRadioButton.setEnabled(true);
            choixPayPalRadioButton.setEnabled(true);

        } else {
            nomEditText.setEnabled(false);
            prenomEditText.setEnabled(false);
            telEditText.setEnabled(false);
            courrielEditText.setEnabled(false);
            mdpEditText.setEnabled(false);
            adresseEditText.setEnabled(false);
            cpEditText.setEnabled(false);
            villeEditText.setEnabled(false);
            provinceEditText.setEnabled(false);
            indicationEditText.setEnabled(false);
            numCBEditText.setEnabled(false);
            titulaireEditText.setEnabled(false);
            moisExpirationEditText.setEnabled(false);
            anneeExpirationEditText.setEnabled(false);
            cryptogrammeEditText.setEnabled(false);
            choixCBRadioButton.setEnabled(false);
            choixPayPalRadioButton.setEnabled(false);
        }
    }

    protected Client setClientData() {
        Client c = DbHelper.getConnectedClient();

        if (infosUtilisateursLayout.getVisibility() == View.VISIBLE) {

            c.setNom(nomEditText.getText().toString());
            c.setPrenom(prenomEditText.getText().toString());
            c.setNumTelephone(telEditText.getText().toString());
            c.setCourriel(courrielEditText.getText().toString());
            c.setMdp(Base64.encode(mdpEditText.getText().toString().getBytes(), Base64.DEFAULT));
        }
        if (infosMoyenPaiementLayout.getVisibility() == View.VISIBLE) {
            Date dateExpiration = new Date(Integer.parseInt(anneeExpirationEditText.getText().toString()), Integer.parseInt(moisExpirationEditText.getText().toString()), 0);
            MoyenPaiement mp = DbHelper.getMoyenPaiementService().getMoyenPaiementById(DbHelper.getConnectedClient().getMoyenPaiementId());
            mp.setNumCB(Long.parseLong(numCBEditText.getText().toString()));
            mp.setTitulaire(titulaireEditText.getText().toString());
            mp.setDateExpiration(dateExpiration);
            mp.setCryptogramme(Integer.parseInt(cryptogrammeEditText.getText().toString()));
            DbHelper.getMoyenPaiementService().save(mp);
            DbHelper.getConnectedClient().setMoyenPaiement(mp);
        }
        if (infosAdresseLayout.getVisibility() == View.VISIBLE) {
            Adresse adr = DbHelper.getAdresseService().getAdresseById(DbHelper.getConnectedClient().getAdresseId());
            adr.setRue(adresseEditText.getText().toString());
            adr.setCodePostal(cpEditText.getText().toString());
            adr.setVille(villeEditText.getText().toString());
            adr.setProvince(provinceEditText.getText().toString());
            adr.setIndication(indicationEditText.getText().toString());
            DbHelper.getAdresseService().save(adr);
            DbHelper.getConnectedClient().setAdresse(adr);
        }
        return c;
    }

    protected void setViewDataClient(Client c) {
        if (infosMoyenPaiementLayout.getVisibility() == View.VISIBLE && !c.isEmptyMoyenPaiement()) {

            numCBEditText.setText(String.valueOf(c.getMoyenPaiement().getNumCB()));
            titulaireEditText.setText(c.getMoyenPaiement().getTitulaire());
            moisExpirationEditText.setText(String.valueOf(c.getMoyenPaiement().getDateExpiration().getMonth()+1));
            anneeExpirationEditText.setText(String.valueOf(c.getMoyenPaiement().getDateExpiration().getYear()));
            cryptogrammeEditText.setText(String.valueOf(c.getMoyenPaiement().getCryptogramme()));
        }
        if (infosAdresseLayout.getVisibility() == View.VISIBLE && !c.isEmptyAdresse()) {

            adresseEditText.setText(c.getAdresse().getRue());
            cpEditText.setText(c.getAdresse().getCodePostal());
            villeEditText.setText(c.getAdresse().getVille());
            provinceEditText.setText(c.getAdresse().getProvince());
            indicationEditText.setText(c.getAdresse().getIndication());
        }
        if (infosUtilisateursLayout.getVisibility() == View.VISIBLE && !c.isEmptyInscripiton()) {
            nomEditText.setText(c.getNom());
            prenomEditText.setText(c.getPrenom());
            telEditText.setText(c.getNumTelephone());
            courrielEditText.setText(c.getCourriel());
            mdpEditText.setText(new String(Base64.decode(c.getMdp(), Base64.DEFAULT)));
        }
    }

//endregion

//region Menu Inflate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inflate_modifier, menu);

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuInflater inflater;
        switch (item.getItemId()) {
            case R.id.action_modifier:

                //Activer les champs
                enabledComponent(Boolean.TRUE);

                //changer les items du menu
                menu.clear();
                getMenuInflater().inflate(R.menu.menu_inflate_sauvegarder, menu);
                break;

            case R.id.action_sauvegarder:
                onActionSave();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean isValidEmail(String s) {
        return !s.trim().isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    private void onActionSave() {
        if (checkChamps()) {
            DbHelper.setConnectedClient(setClientData());
            DbHelper.getClientService().save(DbHelper.getConnectedClient());

            //Desactiver les champs
            enabledComponent(Boolean.FALSE);

            //changer les items du menu
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_inflate_modifier, menu);
        }
    }

    private boolean checkChamps() {
        boolean b = false;

        if (nomEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nom manquant", Toast.LENGTH_SHORT).show();
        } else if (prenomEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Prénom manquant", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(courrielEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Courriel manquant ou Format non valide", Toast.LENGTH_SHORT).show();
        } else if (mdpEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mot de passe manquant", Toast.LENGTH_SHORT).show();
        } else {
            b = true;
        }

        if (b && infosAdresseLayout.getVisibility() == View.VISIBLE) {
            b = false;
            if (adresseEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Adresse manquante", Toast.LENGTH_SHORT).show();
            } else if (cpEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Code Postal manquant", Toast.LENGTH_SHORT).show();
            } else if (villeEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ville manquante", Toast.LENGTH_SHORT).show();
            } else if (provinceEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Province manquante", Toast.LENGTH_SHORT).show();
            } else {
                b = true;
            }
        }
        if (b && infosMoyenPaiementLayout.getVisibility() == View.VISIBLE) {
            b = false;
            if (numCBEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Numéro de la carte manquant", Toast.LENGTH_SHORT).show();
            } else if (titulaireEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Titualire manquant", Toast.LENGTH_SHORT).show();
            } else if (moisExpirationEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Mois d'expiration manquant", Toast.LENGTH_SHORT).show();
            } else if (anneeExpirationEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Année d'expiration manquant", Toast.LENGTH_SHORT).show();
            } else if (cryptogrammeEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Cryptogramme manquant", Toast.LENGTH_SHORT).show();
            } else {
                b = true;
            }
        }

        return b;
    }

    //endregion
}
