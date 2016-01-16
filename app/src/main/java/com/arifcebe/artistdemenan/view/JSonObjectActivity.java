package com.arifcebe.artistdemenan.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arifcebe.artistdemenan.config.BaseApplication;
import com.arifcebe.artistdemenan.R;
import com.arifcebe.artistdemenan.config.Webservice;

import org.json.JSONException;
import org.json.JSONObject;

public class JSonObjectActivity extends AppCompatActivity {

    private static final String TAG = "DEMENAN";
    private LinearLayout layoutMain;
    private Button btnDemenan;
    private TextView nama,status,panggilan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_object_activity);

        layoutMain = (LinearLayout) findViewById(R.id.jo_layoutMain);
        btnDemenan = (Button) findViewById(R.id.jo_btnDemenan);
        nama = (TextView) findViewById(R.id.jo_name);
        status = (TextView) findViewById(R.id.jo_status);
        panggilan = (TextView) findViewById(R.id.jo_panggilan);


        btnDemenan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDemenan();
            }
        });
    }

    /**
     * iki kanggo request salah sijining demenan sek iseh aktif
     */
    private void requestDemenan(){
        BaseApplication.getInstance().startLoader(this);
        JsonObjectRequest request =
                new JsonObjectRequest(Webservice.getDemenan(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG,"response demenan "+response.toString());
                                BaseApplication.getInstance().stopLoader();
                                layoutMain.setVisibility(View.VISIBLE);

                                JSONObject jo = response;
                                try {
                                    nama.setText("gebetanku jenenge : "+jo.getString("name"));
                                    status.setText("status e :"+jo.getString("status"));
                                    panggilan.setText("biasa tak panggil "+jo.getString("panggilanSayang"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                BaseApplication.getInstance().stopLoader();
                            }
                        }
                );

        BaseApplication.getInstance().addToRequestQueue(request,"demenan");
    }
}
