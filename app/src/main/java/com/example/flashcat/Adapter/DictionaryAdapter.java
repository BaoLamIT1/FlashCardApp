package com.example.flashcat.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Activity.WordActivity;
import com.example.flashcat.Database.DatabaseApp;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Word;
import com.example.flashcat.R;

import java.util.ArrayList;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Word> wordArrayList;
    private ArrayList<Word> wordArrayListOld;
    private DatabaseApp db;

    public DictionaryAdapter(Context context, ArrayList<Word> wordArrayList) {
        this.context = context;
        this.wordArrayList = wordArrayList;
        wordArrayListOld= wordArrayList;
        db = new DatabaseApp(context);
    }

    @NonNull
    @Override
    public DictionaryAdapter.DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dictionary,parent,false);
        return new DictionaryViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull DictionaryAdapter.DictionaryViewHolder holder, int position) {
        holder.txtWord.setText(wordArrayList.get(position).getWord());
        holder.txtMinus.setText(wordArrayList.get(position).getMinusWord());
        holder.txtDefinition.setText(wordArrayList.get(position).getDefinitionWord());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteWord(wordArrayList.get(position).getId());
                wordArrayList.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        if(wordArrayList!=null)
            return wordArrayList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    wordArrayList = wordArrayListOld;
                } else{
                    ArrayList<Word> list = new ArrayList<>();
                    for(Word w : wordArrayListOld){
                        if(w.getWord().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(w);
                        }
                    }
                    wordArrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = wordArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                wordArrayList = (ArrayList<Word>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class DictionaryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWord;
        private TextView txtMinus;
        private TextView txtDefinition;
        private ImageButton btnDelete;
        private ImageButton btnArrowcard;
        public DictionaryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWord = itemView.findViewById(R.id.txt_card);
            txtMinus = itemView.findViewById(R.id.txt_minus_card);
            txtDefinition = itemView.findViewById(R.id.txt_definition_card);
            btnDelete = itemView.findViewById(R.id.btn_delete_card);

            // su kien chon item trong list
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, WordActivity.class);
                    i.putExtra("search_query",txtWord.getText());
                    ((Activity) context).startActivity(i);
                }
            });
        }
    }
}
