package com.eric.civiladvocacyapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PoliticianAdapter extends RecyclerView.Adapter<PoliticianViewHolder> {

    private MainActivity mainAct;
    private ArrayList<Politician> pList;

    PoliticianAdapter(ArrayList<Politician> list, MainActivity ma){
        this.pList = list;
        this.mainAct = ma;
    }


    @NonNull
    @Override
    public PoliticianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.politician_view, parent, false);

        itemView.setOnClickListener(mainAct);
        return new PoliticianViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliticianViewHolder holder, int position) {
        Politician person = pList.get(position);
        holder.office.setText(person.getOffice());

        String party = "";
        if(person.getParty().equals("Democratic Party")){
            party = "(Democratic Party)";
        }
        else if (person.getParty().equals("Republican Party")){
            party = "(Republican Party)";
        }
        else{
            party = "(Unknown Party)";
        }

        holder.name.setText(person.getName() + " " + party);
        if(!person.getPhotoUrl().isEmpty()) {
            Log.d("downloading", "image");
            Picasso.get().load(person.getPhotoUrl()).placeholder(R.drawable.missing).error(R.drawable.brokenimage).into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        return pList.size();
    }


    //So i had a really weird issue that my images were duplicating, and after doing some googling online apparently you need to override these methods..
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
