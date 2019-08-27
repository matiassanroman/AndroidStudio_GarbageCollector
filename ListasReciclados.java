package com.example.garbagecollectorfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListasReciclados extends AppCompatActivity {

    private Integer id;


    private  TextView titulo;
    private TextView lista;

    private RequestQueue cola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas_reciclados);

        cola = Volley.newRequestQueue(ListasReciclados.this);

        ObtenerId();

        titulo = (TextView)findViewById(R.id.titulo);
        lista = (TextView)findViewById(R.id.listas);

        SharedPreferences preferencias = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        titulo.setText(preferencias.getString("titulo",""));
        lista.setText(preferencias.getString("lista",""));

    }

    //metodo para el boton1
    public void Star1(View view){
        titulo.setText("Lista de todos tus reciclados");
        lista.setText("");
        ObtenerReciclados();
    }

    //metodo para el boton2
    public void Star2(View view){
        titulo.setText("Total de todos tus reciclados");
        lista.setText("");
        ObtenerTotalReciclados();
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

                            id = response.getInt("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListasReciclados.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        cola.add(peticion);
    }

    private void ObtenerReciclados()
    {
        //HAGO UNA PETICION DE TIPO GET, PARA OBTENER LA LISTA DE TODOS LOS RECICLADOS DE ESE USUARIO.
        // EJECUTO EL METODO ONRESPONSE EN UN HILO EN SEGUNDO PLANO, MEDIANTE LA CLASE ASYNCTASK.

        String urlLista = "http://192.168.0.5:8080/api/reciclados/"+id+"/";

        JsonArrayRequest peticion = new JsonArrayRequest(Request.Method.GET, urlLista, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response)
                    {

                        AsyncTask<JSONArray,?,?> task1 = new AsyncTask<JSONArray, String, Object>()
                        {
                            @Override
                            protected Object doInBackground(JSONArray... objects) {

                                JSONArray response = objects[0];
                                try {

                                    for (int i = 0; i< response.length(); i++) {

                                        JSONObject reciclado = response.getJSONObject(i);

                                        String Reciclado = "RECICLADO "+String.valueOf(i+1)+": ";
                                        String Bottles = "Bottles: " + String.valueOf(reciclado.getInt("bottles")) + ", ";
                                        String Tetrabriks = "Tetrabriks: " + String.valueOf(reciclado.getInt("tetrabriks")) + ", ";
                                        String Cans = "Cans: " + String.valueOf(reciclado.getInt("cans")) + ", ";
                                        String Paperboard = "Paperboard: " + String.valueOf(reciclado.getInt("paperboard")) + ", ";
                                        String Glass = "Glass: " + String.valueOf(reciclado.getInt("glass")) + ", ";
                                        String Date = "Date: " + String.valueOf(reciclado.getString("date"));

                                        publishProgress( Reciclado,Bottles,Tetrabriks,Cans,Paperboard,Glass,Date);

                                    }

                                } catch (
                                        JSONException e)

                                {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                            @Override
                            protected void onProgressUpdate(String... valores)
                            {
                                lista.append(String.valueOf(valores[0])+
                                        String.valueOf(valores[1])+
                                        String.valueOf(valores[2])+
                                        String.valueOf(valores[3])+
                                        String.valueOf(valores[4])+
                                        String.valueOf(valores[5])+
                                        String.valueOf(valores[6])+"\n\n");
                            }
                        };

                        task1.execute(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListasReciclados.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        cola.add(peticion);
    }

    private void ObtenerTotalReciclados()
    {
        //HAGO UNA PETICION DE TIPO GET, PARA OBTENER EL TOTAL DE TODOS LOS RECICLADOS DE ESE USUARIO.
        // EJECUTO EL METODO ONRESPONSE EN UN HILO EN SEGUNDO PLANO, MEDIANTE LA CLASE ASYNCTASK.

        String urlLista = "http://192.168.0.5:8080/api/reciclado/"+id+"/";

        JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, urlLista, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        AsyncTask<JSONObject,?,?> task2 = new AsyncTask<JSONObject, String, Object>()
                        {
                            @Override
                            protected Object doInBackground(JSONObject... objects) {

                                JSONObject response = objects[0];
                                try {

                                    String Bottles = "Bottles: " + String.valueOf(response.getInt("bottles")) + ", ";
                                    String Tetrabriks = "Tetrabriks: " + String.valueOf(response.getInt("tetrabriks")) + ", ";
                                    String Cans = "Cans: " + String.valueOf(response.getInt("cans")) + ", ";
                                    String Paperboard = "Paperboard: " + String.valueOf(response.getInt("paperboard")) + ", ";
                                    String Glass = "Glass: " + String.valueOf(response.getInt("glass")) + ", ";

                                    publishProgress(Bottles,Tetrabriks,Cans,Paperboard,Glass);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            return null;
                            }
                            @Override
                            protected void onProgressUpdate(String... valores)
                            {
                                lista.append(String.valueOf(valores[0])+
                                        String.valueOf(valores[1])+
                                        String.valueOf(valores[2])+
                                        String.valueOf(valores[3])+
                                        String.valueOf(valores[4])+"\n\n");
                            }
                        };

                        task2.execute(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListasReciclados.this,error.toString(), Toast.LENGTH_SHORT).show();
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

        String tituloaux = titulo.getText().toString();

        String listaaux = lista.getText().toString();

        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("titulo",tituloaux);
        editor.putString("lista",listaaux);

        editor.commit();

    }

}


