package com.example.flashcat.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import java.util.ArrayList;

public class EditDeskAdapter extends RecyclerView.Adapter<EditDeskAdapter.EditdeskViewHolder>{
    private ArrayList<Flashcard> fleditArrayList;
    private Context context;
    public void setData(ArrayList<Flashcard> fleditArrayList){
        this.fleditArrayList = fleditArrayList;
        notifyDataSetChanged();
    }

    public EditDeskAdapter(ArrayList<Flashcard> fleditArrayList, Context context) {
        this.fleditArrayList = fleditArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public EditdeskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_flashcard_editdesk,parent,false);
        return new EditdeskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditdeskViewHolder holder, int position) {
        Flashcard flashcard = fleditArrayList.get(position);
        if(flashcard == null)
            return;
        holder.edTermEdit.setText(flashcard.getTerm());
        holder.edDefinitionEdit.setText(flashcard.getDefinition());
        holder.edTermEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                flashcard.setTerm(s.toString());
            }
        });
        holder.edDefinitionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                flashcard.setDefinition(s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(fleditArrayList != null)
        {
            return fleditArrayList.size();
        }
        return 0;
    }

    public class EditdeskViewHolder extends RecyclerView.ViewHolder{
        private EditText edTermEdit;
        private EditText edDefinitionEdit;

        public EditdeskViewHolder(@NonNull View itemView) {
            super(itemView);
            edTermEdit = itemView.findViewById(R.id.ed_term_edit_flashcard);
            edDefinitionEdit = itemView.findViewById(R.id.ed_definition_edit_flashcard);
        }
    }
}
