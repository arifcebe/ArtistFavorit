package com.arifcebe.artistdemenan.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arifcebe.artistdemenan.R;
import com.arifcebe.artistdemenan.config.BaseApplication;
import com.arifcebe.artistdemenan.config.Webservice;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JSonArrayActivity extends AppCompatActivity {

    private Button btnGebetan;
    private ListView lv;

    private ArrayAdapter adapter;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_array_activity);

        btnGebetan = (Button) findViewById(R.id.ja_btnGebetan);
        lv = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

        btnGebetan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGebetan();
            }
        });
    }

    /**
     * iki kanggo request gebetan2 bien
     */
    private void requestGebetan(){
        BaseApplication.getInstance().startLoader(this);
        JsonArrayRequest request = new JsonArrayRequest(Webservice.getGebetan(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        BaseApplication.getInstance().stopLoader();

                        for (int i = 0; i < response.length();i++){
                            try {
                                list.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        BaseApplication.getInstance().stopLoader();
                    }
                });

        BaseApplication.getInstance().addToRequestQueue(request,"gebetan");
    }
}
