package com.example.garbagecollectorfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.garbagecollectorfinal.R.id.tetrabriks1;

public class Reciclar extends AppCompatActivity {

    private Integer id_r;

    private Button bottles1;
    private Button bottles2;
    private TextView valor_bottles;

    private Button tetrabriks1;
    private Button tetrabriks2;
    private TextView valor_tetrabriks;

    private Button glass1;
    private Button glass2;
    private TextView valor_glass;

    private Button paperboard1;
    private Button paperboard2;
    private TextView valor_paperboard;

    private Button cans1;
    private Button cans2;
    private TextView valor_cans;

    private Integer aux = 0;

    private RequestQueue cola;
    private Button enviar;
    private String url = "http://192.168.0.5:8080/api/reciclado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciclar);

        cola = Volley.newRequestQueue(Reciclar.this);

        bottles1 = (Button) findViewById(R.id.bottles1);
        bottles2 = (Button) findViewById(R.id.bottles2);
        valor_bottles = (TextView) findViewById(R.id.valor_bottles);

        tetrabriks1 = (Button) findViewById(R.id.tetrabriks1);
        tetrabriks2 = (Button) findViewById(R.id.tetrabriks2);
        valor_tetrabriks = (TextView) findViewById(R.id.valor_tetrabriks);

        glass1 = (Button) findViewById(R.id.glass1);
        glass2 = (Button) findViewById(R.id.glass2);
        valor_glass = (TextView) findViewById(R.id.valor_glass);

        paperboard1 = (Button) findViewById(R.id.paperboard1);
        paperboard2 = (Button) findViewById(R.id.paperboard2);
        valor_paperboard = (TextView) findViewById(R.id.valor_paperboard);

        cans1 = (Button) findViewById(R.id.cans1);
        cans2 = (Button) findViewById(R.id.cans2);
        valor_cans = (TextView) findViewById(R.id.valor_cans);

        cargar();

        ObtenerId();

        enviar = (Button) findViewById(R.id.enviar_reciclado);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarReciclado();

            }
        });

        bottles1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            aux = Integer.valueOf(valor_bottles.getText().toString());
            aux ++;
            valor_bottles.setText(aux.toString());
            guardar();
            }
        });

        bottles2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_bottles.getText().toString());
                if (aux != 0){
                    aux = Integer.valueOf(valor_bottles.getText().toString());
                    aux --;
                    valor_bottles.setText(aux.toString());
                    guardar();
                }
            }
        });

        tetrabriks1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_tetrabriks.getText().toString());
                aux ++;
                valor_tetrabriks.setText(aux.toString());
                guardar();

            }
        });

        tetrabriks2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_tetrabriks.getText().toString());
                if (aux != 0){
                    aux = Integer.valueOf(valor_tetrabriks.getText().toString());
                    aux --;
                    valor_tetrabriks.setText(aux.toString());
                    guardar();
                }
            }
        });

        glass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_glass.getText().toString());
                aux ++;
                valor_glass.setText(aux.toString());
                guardar();

            }
        });

        glass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_glass.getText().toString());
                if (aux != 0){
                    aux = Integer.valueOf(valor_glass.getText().toString());
                    aux --;
                    valor_glass.setText(aux.toString());
                    guardar();
                }
            }
        });

        paperboard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_paperboard.getText().toString());
                aux ++;
                valor_paperboard.setText(aux.toString());
                guardar();

            }
        });

        paperboard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_paperboard.getText().toString());
                if (aux != 0){
                    aux = Integer.valueOf(valor_paperboard.getText().toString());
                    aux --;
                    valor_paperboard.setText(aux.toString());
                    guardar();
                }
            }
        });

        cans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_cans.getText().toString());
                aux ++;
                valor_cans.setText(aux.toString());
                guardar();

            }
        });

        cans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aux = Integer.valueOf(valor_cans.getText().toString());
                if (aux != 0){
                    aux = Integer.valueOf(valor_cans.getText().toString());
                    aux --;
                    valor_cans.setText(aux.toString());
                    guardar();
                }
            }
        });

    }

    private void cargar(){

        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Integer bottles = preferencias.getInt("bottles",0);
        Integer tetrabriks = preferencias.getInt("tetrabriks",0);
        Integer glass = preferencias.getInt("glass",0);
        Integer paperboard = preferencias.getInt("paperboard",0);
        Integer cans = preferencias.getInt("cans",0);

        valor_bottles.setText(bottles.toString());
        valor_tetrabriks.setText(tetrabriks.toString());
        valor_glass.setText(glass.toString());
        valor_paperboard.setText(paperboard.toString());
        valor_cans.setText(cans.toString());

    }

    private void guardar(){

        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Integer bottles = Integer.valueOf(valor_bottles.getText().toString());
        Integer tetrabriks = Integer.valueOf(valor_tetrabriks.getText().toString());
        Integer glass = Integer.valueOf(valor_glass.getText().toString());
        Integer paperboard = Integer.valueOf(valor_paperboard.getText().toString());
        Integer cans = Integer.valueOf(valor_cans.getText().toString());

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt("bottles",bottles);
        editor.putInt("tetrabriks",tetrabriks);
        editor.putInt("glass",glass);
        editor.putInt("paperboard",paperboard);
        editor.putInt("cans",cans);

        editor.commit();

    }

    private void registrarReciclado(){
        // CREO UN OBJETO JSON
        JSONObject j = new JSONObject();
        try {
            j.put("bottles",Integer.valueOf(valor_bottles.getText().toString()));
            j.put("tetrabriks",Integer.valueOf(valor_tetrabriks.getText().toString()));
            j.put("glass", Integer.valueOf(valor_glass.getText().toString()));
            j.put("paperboard",Integer.valueOf(valor_paperboard.getText().toString()));
            j.put("cans", Integer.valueOf(valor_cans.getText().toString()));
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            j.put("date", date);
            j.put("id_r",id_r);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // CREO LA PETICION PARA ENVIAR AL SERVIDOR
        JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.POST, url, j,
                new Response.Listener<JSONObject>() {
                    //SI LA PETICION FUE EXITOSA, MUESTRA UN MENSAJE Y SE RESETEAN LOS BOTONES.
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(Reciclar.this,"Gracias por colaborar con el medio ambiente.", Toast.LENGTH_SHORT).show();

                        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();

                        editor.putInt("bottles",0);
                        editor.putInt("tetrabriks",0);
                        editor.putInt("glass",0);
                        editor.putInt("paperboard",0);
                        editor.putInt("cans",0);

                        editor.commit();

                        cargar();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Reciclar.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        cola.add(peticion);
    }

    private void ObtenerId()
    {
        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user = preferencias.getString("user","");

        String urlUsuario = "http://192.168.0.5:8080/api/usuario/user/"+user+"/";

        JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, urlUsuario, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {

                            id_r = response.getInt("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Reciclar.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        cola.add(peticion);
    }

}
