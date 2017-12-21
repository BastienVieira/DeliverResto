package com.usherbrooke.deliverresto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;

public class AjoutAdresseFavoriteActivity extends AppCompatActivity {

    private Button enregistrerAdresseButton;

    private EditText adresseEditText;
    private EditText cpEditText;
    private EditText villeEditText;
    private EditText provinceEditText;
    private EditText indicationEditText;
    private static final AjoutAdresseFavoriteActivity instance = new AjoutAdresseFavoriteActivity();

    public static AjoutAdresseFavoriteActivity getInstance() {
        return instance;
    }

    public Button getEnregistrerAdresseButton() {
        return enregistrerAdresseButton;
    }

    public EditText getAdresseEditText() {
        return adresseEditText;
    }

    public EditText getCpEditText() {
        return cpEditText;
    }

    public EditText getVilleEditText() {
        return villeEditText;
    }

    public EditText getProvinceEditText() {
        return provinceEditText;
    }

    public EditText getIndicationEditText() {
        return indicationEditText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_adresse_favorite);

        //Titre de l'actiivty
        setTitle("Ajout d'une adresse favorite");

        //region Récupération des composents (findViewById)

        enregistrerAdresseButton = (Button) findViewById(R.id.enregistrerAdresseButton);

        adresseEditText = (EditText) findViewById(R.id.adresseEditText);
        cpEditText = (EditText) findViewById(R.id.cpEditText);
        villeEditText = (EditText) findViewById(R.id.villeEditText);
        provinceEditText = (EditText) findViewById(R.id.provinceEditText);
        indicationEditText = (EditText) findViewById(R.id.indicationEditText);

        //endregion

        //region Ajout des listener aux boutons

        enregistrerAdresseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnregistrerAdresse(v);
            }
        });

        //endregion

        if (DbHelper.getConnectedClient() != null && !DbHelper.getConnectedClient().isEmptyAdresse()) {
            setViewDataClient(DbHelper.getConnectedClient());
        }

    }

    //region Button Listener

    protected void onEnregistrerAdresse(View v) {
        if (adresseEditText.getText().toString().trim().matches("")) {
            Toast.makeText(getApplicationContext(), "Rue manquante", Toast.LENGTH_SHORT).show();
        } else if (cpEditText.getText().toString().trim().matches("")) {
            Toast.makeText(getApplicationContext(), "Code Postal manquant", Toast.LENGTH_SHORT).show();
        } else if (villeEditText.getText().toString().trim().matches("")) {
            Toast.makeText(getApplicationContext(), "Ville manquante", Toast.LENGTH_SHORT).show();
        } else if (provinceEditText.getText().toString().trim().matches("")) {
            Toast.makeText(getApplicationContext(), "Province manquante", Toast.LENGTH_SHORT).show();
        } else {

            if (DbHelper.getConnectedClient() != null) {
                if (DbHelper.getConnectedClient().isEmptyAdresse()) {
                   DbHelper.getAdresseService().save(new Adresse(
                            null,
                            adresseEditText.getText().toString(),
                            cpEditText.getText().toString(),
                            villeEditText.getText().toString(),
                            provinceEditText.getText().toString(),
                            indicationEditText.getText().toString())
                    );
                    Adresse adr = DbHelper.getAdresseService().getAdresseByRueVille(adresseEditText.getText().toString(), villeEditText.getText().toString());
                    if(adr != null){
                        DbHelper.getConnectedClient().setAdresse(adr);
                        DbHelper.getConnectedClient().setAdresseId(adr.getId());
                        DbHelper.getClientService().save(DbHelper.getConnectedClient());
                        Toast.makeText(this, "Adresse favorite enregistrée", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Erreur sauvegarde adresse", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, "Adresse favorite enregistrée", Toast.LENGTH_SHORT).show();
                } else {
                    Adresse adr = DbHelper.getAdresseService().getAdresseById(DbHelper.getConnectedClient().getAdresseId());
                    adr.setRue(adresseEditText.getText().toString());
                    adr.setCodePostal(adresseEditText.getText().toString());
                    adr.setVille(villeEditText.getText().toString());
                    adr.setProvince(provinceEditText.getText().toString());
                    adr.setIndication(indicationEditText.getText().toString());
                    DbHelper.getAdresseService().save(adr);
                    DbHelper.getConnectedClient().setAdresse(DbHelper.getAdresseService().getAdresseByRueVille(adr.getRue(), adr.getVille()));
                    Toast.makeText(this, "Modification de votre adresse favorite enregistrée", Toast.LENGTH_SHORT).show();
                }
            } else {
                //SetClientData
                DbHelper.setSubscribingAdresse(new Adresse(
                        null,
                        adresseEditText.getText().toString(),
                        cpEditText.getText().toString(),
                        villeEditText.getText().toString(),
                        provinceEditText.getText().toString(),
                        indicationEditText.getText().toString())
                );
                Toast.makeText(this, "Adresse favorite enregistrée", Toast.LENGTH_SHORT).show();
            }
            //Start another activity
            this.finish();
        }
    }

    //endregion

    protected void setViewDataClient(Client c) {
        Adresse adr = DbHelper.getAdresseService().getAdresseById(c.getAdresseId());
        if (adr != null) {
            adresseEditText.setText(adr.getRue());
            cpEditText.setText(adr.getCodePostal());
            villeEditText.setText(adr.getVille());
            provinceEditText.setText(adr.getProvince());
            indicationEditText.setText(adr.getIndication());
        }

    }
}
