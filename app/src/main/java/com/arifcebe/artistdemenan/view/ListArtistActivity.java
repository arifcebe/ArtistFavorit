package com.arifcebe.artistdemenan.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arifcebe.artistdemenan.R;
import com.arifcebe.artistdemenan.adapter.ListArtistAdapter;
import com.arifcebe.artistdemenan.config.BaseApplication;
import com.arifcebe.artistdemenan.config.Webservice;
import com.arifcebe.artistdemenan.model.Artist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListArtistActivity extends AppCompatActivity {

    private Button btnArtist;
    private ListView lv;

    private List<Artist> listArtist = new ArrayList<Artist>();
    private ListArtistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_artist_activity);

        btnArtist = (Button) findViewById(R.id.artist_btnArtist);
        lv= (ListView) findViewById(R.id.listview);
        adapter = new ListArtistAdapter(this,listArtist);
        lv.setAdapter(adapter);

        btnArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestArtist();
            }
        });

    }

    private void requestArtist(){
        BaseApplication.getInstance().startLoader(this);
        JsonArrayRequest request = new JsonArrayRequest(Webservice.getArtist(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        BaseApplication.getInstance().stopLoader();
                        Log.d("artist","response artist "+response.toString());
                        generateListArtist(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        BaseApplication.getInstance().stopLoader();
                    }
                });

        BaseApplication.getInstance().addToRequestQueue(request, "artist");
    }

    /**
     * generate list artist masukkan ke dalam model dan taruh ke adapter
     * @param response
     */
    private void generateListArtist(JSONArray response){
        for (int i = 0; i < response.length();i++){
            try {
                JSONObject jo = response.getJSONObject(i);
                Artist artist = new Artist();
                artist.setName(jo.getString("name"));
                artist.setHoby(jo.getString("hoby"));
                artist.setPanggilan(jo.getString("panggilan"));
                artist.setPhoto(jo.getString("photo"));

                listArtist.add(artist);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }
}
