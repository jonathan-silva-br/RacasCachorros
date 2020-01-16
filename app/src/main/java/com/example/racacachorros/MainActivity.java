package com.example.racacachorros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listRacas;
    private ArrayList<String> racas;
    private RequestQueue mQueue;
    private String raca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        listRacas = findViewById(R.id.listRacas);
        racas = new ArrayList<>();

        //Adaptador para a ListView. Serve para definir o layout de apresentação da ListView.
        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1, racas);

        //Adiciona o adaptador para a lista
        listRacas.setAdapter(adaptador);

        //Adiciona clique na Lista
        listRacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                raca = listRacas.getItemAtPosition(position).toString();
                String url = "https://dog.ceo/api/breed/"+raca+"/list";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    String subraca = "";
                                    JSONArray message = response.getJSONArray("message");

                                    for (int x = 0; x < message.length(); x++){
                                        subraca += message.get(x).toString()+",";
                                    }

                                    if(!subraca.equals("")){
                                        Intent i = new Intent(MainActivity.this, RacaActivity.class);
                                        i.putExtra("Raca", raca);
                                        i.putExtra("subRaca", subraca);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Intent i = new Intent(MainActivity.this, ImageActivity.class);
                                        i.putExtra("Raca", raca);
                                        i.putExtra("subRaca", subraca);
                                        startActivity(i);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                mQueue.add(request);


            }
        });

        String url = "https://dog.ceo/api/breeds/list";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("message");

                            for (int i = 0; i < jsonArray.length(); i++){
                                racas.add(jsonArray.get(i).toString());
                            }
                            adaptador.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}
