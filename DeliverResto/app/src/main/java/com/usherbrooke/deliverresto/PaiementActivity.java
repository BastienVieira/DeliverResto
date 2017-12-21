package com.usherbrooke.deliverresto;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.usherbrooke.deliverresto.components.PaimentPlatAdapter;
import com.usherbrooke.deliverresto.entities.Plat;

import java.util.Calendar;
import com.usherbrooke.deliverresto.entities.Adresse;
import com.usherbrooke.deliverresto.entities.Commande;
import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PaiementActivity extends AppCompatActivity {

    private TextView txtTotal;
    private EditText editHour;
    private ImageButton btnHeure;
    private Button aLiverButton;
    private Button aEmporterButton;
    private ListView lstPlat;
    private RadioButton rdButtonTot;
    private PaimentPlatAdapter adapter = null;

    private String time = "";
    private float sum = 0;

    public TextView getTxtTotal() {
        return txtTotal;
    }

    public EditText getEditHour() {
        return editHour;
    }

    public ImageButton getBtnHeure() {
        return btnHeure;
    }

    public Button getaLiverButton() {
        return aLiverButton;
    }

    public Button getaEmporterButton() {
        return aEmporterButton;
    }

    public ListView getLstPlat() {
        return lstPlat;
    }

    public RadioButton getRdButtonTot() {
        return rdButtonTot;
    }

    public PaimentPlatAdapter getAdapter() {
        return adapter;
    }

    public String getTime() {
        return time;
    }

    public float getSum() {
        return sum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);
        setTitle("Paiement");

        lstPlat = (ListView) findViewById(R.id.listViewRecapitulatif);
        adapter = new PaimentPlatAdapter(this, Panier.getInstance().getLstPlats());
        lstPlat.setAdapter(adapter);

        for (Plat plat : Panier.getInstance().getLstPlats()) {
            sum = sum + (plat.getPrix() * Panier.getInstance().getHashMapPlats().get(plat).intValue());
        }
        txtTotal = (TextView) findViewById(R.id.textSumTotal);
        txtTotal.setText(String.valueOf(sum) + "$");

        rdButtonTot = (RadioButton) findViewById(R.id.radioPlusTot);
        rdButtonTot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdButtonTot.isChecked()){
                    editHour.setText("Heure");
                    editHour.setEnabled(false);
                }
            }
        });

        btnHeure = (ImageButton) findViewById(R.id.setClockButton);
        btnHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdButtonTot.setChecked(false);
                editHour.setEnabled(true);
                showDialog();
            }
        });

        editHour = (EditText) findViewById(R.id.editHour);

        aLiverButton = (Button) findViewById(R.id.livrerButton);
        aLiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLivraisonClick();
            }
        });

        aEmporterButton = (Button) findViewById(R.id.emporterButton);
        aEmporterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmporterCLick();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty, menu);

        return true;
    }

    private void setTime(String hours, String minutes){
        editHour.setText(hours + ":" + minutes);
    }

    public void showDialog() {

        final Dialog d = new Dialog(this);
        d.setTitle("Choisir votre heure");
        d.setContentView(R.layout.hour_dialog);
        Button bValider = (Button) d.findViewById(R.id.hourValiderButton);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPickerHours);
        np.setMaxValue(23);
        np.setMinValue(0);
        np.setValue(Integer.valueOf(12));
        np.setWrapSelectorWheel(false);

        final NumberPicker np2 = (NumberPicker) d.findViewById(R.id.numberPickerMinutes);
        np2.setMaxValue(59);
        np2.setMinValue(0);
        np2.setValue(Integer.valueOf(30));
        np2.setWrapSelectorWheel(false);

        bValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(String.valueOf(np.getValue()), String.valueOf(np2.getValue()));
                d.dismiss();
            }
        });
        d.show();
    }

    private void onLivraisonClick(){
        setCommandPanier();
        Intent intent = new Intent(this, CommandeALivrerActivity.class);
        startActivity(intent);
    }

    private void onEmporterCLick(){
        setCommandPanier();
        Intent intent = new Intent(this, CommandeAEmporterActivity.class);
        startActivity(intent);
    }

    private void setCommandPanier(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-6:00"));
        if(rdButtonTot.isChecked()){
//            int currentHour = cal.get(Calendar.HOUR);
//            int currentMinutes = cal.get(Calendar.MINUTE);
            Panier.getInstance().getCurrentCommande().setHeureDeLivraison(cal.getTime());
        }
        else {
            String time[] = editHour.getText().toString().split(":");
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0]));
            cal.set(Calendar.MINUTE, Integer.valueOf(time[1]));

            Panier.getInstance().getCurrentCommande().setHeureDeLivraison(cal.getTime());
        }
    }
}
