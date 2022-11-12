package com.eric.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private PoliticianAdapter politicianAdapter;
    private final ArrayList<Politician> politicianList = new ArrayList<>();

    private TextView address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.politicianView);
        politicianAdapter = new PoliticianAdapter(politicianList , this);
        recyclerView.setAdapter(politicianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        makeDummyData();

        address = findViewById(R.id.address_view);
        Log.d("wtf", politicianList.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutOption:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.searchOption:
                //DIALOG SHIT HERE
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        Politician p = politicianList.get(position);

        Intent intent = new Intent(this, OfficialActivity.class);
        intent.putExtra("POLITICIAN", p);
        startActivity(intent);

    }

    private void makeDummyData(){

        Politician placeholder = new Politician("stew", "valorant player", "i keep changing my dpi accidently", "lol", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder2 = new Politician("troll", "valorant player", "really short player", "lol", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder2);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder3 = new Politician("oli", "valorant player", "french canadian", "lol", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder3);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder4 = new Politician("lou", "valorant player", "lineup nerd", "lol", "lol", "lol", "", "lol", "lol", "lol");
        politicianList.add(placeholder4);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder5 = new Politician("ricky", "valorant player", "part time screaM", "", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder5);
        politicianAdapter.notifyItemInserted(politicianList.size());

    }

}