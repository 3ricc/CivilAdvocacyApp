package com.eric.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class OfficialActivity extends AppCompatActivity {

    private static RequestQueue queue;
    private Politician person;

    private TextView location;
    private TextView name;
    private TextView office;
    private TextView party;
    private TextView info1Title;
    private TextView info2Title;
    private TextView info3Title;
    private TextView info4Title;
    private TextView info1Desc;
    private TextView info2Desc;
    private TextView info3Desc;
    private TextView info4Desc;

    private ConstraintLayout mConstraintLayout;

    private ImageView facebook;
    private ImageView twitter;
    private ImageView youtube;
    private ImageView official_image;
    private ImageView party_icon;

    private String twitterBase = "https://www.twitter.com/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        queue = Volley.newRequestQueue(this);

        location = findViewById(R.id.official_location);
        name = findViewById(R.id.official_name);
        office = findViewById(R.id.official_title);
        party = findViewById(R.id.official_party);

        info1Title = findViewById(R.id.official_info1_title);
        info2Title = findViewById(R.id.official_info2_title);
        info3Title = findViewById(R.id.official_info3_title);
        info4Title = findViewById(R.id.official_info4_title);

        info1Desc = findViewById(R.id.official_info1_desc);
        info2Desc = findViewById(R.id.official_info2_desc);
        info3Desc = findViewById(R.id.official_info3_desc);
        info4Desc = findViewById(R.id.official_info4_desc);

        facebook = findViewById(R.id.facebookButton);
        twitter = findViewById(R.id.twitterButton);
        youtube = findViewById(R.id.youtubeButton);
        official_image = findViewById(R.id.official_picture);
        party_icon = findViewById(R.id.party_logo);

        mConstraintLayout = findViewById(R.id.official_layout);


        Intent intent = getIntent();

        if(intent.hasExtra("POLITICIAN")) {
            person = (Politician) intent.getSerializableExtra("POLITICIAN");
            Bundle extras = intent.getExtras();
            if (extras != null){
                location.setText(extras.getString("LOCATION"));
            }

            //setting the listeners here
            if(!person.getFacebookLink().isEmpty()){
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String facebook_url = "https://www.facebook.com/" + person.getFacebookLink();
                        Intent intent;

                        if (isPackageInstalled("com.facebook.katana")) {
                            String urlToUse = "fb://facewebmodal/f?href=" + facebook_url;
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
                        } else {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook_url));
                        }

                        // Check if there is an app that can handle fb or https intents
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            makeErrorAlert("No Application found that handles ACTION_VIEW (fb/https) intents");
                        }

                    }
                });

            }
            else{
                facebook.setVisibility(View.INVISIBLE);
            }

            if(!person.getTwitterLink().isEmpty()){
                twitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user = person.getTwitterLink();
                        String twitterAppUrl = "twitter://user?screen_name=" + user;
                        String twitterWebUrl = "https://twitter.com/" + user;

                        Intent intent;
                        // Check if Twitter is installed, if not we'll use the browser
                        if (isPackageInstalled("com.twitter.android")) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
                        } else {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebUrl));
                        }

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            makeErrorAlert("No Application found that handles ACTION_VIEW (twitter/https) intents");
                        }

                    }
                });

            }
            else{
                twitter.setVisibility(View.INVISIBLE);
            }

            if(!person.getYoutubeLink().isEmpty()){
                youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = person.getYoutubeLink();
                        Intent intent = null;
                        try {
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.setPackage("com.google.android.youtube");
                            intent.setData(Uri.parse("https://www.youtube.com/" + name));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.youtube.com/" + name)));
                        }
                    }
                });
            }
            else{
                youtube.setVisibility(View.INVISIBLE);
            }

            name.setText(person.getName());

            if(person.getParty().equals("Democratic Party")){
                party.setText("(Democratic Party)");
            }
            else if (person.getParty().equals("Republican Party")){
                party.setText("(Republican Party)");
            }
            else{
                party.setText("(Unknown Party)");
            }
            office.setText(person.getOffice());

            info1Desc.setText(person.getAddress());
            info2Desc.setText(person.getPhone());
            info3Desc.setText(person.getEmail());
            info4Desc.setText(person.getWebsite());



            if (person.getAddress().isEmpty()) {
                info1Title.setVisibility(View.GONE);
                info1Desc.setVisibility(View.GONE);
            }
            if (person.getPhone().isEmpty()) {
                info2Title.setVisibility(View.GONE);
                info2Desc.setVisibility(View.GONE);
            }
            if (person.getEmail().isEmpty()) {
                info3Title.setVisibility(View.GONE);
                info3Desc.setVisibility(View.GONE);
            }
            if (person.getWebsite().isEmpty()) {
                info4Title.setVisibility(View.GONE);
                info4Desc.setVisibility(View.GONE);
            }

            if(person.getParty().equals("Democratic Party")){
                int iconResId = getResources().getIdentifier("dem_logo", "drawable", getPackageName());
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                party_icon.setImageResource(iconResId);
            }
            else if(person.getParty().equals("Republican Party")){
                int iconResId = getResources().getIdentifier("rep_logo", "drawable", getPackageName());
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
                party_icon.setImageResource(iconResId);
            }
            else{
                party_icon.setVisibility(View.INVISIBLE);
            }

            if(!person.getPhotoUrl().isEmpty()){
                downloadImage(person.getPhotoUrl());
            }


        }// end of politician


    }

    private void downloadImage(String urlString){
        Picasso.get().load(urlString).placeholder(R.drawable.missing).error(R.drawable.brokenimage).into(official_image, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("success", "downloaded photo!");
            }
            @Override
            public void onError(Exception e) {
                Log.d("fail", "onError: " + e);
            }
        });
    }



    public void onImageClick(View v){
        if(!person.getPhotoUrl().isEmpty()) {
            Intent intent = new Intent(this, PhotoActivity.class);
            intent.putExtra("POLITICIAN", person);
            intent.putExtra("LOCATION", location.getText().toString());
            startActivity(intent);
        }
    }

    public boolean isPackageInstalled(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clickCall(View v) {
        String number = info2Desc.getText().toString();

        Log.d("number", number);

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_DIAL (tel) intents");
        }
    }

    public void clickMap(View v) {
        String address = info1Desc.getText().toString();

        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);

        // Check if there is an app that can handle geo intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (geo) intents");
        }
    }

    public void clickEmail(View v) {
        String[] addresses = new String[]{ info3Desc.getText().toString()};

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "This comes from EXTRA_SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "Email text body from EXTRA_TEXT...");

        // Check if there is an app that can handle mailto intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles SENDTO (mailto) intents");
        }
    }

    public void clickWebsite(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(info4Desc.getText().toString()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void clickParty(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String d = "https://democrats.org";
        String r = "https://gop.com";

        if(person.getParty().equals("Democratic Party")) {
            intent.setData(Uri.parse(d));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }
        else if(person.getParty().equals("Republican Party")){
            intent.setData(Uri.parse(r));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    }



}