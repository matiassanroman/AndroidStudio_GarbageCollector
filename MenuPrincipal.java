package com.example.garbagecollectorfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView user;
    private TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Boolean seccion_abierta = true;

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("seccion_abierta",seccion_abierta);
        editor.commit();

        user = (TextView) findViewById(R.id.bienvenida);
        user.setText("Bienvenido: "  + "\n" + preferencias.getString("user",""));
        mensaje = (TextView) findViewById(R.id.mensaje);
        mensaje.setText("Gracias por volver y ayudar al medio ambiente.");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.reciclar) {
            Intent i = new Intent(MenuPrincipal.this,Reciclar.class);
            startActivity(i);

        } else if (id == R.id.listas) {
            Intent i = new Intent(MenuPrincipal.this,ListasReciclados.class);
            startActivity(i);

        } else if (id == R.id.cerrar_seccion) {
            SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.clear();
            editor.commit();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
