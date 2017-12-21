package com.usherbrooke.deliverresto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.usherbrooke.deliverresto.bdd.DbHelper;

/**
 * Created by Valentin on 23/11/2017.
 */

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_Menu = navigationView.getMenu();

        if(DbHelper.getConnectedClient() == null)
            nav_Menu.findItem(R.id.nav_monCompte).setVisible(false);
        else
            nav_Menu.findItem(R.id.nav_monCompte).setVisible(true);
    }

    public void setViewLayout(int layoutRessource){

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutRessource, null, false);
        mDrawer.addView(contentView, 0);
    }

    //region NAVIGATION DRAWER (menu sur la gauche)
    @Override
    public void onBackPressed() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_monCompte) {
            //Start another activity
            Intent intent = new Intent(this, MonCompteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_carte) {
            //Start another activity
            Intent intent = new Intent(this, CarteActivity.class);
            startActivity(intent);
            //TODO: Appeler l'activité de la carte

        } else if (id == R.id.nav_panier) {
            //Start another activity
            Intent intent = new Intent(this, PanierActivity.class);
            startActivity(intent);
            //TODO: Appeler l'activité du panier

        } /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
