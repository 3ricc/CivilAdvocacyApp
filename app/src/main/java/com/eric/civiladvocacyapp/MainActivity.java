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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

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

    private final String API_KEY = "AIzaSyCDPCMtfWRLS34FXwOlpp9hWZAtsLgRoz0";

    private String url = "https://www.googleapis.com/civicinfo/v2/representatives";
    private RequestQueue queue;

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

        //makeDummyData();

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
                        downloadData(locationString);
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

    public void downloadData(String address){
        queue = Volley.newRequestQueue(this);

        Uri.Builder buildURL = Uri.parse(url).buildUpon();
        buildURL.appendQueryParameter("key", API_KEY);
        buildURL.appendQueryParameter("address", address);
        String urlToUse = buildURL.build().toString();


        Response.Listener<JSONObject> listener =
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            Log.d("response",response.toString());
                            parseJSON(response);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                };

        Response.ErrorListener error = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                try{
                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error);

        queue.add(jsonObjectRequest);



        Log.d("url", urlToUse);

    }

    private void parseJSON(JSONObject json){

        try {
            JSONObject normalizedInput = json.getJSONObject("normalizedInput");
            JSONArray offices = json.getJSONArray("offices");
            JSONArray officials = json.getJSONArray("officials");
            Log.d("normal", normalizedInput.toString());
            Log.d("officies", offices.toString());
            Log.d("offocials", officials.toString());

            for(int i = 0; i < offices.length(); i++){
                JSONObject office = offices.getJSONObject(i);
                JSONArray indexes = office.getJSONArray("officialIndices");
                Log.d("indexes", indexes.toString());

                for(int h = 0; h < indexes.length(); h++){
                    String officeTitle = office.getString("name");
                    int num = indexes.getInt(h);
                    JSONObject person = officials.getJSONObject(num);
                    Log.d("person", person.toString());

                    String name = person.getString("name");

                    String address = "";
                    try {
                        JSONArray addressArray = person.getJSONArray("address");
                        JSONObject addressObject = addressArray.getJSONObject(0);
                        String line1 = "";
                        String line2 = "";
                        String line3 = "";
                        String city = addressObject.getString("city");
                        String state = addressObject.getString("state");
                        String zip = addressObject.getString("zip");
                        try{
                            line1 = addressObject.getString("line1");
                        }
                        catch(Exception e){
                        }

                        try{
                            line2 = addressObject.getString("line2");
                        }
                        catch(Exception e){

                        }

                        try{
                            line3 = addressObject.getString("line3");
                        }
                        catch(Exception e){

                        }

                        if(line3.isEmpty()){
                            if(line2.isEmpty()){
                                address = line1 + " " + city + ", " + state + ", " + zip;
                            }
                            else{
                                address = line1 + " " +line2 + " "+ city + ", " + state + ", " + zip;
                            }
                        }
                        else{
                            address = line1 + " " + line2 + "" + line3 + " " + city + ", " + state + ", " + zip;
                        }

                    }
                    catch (Exception e){

                    }

                    String party = "";
                    try{
                        party = person.getString("party");
                    }
                    catch (Exception e){
                        party = "Unknown";
                    }

                    //Log.d("party", party);

                    String photoUrl = "";
                    try {
                        photoUrl = person.getString("photoUrl");
                    }
                    catch (Exception e){
                        photoUrl = "placeholder";
                    }

                    String number = "";
                    try{
                        JSONArray numbers = person.getJSONArray("phones");
                        number = numbers.getString(0);
                    }
                    catch (Exception e){

                    }

                    //Log.d("number", number);

                    String urls = "";
                    try{
                        JSONArray urlsList = person.getJSONArray("urls");
                        urls = urlsList.getString(0);
                    }
                    catch (Exception e){

                    }

                    //Log.d("url", urls);

                    String email = "";
                    try{
                        JSONArray emails = person.getJSONArray("emails");
                        email = emails.getString(0);
                    }
                    catch (Exception e){

                    }
                    //Log.d("email", email);


                    String facebookLink = "";
                    String twitterLink = "";
                    String youtubeLink = "";
                    try {
                       JSONArray channels = person.getJSONArray("channels");
                       for(int x = 0; x < channels.length(); x++){
                           JSONObject linkObject = channels.getJSONObject(x);
                           String type = linkObject.getString("type");
                           if (type.equals("Facebook")){
                               facebookLink = linkObject.getString("id");
                           }
                           if (type.equals("Twitter")){
                               twitterLink = linkObject.getString("id");
                           }
                           if (type.equals("YouTube")){
                               youtubeLink = linkObject.getString("id");
                           }
                       }
                    }
                    catch (Exception e){
                    }

                    //Log.d("facebook", facebookLink);
                    //Log.d("twitter", twitterLink);
                    //Log.d("youtube", youtubeLink);

                    Politician p = new Politician(name, officeTitle, party, address, number, email, urls, facebookLink, twitterLink, youtubeLink);
                    updateList(p);

                    Log.d("politician", p.toString());


                }// end of for loop for each person



            }// end of for loop for each office

        }
        catch (Exception e){
            Log.d("exception", "exception occured");
        }


    }

    public void updateList(Politician p){
        politicianList.add(p);
        politicianAdapter.notifyItemInserted(politicianList.size());
    }



}