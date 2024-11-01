package com.example.flashcat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Model.Dictionary.Phonetics;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;

import java.io.IOException;
import java.util.ArrayList;

public class MinusAdapter extends RecyclerView.Adapter<MinusAdapter.MinusViewHolder> {
    private ArrayList<Phonetics> minusArrayList;
    private Context context;

    public void setData(ArrayList<Phonetics> minus){
        this.minusArrayList = minus;
        notifyDataSetChanged();
    }
    public MinusAdapter(ArrayList<Phonetics> minus, Context context) {
        this.minusArrayList = minus;
        this.context = context;
    }
    @NonNull
    @Override
    public MinusAdapter.MinusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minus_layout,parent,false);
        return new MinusViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MinusAdapter.MinusViewHolder holder, int position) {
        holder.minusTextView.setText(minusArrayList.get(position).getText());
        holder.btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = new MediaPlayer();
                try{
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(minusArrayList.get(position).getAudio());
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                   e.printStackTrace();
                    Toast.makeText(context, "Couldn't play audio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return minusArrayList.size();
    }

    public class MinusViewHolder extends RecyclerView.ViewHolder {

        private TextView minusTextView;
        private ImageButton btnSound;
        public MinusViewHolder(@NonNull View itemView) {
            super(itemView);
            minusTextView = itemView.findViewById(R.id.txt_minus_word);
            btnSound = itemView.findViewById(R.id.btn_volume_word);
        }


    }
}
