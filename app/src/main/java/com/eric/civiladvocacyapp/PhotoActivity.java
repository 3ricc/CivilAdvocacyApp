package com.eric.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoActivity extends AppCompatActivity {

    private TextView location;
    private TextView name;
    private TextView office;
    private ImageView image;
    private ImageView icon;

    private Politician person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        location = findViewById(R.id.photo_location);
        name = findViewById(R.id.photo_name);
        office = findViewById(R.id.photo_office);
        image = findViewById(R.id.photo_image);
        icon = findViewById(R.id.photo_party_icon);

        Intent intent = getIntent();
        if(intent.hasExtra("POLITICIAN")){
            person = (Politician) intent.getSerializableExtra("POLITICIAN");
            name.setText(person.getName());
            office.setText(person.getOffice());

            Bundle extras = intent.getExtras();
            if(extras != null){
                location.setText(extras.getString("LOCATION"));
            }

        }



    }
}