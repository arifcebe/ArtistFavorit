package com.arifcebe.artistdemenan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arifcebe.artistdemenan.view.JSonArrayActivity;
import com.arifcebe.artistdemenan.view.JSonObjectActivity;
import com.arifcebe.artistdemenan.view.ListArtistActivity;
import com.arifcebe.artistdemenan.view.LoginActivity;
import com.arifcebe.artistdemenan.view.UploadImageActivity;

public class MainActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener{

    private ListView lv;
    private String[] listMenu;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listMenu = new String[]{"sample json array","sample json object",
            "sample json array and objecy","Upload image","Login"};
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listMenu);
        lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(this, JSonArrayActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, JSonObjectActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ListArtistActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, UploadImageActivity.class));
                break;
            default:
                startActivity(new Intent(this, LoginActivity.class));

        }
    }
}
