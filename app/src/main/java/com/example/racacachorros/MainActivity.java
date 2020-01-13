package com.example.racacachorros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listRacas;
    private JSONArray racas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.text);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://dog.ceo/api/breeds/list/all";

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
            }
        })

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        listRacas = findViewById(R.id.listRacas);

        //Adaptador para a ListView. Serve para definir o layout de apresentação da ListView.
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                //racas
        );

        //Adiciona o adaptador para a lista
        listRacas.setAdapter(adaptador);

        //Adiciona clique na Lista
        listRacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String que armazena o item clicado. Convertido de item para String.
                String valorSelecionado = listRacas.getItemAtPosition(position).toString();

                //Toast é a caixa de texto que aparece temporariamente com o nome do item.
                Toast.makeText(getApplicationContext(), valorSelecionado, Toast.LENGTH_LONG).show();
            }
        });
    }


}
