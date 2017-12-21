package com.usherbrooke.deliverresto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;
import com.usherbrooke.deliverresto.components.MagasinAdapter;
import com.usherbrooke.deliverresto.components.PaimentPlatAdapter;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Client;
import com.usherbrooke.deliverresto.entities.Magasin;
import com.usherbrooke.deliverresto.entities.Panier;

import java.util.ArrayList;
import java.util.List;

public class CommandeAEmporterActivity extends AppCompatActivity {

    private Button btnValidate;
    private Button btnCancel;

    private EditText edtNom;
    private EditText edtPrenom;

    private ListView lstViewMagasins;

    private List<Magasin> lstMagasins;

    private MagasinAdapter mAdapter;

    private int previousItemSelected=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande_aemporter);
        setTitle("A Emporter");

        edtNom = (EditText) findViewById(R.id.nomEditTextEmporter);
        edtPrenom = (EditText) findViewById(R.id.prenomEditTextEmporter);

        btnValidate = (Button) findViewById(R.id.validerCommandeButton);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onValidateClick();
            }
        });

        btnCancel = (Button) findViewById(R.id.annulerCommandeButton);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClick();
            }
        });

        lstMagasins = DbHelper.getMagasinService().getAllMagasins();

        lstViewMagasins = (ListView) findViewById(R.id.listViewMagasins);
        mAdapter = new MagasinAdapter(this, lstMagasins);
        lstViewMagasins.setAdapter(mAdapter);
        lstViewMagasins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addMagasinAdress(lstMagasins.get(position).getNom());
                lstViewMagasins.getChildAt(previousItemSelected).setBackgroundColor(0);
                lstViewMagasins.getChildAt(position).setBackgroundColor(Color.parseColor("#c4e2ff"));
                previousItemSelected = position;
            }
        });
        remplirChamps();
    }

    public void remplirChamps(){
        Client c = DbHelper.getConnectedClient();
        if(c != null && c.getNom() != null){
            edtNom.setText(c.getNom());
            edtPrenom.setText(c.getPrenom());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }

    private boolean checkEmpty() {
        if(edtNom.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Nom manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(edtPrenom.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Prénom manquant", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(Panier.getInstance().getCurrentCommande().getMagasin() == null){
            Toast.makeText(getApplicationContext(), "Selectionner un magasin", Toast.LENGTH_SHORT).show();
            return true;

        }
        return false;
    }

    private void addMagasinAdress(String name){
//        Panier.getInstance().getCurrentCommande().setMagasin(new Magasin(null, name,adresse));
        Panier.getInstance().getCurrentCommande().setMagasin(DbHelper.getMagasinService().getMagasinByName(name));
    }

    private void setClientPanier() {
        Panier.getInstance().getClient().setNom(edtNom.getText().toString());
        Panier.getInstance().getClient().setPrenom(edtPrenom.getText().toString());
        Panier.getInstance().getCurrentCommande().setbAEmporter(true);
    }

    private void onValidateClick(){
        if(!checkEmpty()){
            setClientPanier();
            DbHelper.getCommandeService().saveCommandeFromPanier();
            Panier.getInstance().resetCommande();

            Intent intent = new Intent(this, FinalActivity.class);
            startActivity(intent);
        }
    }

    private void onCancelClick(){
        NavUtils.navigateUpFromSameTask(this);
    }
}
