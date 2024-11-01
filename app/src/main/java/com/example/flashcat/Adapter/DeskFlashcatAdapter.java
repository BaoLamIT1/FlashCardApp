package com.example.flashcat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import java.io.IOException;
import java.util.ArrayList;

public class DeskFlashcatAdapter extends RecyclerView.Adapter<DeskFlashcatAdapter.DeskFlashcatViewHolder>{
    private ArrayList<Flashcard> flashcardArrayList;
    private Context context;
    public void setData(ArrayList<Flashcard> flashcardArrayList){
        this.flashcardArrayList = flashcardArrayList;
        notifyDataSetChanged();
    }
    public DeskFlashcatAdapter(ArrayList<Flashcard> flashcardArrayList, Context context) {
        this.flashcardArrayList = flashcardArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeskFlashcatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_flashcard_desk,parent,false);
        return new DeskFlashcatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeskFlashcatViewHolder holder, int position) {
        Flashcard flashcard = flashcardArrayList.get(position);
        if(flashcard == null)
            return;
        holder.txtDefinitionDesk.setText(flashcard.getDefinition());
        holder.txtTearmDesk.setText(flashcard.getTerm());
        holder.btnVolumeDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(flashcard.getSound());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(flashcardArrayList != null){
            return flashcardArrayList.size();
        }
        return 0;
    }

    public class DeskFlashcatViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTearmDesk;
        private TextView txtDefinitionDesk;
        private ImageButton btnVolumeDesk;
        private ImageButton btnStarDesk;
        private ConstraintLayout layoutForeground;
        public DeskFlashcatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTearmDesk = itemView.findViewById(R.id.txt_term_desk);
            txtDefinitionDesk = itemView.findViewById(R.id.txt_definition_desk);
            btnVolumeDesk = itemView.findViewById(R.id.btn_volume_term_desk);
            layoutForeground = itemView.findViewById(R.id.layout_foreground);
        }
    }
    public void removeItem(int index){
        flashcardArrayList.remove(index);
        notifyItemRemoved(index);
    }
    public void undoItem(Flashcard flashcard, int index){
        flashcardArrayList.add(index,flashcard);
        notifyItemInserted(index);
    }
}
