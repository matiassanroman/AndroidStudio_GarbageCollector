package com.example.garbagecollectorfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText address;
    private EditText mail;

    private Boolean lleno = false;

    private RequestQueue cola;
    private Button registrar;
    private String url = "http://192.168.0.5:8080/api/usuario";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);
        mail = (EditText) findViewById(R.id.email);
        registrar = (Button)findViewById(R.id.registrar);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        cola = Volley.newRequestQueue(MainActivity.this);

        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        //COMPRUEBO SI HAY SECCION ABIERTA
        if (preferencias.getBoolean("seccion_abierta",false)) {

            Intent i = new Intent(MainActivity.this,MenuPrincipal.class);
            startActivity(i);
            finish();

        }
        else{
            // ESCUCHO AL BOTON
            registrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LlenoFormulario() == true)
                    {
                        registrar.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        registrar();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean LlenoFormulario()
    {
        //VERIFICA QUE ESTEN TODOS LOS CAMPOS COMPLETOS Y ADEMAS GUARDA LA INFORMACION.
        lleno = false;
        if(firstname.getText().toString().length() != 0)
            if(lastname.getText().toString().length() != 0)
                if(username.getText().toString().length() != 0)
                    if(address.getText().toString().length() != 0)
                        if(mail.getText().toString().length() != 0)
                            lleno = true;
        return lleno;
    }

    private void registrar()
    {
        //CREO OBJETO JSON
        JSONObject j = new JSONObject();
        try {
            j.put("firstname",firstname.getText().toString());
            j.put("lastname",lastname.getText().toString());
            j.put("username", username.getText().toString());
            j.put("address",address.getText().toString());
            j.put("mail", mail.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // CREO LA PETICION PARA ENVIAR AL SERVIDOR
        JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.POST, url, j,
                new Response.Listener<JSONObject>() {

                    //SI LA PETICION FUE EXITOSA, SE PASA A LA SIGUIENTE ACTIVIDAD.
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Intent i = new Intent(MainActivity.this,MenuPrincipal.class);
                        startActivity(i);
                        finish();
                    }
                    //EN CASO DE ERROR, SE VUELVE A HABILITAR EL BOTON
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                registrar.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        cola.add(peticion);
    }

    //EL SISTEMA LLAMA A ESTE METODO, COMO EL PRIMER INDICADOR DE QUE EL USUARIO ESTA ABANDONANDO LA ACTIVIDAD.
    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user = username.getText().toString();

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("user",user);

        editor.commit();

    }

}
