package com.example.flashcat.Adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.io.IOException;
import java.util.ArrayList;

public class FlashcatAdapter extends RecyclerView.Adapter<FlashcatAdapter.FlashcardViewHolder>{
    private ArrayList<Flashcard> flashcardArrayList;
    private Context context;
    private int wordLearned;
    private int currentPosition = 0; // Theo dõi vị trí hiện tại
    private int draggedItemPosition = -1;
    private boolean status = true;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }

    public FlashcatAdapter(ArrayList<Flashcard> flashcardArrayList, Context context) {
        this.flashcardArrayList = flashcardArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_flashcard,parent,false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Flashcard flashcard = flashcardArrayList.get(position);
        holder.txtTerm.setText(flashcard.getTerm());
        holder.txtDefinition.setText(flashcard.getDefinition());
        holder.itemView.setTag("Drag");
        if (position == draggedItemPosition) {
            // Đặt màu nền xám nhạt cho item khi đang được kéo
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setTag("Drag");
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mineTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(v.getTag().toString(), mineTypes, item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(data,dragShadowBuilder,v,0);
                draggedItemPosition = position;
                notifyDataSetChanged();
                return true;
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mineTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(v.getTag().toString(), mineTypes, item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(data,dragShadowBuilder,v,0);
                return true;
            }
        });
        holder.btnVolumeTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(false);
                Log.d("volum", "onClick: ");
                MediaPlayer player = new MediaPlayer();
                try{
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(flashcardArrayList.get(position).getSound());
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Couldn't play audio", Toast.LENGTH_SHORT).show();
                }
                setStatus(true);
            }
        });
        holder.btnVolumeDefinition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(false);
                //xu ly su kien click Volume

                setStatus(true);
            }
        });
        holder.easyFlipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStatus()) {
                    holder.easyFlipView.flipTheView();
                }
            }
        });
    }
    private void updateNumberOfWordsLearned() {
        wordLearned = 0;
        for (Flashcard flashcard : flashcardArrayList) {
            if (flashcard.isStatus()) {
                wordLearned++;
            }
        }
    }
    public void updateStatus(int position, boolean newStatus) {
        Flashcard flashcard = flashcardArrayList.get(position);
        flashcard.setStatus(newStatus);
        notifyItemChanged(position);
    }
    public int getNumberOfWordsLearned() {
        return wordLearned;
    }
    @Override
    public int getItemCount() {
        if(flashcardArrayList != null){
            return flashcardArrayList.size();
        }
        return 0;
    }


    public class FlashcardViewHolder extends RecyclerView.ViewHolder{
        private TextView txtWordLearned;
        private TextView txtWordUnLearned;
        private TextView txtTerm;
        private TextView txtDefinition;
        private ImageButton btnVolumeTerm;
        private ImageButton btnVolumeDefinition;
        private EasyFlipView easyFlipView;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWordLearned = itemView.findViewById(R.id.txt_win);
            txtWordUnLearned = itemView.findViewById(R.id.txt_lose);
            txtDefinition = itemView.findViewById(R.id.definition_flashcard);
            txtTerm = itemView.findViewById(R.id.term_flashcard);
            btnVolumeDefinition = itemView.findViewById(R.id.btn_volume_definition);
            btnVolumeTerm = itemView.findViewById(R.id.btn_volume_term);
            easyFlipView = itemView.findViewById(R.id.easy_flip_flashcard);
        }
    }
    public void undo(int index){

    }
}
