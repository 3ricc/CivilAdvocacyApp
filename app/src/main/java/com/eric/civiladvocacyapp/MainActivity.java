package com.eric.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private PoliticianAdapter politicianAdapter;
    private final ArrayList<Politician> politicianList = new ArrayList<>();

    private FusedLocationProviderClient mFusedLocationClient;

    private static final int LOCATION_REQUEST = 111;

    private static String locationString = "Unspecified Location";

    private TextView address;

    //i typed this line on a laptop

    //i typed this line on the desktop

    //right back at you buddy



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        determineLocation();

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
        intent.putExtra("LOCATION", address.getText().toString());
        startActivity(intent);

    }

    private void makeDummyData(){

        Politician placeholder = new Politician("stew", "should not nap", "CRWN", "lol", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder2 = new Politician("troll", "oh ur my igl?", "CRWN", "lol", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder2);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder3 = new Politician("oli", "french canadian", "CRWN", "lol", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder3);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder4 = new Politician("lou", "lineup nerd", "CRWN", "lol", "lol", "lol", "", "lol", "lol", "lol");
        politicianList.add(placeholder4);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder5 = new Politician("ricky", "part time screaM", "CRWN", "", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder5);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder6 = new Politician("prainex", "schrodingers coach", "CRWN", "", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder6);
        politicianAdapter.notifyItemInserted(politicianList.size());

        Politician placeholder7 = new Politician("lero", "pokemon fanatic", "CRWN", "", "lol", "lol", "lol", "lol", "lol", "lol");
        politicianList.add(placeholder7);
        politicianAdapter.notifyItemInserted(politicianList.size());

    }

    private void determineLocation() {
        // Check perm - if not then start the  request and return
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some situations this can be null.
                    if (location != null) {
                        locationString = getPlace(location);
                        address.setText(locationString);
                    }
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    determineLocation();
                } else {
                    address.setText(R.string.deniedText);
                }
            }
        }
    }

    private String getPlace(Location loc) {

        StringBuilder sb = new StringBuilder();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            sb.append(String.format(
                    Locale.getDefault(),
                    "%s, %s",
                    city, state, loc.getProvider(), loc.getLatitude(), loc.getLongitude()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



}