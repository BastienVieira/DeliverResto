package com.usherbrooke.deliverresto.components;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.Ingredient;

import android.widget.Toast;

import com.usherbrooke.deliverresto.entities.Panier;
import com.usherbrooke.deliverresto.entities.Plat;

/**
 * Created by Valentin on 19/11/2017.
 */

public class PlatComponent extends LinearLayout {

    private Plat currentPlat;
    private TextView titleTextView;
    private TextView ingredientsTextView;

    private Button numberButton;
    private TextView numberTv;

    public PlatComponent(Context context) {
        super(context);
        initializeViews(context);
    }

    public PlatComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public PlatComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.plat_component, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        titleTextView = (TextView) this.findViewById(R.id.titlePlatTextView);
        ingredientsTextView = (TextView) this.findViewById(R.id.ingredientsTextView);
        numberButton = (Button) this.findViewById(R.id.numberButton);
        numberButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        numberTv = (TextView) this.findViewById(R.id.numberTextView);
    }

    public void setupData(Plat plat) {
        currentPlat = plat;

        StringBuilder ingredients = new StringBuilder();
        if (plat.getListeIngredients().size() > 0) {
            for (Ingredient ingredient : plat.getListeIngredients()) {
                ingredients.append(ingredient.getNom()).append(", ");
            }
            ingredients.deleteCharAt(ingredients.length() - 2);
        }

        LinearLayout layout_text = (LinearLayout) findViewById(R.id.layout_text);
        layout_text.setBackgroundResource(plat.getDrawable());
        titleTextView.setText(plat.getNom());
        ingredientsTextView.setText(ingredients);
    }

    public void show() {

        final Dialog d = new Dialog(getContext());
        d.setTitle("Choisir votre quantité");
        d.setContentView(R.layout.numberdialog);
        Button bValider = (Button) d.findViewById(R.id.numberValiderButton);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
        np.setMaxValue(30);
        np.setMinValue(0);
        np.setValue(Integer.valueOf(numberTv.getText().toString()));
        np.setWrapSelectorWheel(false);
        bValider.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (np.getValue() != 0) {
                    Panier.getInstance().addPlat(currentPlat, Integer.valueOf(np.getValue()));
                    numberTv.setText(Integer.valueOf(np.getValue()).toString());
                    d.dismiss();
                } else {
                    Toast.makeText(getContext(), "Valeur 0 impossible", Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }

}
