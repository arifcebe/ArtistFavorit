package com.arifcebe.artistdemenan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arifcebe.artistdemenan.R;
import com.arifcebe.artistdemenan.config.BaseApplication;
import com.arifcebe.artistdemenan.config.Webservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arifcebe on 16/04/16.
 */
public class LoginActivity extends AppCompatActivity {

    private TextView resultLogin;
    private LinearLayout mainLayout;
    private EditText edUser,edPass;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        resultLogin = (TextView) findViewById(R.id.login_result);
        mainLayout = (LinearLayout) findViewById(R.id.login_layoutLogin);
        edUser = (EditText) findViewById(R.id.login_user);
        edPass = (EditText) findViewById(R.id.login_pass);
        btnLogin = (Button) findViewById(R.id.login_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });

    }

    private void processLogin(){
        BaseApplication.getInstance().startLoader(this);
        StringRequest request = new StringRequest(Request.Method.POST,
                Webservice.getLogin(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        BaseApplication.getInstance().stopLoader();

                        Log.d("Login","result login "+response);

                        try {
                            JSONObject jo = new JSONObject(response);
                            int status = jo.getInt("status");
                            String message = jo.getString("message");

                            if(status == 200){
                                String user = jo.getJSONObject("data").getString("username");
                                resultLogin.setText(message+"\n" +
                                    "Selamat datang "+user);
                                resultLogin.setTextColor(getResources().getColor(R.color.colorPrimary));
                            }else{
                                resultLogin.setText(message);
                                resultLogin.setTextColor(getResources().getColor(R.color.err_color));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        BaseApplication.getInstance().stopLoader();
                        Toast.makeText(LoginActivity.this,"error connection",Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("user",edUser.getText().toString());
                params.put("pass",edPass.getText().toString());
                params.put("submit","Login");
                return params;
            }
        };

        BaseApplication.getInstance().addToRequestQueue(request,"login");
    }
}
