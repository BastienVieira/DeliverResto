package com.usherbrooke.deliverresto.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.usherbrooke.deliverresto.MainActivity;
import com.usherbrooke.deliverresto.PanierActivity;
import com.usherbrooke.deliverresto.R;
import com.usherbrooke.deliverresto.entities.Commande;
import com.usherbrooke.deliverresto.entities.Plat;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin on 12/11/2017.
 */
public class CommandeComponent extends LinearLayout {
    private TextView title;

    private ListView listView;
    private SwipeActionAdapter mAdapter;

    private List<Commande> listCommandes = new ArrayList<>();
    private List<Plat> listePlats = new ArrayList<>();

    //region Initialisation des sous composants

    public CommandeComponent(Context context) {
        super(context);
        initializeViews(context);
    }

    public CommandeComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CommandeComponent(Context context,
                             AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.commande_component, this);
    }

    @Override//adapte au mieux la taille du listView en fonction de son contenu
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getResources().getDisplayMetrics().heightPixels;
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        title = (TextView) this.findViewById(R.id.textCommandeComponent);
        title.setBackgroundColor(Color.LTGRAY);

        listView = (ListView) this.findViewById(R.id.listView);
    }

    public TextView getTitle() {
        return title;
    }

    public void setupSwipeListView(List<Plat> listePlts, final boolean isSwipeRightAllowed, final boolean isSwipeLeftAllowed) {
        listView = (ListView) this.findViewById(R.id.listView);
        this.listePlats = listePlts;
        // Create an Adapter for your content
        ArrayAdapter<Plat> tAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.row_bg,
                R.id.textListView,
                listePlats
        );

        // Wrap your content in a SwipeActionAdapter
        mAdapter = new SwipeActionAdapter(tAdapter);
        // Pass a reference of your ListView to the SwipeActionAdapter
        mAdapter.setListView(listView);

        // Set the SwipeActionAdapter as the Adapter for your ListView
        listView.setAdapter(mAdapter);
        mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.row_bg_left_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);

        // Listen to swipes
        mAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                if (direction.isLeft())
                    return isSwipeLeftAllowed; // Change this to false to disable left swipes
                if (direction.isRight()) return isSwipeRightAllowed;
                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                // Only dismiss an item when swiping normal left
                return true;
            }

            @Override
            public void onSwipe(int[] positionList, SwipeDirection[] directionList) {
                for (int i = 0; i < positionList.length; i++) {
                    int position = positionList[i];

                    switch (directionList[i]) {
                        case DIRECTION_FAR_LEFT:
                        case DIRECTION_NORMAL_LEFT:
//                            swipeLeft(position);
                            break;

                        case DIRECTION_FAR_RIGHT:
                        case DIRECTION_NORMAL_RIGHT:
//                            swipeRight(position);
                            break;
                    }
                }
            }
        });
    }

    public ListView getListView() {
        return listView;
    }

    public void removeRow(int rowPosition) {
        listCommandes.remove(rowPosition);
        mAdapter.notifyDataSetChanged();
    }

    //endregion

    public void setTitle(String t) {
        this.title = (TextView) this.findViewById(R.id.textCommandeComponent);
        this.title.setText(t);

    }

//    public void swipeRight(int rowPosition) {
//        if (getContext() instanceof PanierActivity){
//            if(listCommandes.size() > 0){
//                ((PanierActivity) getContext()).swipeRight(rowPosition, listePlats.get(rowPosition));
//            }else {
//                ((PanierActivity) getContext()).swipeRight(rowPosition, null);
//            }
//        }
//
//    }
//
//    public void swipeLeft(int rowPosition) {
//        if (getContext() instanceof PanierActivity){
//            if(listCommandes.size() > 0){
//                ((PanierActivity) getContext()).swipeLeft(rowPosition, listePlats.get(rowPosition));
//            }else {
//                ((PanierActivity) getContext()).swipeLeft(rowPosition, null);
//            }
//        }
//    }

}
