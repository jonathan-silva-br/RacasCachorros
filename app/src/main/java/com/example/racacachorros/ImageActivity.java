package com.example.racacachorros;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.racacachorros.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class ImageActivity extends AppCompatActivity {
    private Intent i;
    private String raca;
    private  String url;
    private ImageView imageView;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        i = getIntent();
        raca = i.getStringExtra("Raca");

        queue = Volley.newRequestQueue(this);

        imageView = findViewById(R.id.imageView);
        qualquer_nome();
    }

    private void qualquer_nome(){
        Boolean sub = i.getBooleanExtra("verif", false);
        if(sub){
            String subRaca = i.getStringExtra("subRaca");
            url = "https://dog.ceo/api/breed/"+raca+"/"+subRaca+"/images/random";
        }else{
            url = "https://dog.ceo/api/breed/"+raca+"/images/random";
        }

        JsonObjectRequest request = new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    String img = response.get("message").toString();
                    SharedPreferences imagem = getSharedPreferences("ultimage", MODE_PRIVATE);
                    SharedPreferences.Editor editor = imagem.edit();
                    editor.putString("ultima",img);
                    editor.apply();

                    Picasso.get().load(img).into(imageView);

                }catch (Exception e ){
                    Log.e("AAAA",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AAAA",error.getMessage());
            }
        });

        queue.add(request);

    }
}
