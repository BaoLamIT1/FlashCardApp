package com.example.flashcat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Model.Dictionary.Definition;
import com.example.flashcat.R;

import java.util.ArrayList;
import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder> {

    private Context context;
    private ArrayList<Definition> definitionArrayList;

    public DefinitionAdapter(Context context, ArrayList<Definition> definitionArrayList) {
        this.context = context;
        this.definitionArrayList = definitionArrayList;
    }

    @NonNull
    @Override
    public DefinitionAdapter.DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_definitions,parent,false);
        return new DefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionAdapter.DefinitionViewHolder holder, int position) {
        List<String> definition = new ArrayList<>();
        holder.txtDenifition.setText(definitionArrayList.get(position).getDefinition());
        holder.txtExample.setText("Ex: "+ definitionArrayList.get(position).getExample());

        StringBuilder synonyms = new StringBuilder();
        StringBuilder antonyms = new StringBuilder();
        synonyms.append(definitionArrayList.get(position).getSynonyms());
        antonyms.append(definitionArrayList.get(position).getAntonyms());

        holder.txtSynonyms.setText(synonyms);
        holder.txtAntonyms.setText(antonyms);

        holder.txtSynonyms.setSelected(true);
        holder.txtAntonyms.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return definitionArrayList.size();
    }

    public class DefinitionViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDenifition,txtExample, txtSynonyms, txtAntonyms;
        public DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDenifition = itemView.findViewById(R.id.definition_word);
            txtExample = itemView.findViewById(R.id.example_word);
            txtSynonyms = itemView.findViewById(R.id.synonyms_word);
            txtAntonyms = itemView.findViewById(R.id.antonyms_word);
        }
    }
}

