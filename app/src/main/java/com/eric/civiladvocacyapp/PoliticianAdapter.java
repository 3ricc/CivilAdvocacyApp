package com.eric.civiladvocacyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.name.setText(person.getName());
        //holder.image.setImageResource(0); //FIX THIS SHIT

    }

    @Override
    public int getItemCount() {
        return pList.size();
    }
}
