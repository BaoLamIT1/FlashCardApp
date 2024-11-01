package com.example.flashcat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Model.Dictionary.Meaning;
import com.example.flashcat.R;

import java.util.ArrayList;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>  {
    private Context context;
    protected ArrayList<Meaning> meaningArrayList;

    public MeaningAdapter(Context context, ArrayList<Meaning> meaningArrayList) {
        this.context = context;
        this.meaningArrayList = meaningArrayList;
    }



    @NonNull
    @Override
    public MeaningAdapter.MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meaning_dictionary,parent,false);
        return new MeaningViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MeaningAdapter.MeaningViewHolder holder, int position) {
        holder.txtpartOfSpeech.setText(meaningArrayList.get(position).getPartOfSpeech());
        holder.recyclerViewDefinition.setHasFixedSize(true);
        holder.recyclerViewDefinition.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        DefinitionAdapter definitionAdapter = new DefinitionAdapter(context,meaningArrayList.get(position).getDefinitions());
        holder.recyclerViewDefinition.setAdapter(definitionAdapter);
    }

    @Override
    public int getItemCount() {
        return meaningArrayList.size();
    }

    public class MeaningViewHolder extends RecyclerView.ViewHolder {
        TextView txtpartOfSpeech;
        RecyclerView recyclerViewDefinition;
        public MeaningViewHolder(@NonNull View itemView) {
            super(itemView);
            txtpartOfSpeech = itemView.findViewById(R.id.partOfSpeech);
            recyclerViewDefinition = itemView.findViewById(R.id.list_definitions);
        }
    }
}
