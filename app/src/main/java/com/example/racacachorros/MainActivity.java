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

        //mTextViewResult = findViewById(R.id.text_view_result);
        mQueue = Volley.newRequestQueue(this);

        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://dog.ceo/api/breeds/list/all";

        listRacas = findViewById(R.id.listRacas);
        racas = new ArrayList<>();

        //Adaptador para a ListView. Serve para definir o layout de apresentação da ListView.
        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                racas
        );

        //Adiciona o adaptador para a lista
        listRacas.setAdapter(adaptador);

        //Adiciona clique na Lista
        listRacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = "https://dog.ceo/api/breeds/list";
                raca = listRacas.getItemAtPosition(position).toString();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    String subraca = "";
                                    JSONObject message = response.getJSONObject("message");

                                    JSONArray jsonArray = message.getJSONArray(raca);

                                    for (int x = 0; x < jsonArray.length(); x++){
                                        subraca += jsonArray.get(x).toString()+" , ";
                                    }

                                    Intent intent = new Intent();
                                    intent.putExtra("Raca", raca);
                                    intent.putExtra("subRaca", subraca);
                                    startActivity(intent);

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

    /*private void jsonParse(){
        String url = "https://dog.ceo/api/breeds/list";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("message");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject message = jsonArray.getJSONObject(i);

                                String racaName = message.getString("racaName");
                                racas.add(racaName);

                                //mTextViewResult.append(racaName);
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
    }*/


}
