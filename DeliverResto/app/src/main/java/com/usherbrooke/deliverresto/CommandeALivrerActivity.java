package com.usherbrooke.deliverresto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.Panier;

public class CommandeALivrerActivity extends AppCompatActivity {

    private ImageButton positionButton;

    private Button validerButton;
    private Button annulerButton;

    private EditText txtAdresse;
    private EditText txtVille;
    private EditText txtCP;
    private EditText txtProvince;
    private EditText txtIndication;
    private EditText txtNom;
    private EditText txtPrenom;

    public ImageButton getPositionButton() {
        return positionButton;
    }

    public Button getValiderButton() {
        return validerButton;
    }

    public Button getAnnulerButton() {
        return annulerButton;
    }

    public EditText getTxtAdresse() {
        return txtAdresse;
    }

    public EditText getTxtVille() {
        return txtVille;
    }

    public EditText getTxtCP() {
        return txtCP;
    }

    public EditText getTxtProvince() {
        return txtProvince;
    }

    public EditText getTxtIndication() {
        return txtIndication;
    }

    public EditText getTxtNom() {
        return txtNom;
    }

    public EditText getTxtPrenom() {
        return txtPrenom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande_alivrer);
        setTitle("En Livraison");

        txtAdresse = (EditText) findViewById(R.id.adresseEditText);
        txtVille = (EditText) findViewById(R.id.villeEditText);
        txtCP = (EditText) findViewById(R.id.cpEditText);
        txtProvince = (EditText) findViewById(R.id.provinceEditText);
        txtIndication = (EditText) findViewById(R.id.indicationEditText);
        txtNom = (EditText) findViewById(R.id.nomEditText);
        txtPrenom = (EditText) findViewById(R.id.prenomEditText);

        //TODO fonction recupération Adresse et Client via FakeBD

        positionButton = (ImageButton) findViewById(R.id.setPositionButton);
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPosition();
            }
        });

        validerButton = (Button) findViewById(R.id.validerLivraisonButton);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmpty()){
                    Toast.makeText(getBaseContext(),"Champs vides",Toast.LENGTH_LONG).show();
                }
                else{
                    setCommandPanier();
                    onValidateClick();
                }
            }
        });

        annulerButton = (Button) findViewById(R.id.annulerLivraisonButton);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnnulerClick();
            }
        });

        remplirChamps();
    }

    public void remplirChamps(){
        Client c = DbHelper.getConnectedClient();

        if(c != null && c.getAdresse() != null){
            txtAdresse.setText(c.getAdresse().getRue());
            txtVille.setText(c.getAdresse().getVille());
            txtCP.setText(c.getAdresse().getCodePostal());
            txtProvince.setText(c.getAdresse().getProvince());
        }
        if(c != null && c.getNom() != null && c.getPrenom() != null){
            txtNom.setText(c.getNom());
            txtPrenom.setText(c.getPrenom());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);

        return true;
    }

    private void getPosition() {

        //TODO Get the real position

        //Simulation
        txtAdresse.setText("2157 Rue Galt Ouest");
        txtVille.setText("Sherbrooke");
        txtCP.setText("J1J 2V2");
        txtProvince.setText("Quebec");
    }

    private boolean checkEmpty() {
        if(txtProvince.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Province manquante", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(txtCP.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Code postal manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(txtVille.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Ville manquante", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(txtNom.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Nom manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(txtPrenom.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Prénom manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void setCommandPanier() {

        Panier.getInstance().setAdrCommande(new Adresse(null, txtAdresse.getText().toString(),
                txtCP.getText().toString(), txtVille.getText().toString(),
                txtProvince.getText().toString(), txtIndication.getText().toString()));

        Panier.getInstance().getCurrentCommande().setbAEmporter(false);

        Panier.getInstance().getClient().setNom(txtNom.getText().toString());
        Panier.getInstance().getClient().setPrenom(txtPrenom.getText().toString());
    }

    private void onValidateClick() {
        Intent intent = new Intent(this, MoyenPaimentLivraisonActivity.class);
        startActivity(intent);
    }

    private void onAnnulerClick() {
        NavUtils.navigateUpFromSameTask(this);
    }

}
