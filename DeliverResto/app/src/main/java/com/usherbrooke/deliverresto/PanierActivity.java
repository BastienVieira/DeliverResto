package com.usherbrooke.deliverresto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.usherbrooke.deliverresto.components.CommandeComponent;
import com.usherbrooke.deliverresto.components.PanierAdapter;
import com.usherbrooke.deliverresto.entities.Commande;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

import java.util.List;

public class PanierActivity extends AppCompatActivity {

    private CommandeComponent currentPanier;
    private MenuItem newDeliveryButton;
    private Commande currentCommand;
    private TextView aucunCurrentPanier;
    private Button commanderButton;
    private ListView lstPlat;
    private PanierAdapter adapter = null;

    public ListView getLstPlat() {
        return lstPlat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        setTitle("Panier");

        aucunCurrentPanier = (TextView) findViewById(R.id.aucun_current_panier);
        aucunCurrentPanier.setBackgroundColor(Color.LTGRAY);
        aucunCurrentPanier.setTextColor(Color.WHITE);

        commanderButton = (Button) findViewById(R.id.commanderButton);
        commanderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommander(v);
            }
        });

        if(Panier.getInstance().getLstPlats().isEmpty()){
            aucunCurrentPanier.setVisibility(View.VISIBLE);
            commanderButton.setVisibility(View.INVISIBLE);
        }
        else{
            aucunCurrentPanier.setVisibility(View.INVISIBLE);
            lstPlat = (ListView) findViewById(R.id.listViewPanier);
            adapter = new PanierAdapter(this,Panier.getInstance().getLstPlats());
            lstPlat.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if((adapter != null)){
            handleAucuneLivraisonText();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }

    private void onCommander(View v){
        //todo A partir d'ici lancer les activity avec un startActivityForResult(intent).
        //todo Permet lorsque la commande est terminée de fermer toutes les activités entre celle la
        //todo et FinalActivity ainsi on évite de recreer CarteActivity et surtout lorsqu'on fait un
        //todo retour on ne retourne pas sur finalActivity
        Intent intent = new Intent(this, PaiementActivity.class);
        startActivity(intent);
    }

    public void onClickMoins(Plat commande) {
        //green - de gauche Ã  droite
        Panier.getInstance().removePlatbyOne(commande);
        handleAucuneLivraisonText();
    }

    public void onClickPlus(Plat commande) {
        //red - de droite Ã  gauche
        Panier.getInstance().addPlat(commande,Panier.getInstance().getHashMapPlats().get(commande)+1);
        handleAucuneLivraisonText();
    }

    private void handleAucuneLivraisonText(){

        if(Panier.getInstance().getLstPlats().size() > 0){
            aucunCurrentPanier.setVisibility(View.INVISIBLE);
            commanderButton.setVisibility(View.VISIBLE);
        }else {
            aucunCurrentPanier.setVisibility(View.VISIBLE);
            commanderButton.setVisibility(View.INVISIBLE);
        }
        List l = Panier.getInstance().getLstPlats();
        adapter.refreshListView(Panier.getInstance().getLstPlats());
    }


}
