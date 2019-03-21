package com.example.testproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.testproject.R;
import com.example.testproject.adapters.RecyclerViewAdapter;
import com.example.testproject.model.Cards;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String JSON_URL = "https://api.hearthstonejson.com/v1/latest/frFR/cards.collectible.json";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Cards> listCards;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCards = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_id);
        jsonrequest();
    }

    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        Cards card = new Cards();
                        card.setName(jsonObject.getString("name"));
                        card.setArtist(jsonObject.getString("artist"));
                        card.setFlavor(jsonObject.getString("flavor"));
                        card.setId(jsonObject.getString("id"));
                        listCards.add(card);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                setuprecyclerview(listCards);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);


    }

    private void setuprecyclerview(List<Cards> listCards) {

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, listCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
}
