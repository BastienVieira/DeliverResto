package com.usherbrooke.deliverresto.components;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.Plat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin on 19/11/2017.
 */

public class CategoriePlatComponent extends LinearLayout {

    private TextView titleTextView;
    private ListView listView;

    public CategoriePlatComponent(Context context) {
        super(context);
        initializeViews(context);
    }

    public CategoriePlatComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CategoriePlatComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cat_plat_component, this);
    }

    @Override//adapte au mieux la taille du listView en fonction de son contenu
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getResources().getDisplayMetrics().heightPixels;
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        titleTextView = (TextView) this.findViewById(R.id.categoriePlatText);
        listView = (ListView) this.findViewById(R.id.listViewPlats);
        titleTextView.setBackgroundColor(Color.LTGRAY);
    }

    public void setupData(String title, List<Plat> listPlat) {
        titleTextView.setText(title);
        listView.setAdapter(new PlatAdapter(getContext(), listPlat));
    }


}
