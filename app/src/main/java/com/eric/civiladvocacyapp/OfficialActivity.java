package com.eric.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OfficialActivity extends AppCompatActivity {

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

    private ImageView facebook;
    private ImageView twitter;
    private ImageView youtube;
    private ImageView official_image;
    private ImageView party_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

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

        Intent intent = getIntent();

        if(intent.hasExtra("POLITICIAN")) {
            person = (Politician) intent.getSerializableExtra("POLITICIAN");


            //setting the listeners here
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("button", "facebook!");
                }
            });
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("button", "twitter!");
                }
            });
            youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("button", "youtube!");
                }
            });

            name.setText(person.getName());
            party.setText(person.getParty());
            office.setText(person.getOffice());

            info1Title.setText("Address:");
            info1Desc.setText(person.getAddress());
            info2Title.setText("Phone:");
            info2Desc.setText(person.getPhone());
            info3Title.setText("Email:");
            info3Desc.setText(person.getEmail());
            info4Title.setText("Website:");
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

        }




    }



}