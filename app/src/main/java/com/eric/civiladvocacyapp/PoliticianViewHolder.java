package com.eric.civiladvocacyapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PoliticianViewHolder extends RecyclerView.ViewHolder{

     TextView office;
     TextView name;
     ImageView image;

    public PoliticianViewHolder(@NonNull View itemView) {
        super(itemView);
        office = itemView.findViewById(R.id.view_office);
        name = itemView.findViewById(R.id.view_name);
        image = itemView.findViewById(R.id.view_image);
    }

}
