package com.eric.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    private TextView location;
    private TextView name;
    private TextView office;
    private ImageView image;
    private ImageView icon;

    private ConstraintLayout mConstraintLayout;

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
        mConstraintLayout = findViewById(R.id.photo_layout);

        Intent intent = getIntent();
        if(intent.hasExtra("POLITICIAN")){
            person = (Politician) intent.getSerializableExtra("POLITICIAN");
            name.setText(person.getName());
            office.setText(person.getOffice());

            if(person.getParty().equals("Democratic Party")){
                int iconResId = getResources().getIdentifier("dem_logo", "drawable", getPackageName());
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                icon.setImageResource(iconResId);
            }
            else if(person.getParty().equals("Republican Party")){
                int iconResId = getResources().getIdentifier("rep_logo", "drawable", getPackageName());
                mConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
                icon.setImageResource(iconResId);
            }
            else{

            }


            Bundle extras = intent.getExtras();
            if(extras != null){
                location.setText(extras.getString("LOCATION"));
            }

            Picasso.get().load(person.getPhotoUrl()).placeholder(R.drawable.missing).error(R.drawable.brokenimage).into(image, new Callback() {
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



    }
}