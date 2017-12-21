package com.usherbrooke.deliverresto;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usherbrooke.deliverresto.bdd.DbHelper;

import java.util.Arrays;


public class MainActivity extends NavActivity {
//        extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {

    private Button connexionButton;
    private Button mdpOublieButton;
    private Button sinscrireButton;
    private Button commanderButton;

    private EditText courrielEditText;
    private EditText mdpEditText;

    public Button getConnexionButton() {
        return connexionButton;
    }

    public Button getMdpOublieButton() {
        return mdpOublieButton;
    }

    public Button getSinscrireButton() {
        return sinscrireButton;
    }

    public Button getCommanderButton() {
        return commanderButton;
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
        this.setViewLayout(R.layout.content_main);

        //region Récupération des composents (findViewById)
        courrielEditText = (EditText) findViewById(R.id.courrielEditText);
        mdpEditText = (EditText) findViewById(R.id.mdpEditText);

        connexionButton = (Button) findViewById(R.id.connexionButton);
        mdpOublieButton = (Button) findViewById(R.id.mdpOublieButton);
        sinscrireButton = (Button) findViewById(R.id.inscriptionButton);
        commanderButton = (Button) findViewById(R.id.commanderButton);
        //endregion

        //region region Ajout des listener aux boutons

        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConnexion(v);
            }
        });

        mdpOublieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMdpOublie(v);
            }
        });

        sinscrireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSinscrire(v);
            }
        });

        commanderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommander(v);
            }
        });

        //endregion

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        DbHelper.createDbHelper(this);//initialise le système de bdd
    }

    //region Button Listener

    private void onConnexion(View v) {
        String courriel = courrielEditText.getText().toString();
        String mdp = mdpEditText.getText().toString();

        if (DbHelper.getClientService().connectClient(courriel, mdp)) {   //todo compare dans la bdd
            Intent intent = new Intent(this, CarteActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Vous etes connecté", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_LONG).show();
        }
    }

    private void onMdpOublie(View v) {
        show();
    }

    private void onSinscrire(View v) {
        //Start another activity
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
    }

    private void onCommander(View v) {
        Intent intent = new Intent(this, CarteActivity.class);
        startActivity(intent);
    }

    public void show() {

        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.mdpoublie);
        Button bValider = (Button) d.findViewById(R.id.mdpOublieDialogButton);
        final EditText editText = (EditText) d.findViewById(R.id.courrielMdpEditText);
        bValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Veuillez saisir un mail correct", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Envoi d'un mail pour récupérer votre mot de passe", Toast.LENGTH_LONG).show();
                    d.dismiss();
                }
            }
        });
        d.show();
    }
}
