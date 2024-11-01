package com.example.flashcat.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Activity.Desk.CreateFlashcardActivity;
import com.example.flashcat.Activity.Desk.DeskActivity;
import com.example.flashcat.Activity.Desk.FlashcardActivity;
import com.example.flashcat.Model.Flashcard;
import com.example.flashcat.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

public class DeskFlashcardTopAdapter extends RecyclerView.Adapter<DeskFlashcardTopAdapter.DeskTopViewHolder>{
    private ArrayList<Flashcard> flashcardArrayList;
    private Context context;
    private static final int TYPE_FLASHCARD = 1;
    private static final int TYPE_INTERACTIVE_LAYOUT = 2;
    private boolean status = true;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public DeskFlashcardTopAdapter(ArrayList<Flashcard> flashcardArrayList, Context context) {
        this.flashcardArrayList = flashcardArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeskTopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FLASHCARD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_flashcard_top_desk, parent, false);
            return new DeskTopViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_create_flashcard, parent, false);
            return new DeskTopViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DeskTopViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (getItemViewType(position) == TYPE_FLASHCARD) {
            Flashcard flashcard = flashcardArrayList.get(position);
            if(flashcard == null)
                return;
            holder.txtTerm.setText(flashcard.getTerm());
            holder.txtDefinition.setText(flashcard.getDefinition());

            holder.btnZoomFront.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setStatus(false);
                    // Xử lý sự kiện khi btnZoomFront được click
                    Intent i = new Intent(holder.itemView.getContext(),FlashcardActivity.class);
                    i.putExtra("idFlashcard", flashcard.getID_Flashcard());
                    i.putExtra("idDesk",flashcard.getID_Deck());
                    i.putExtra("flipFrom","front");
                    context.startActivity(i);
                    setStatus(true);
                }
            });
            holder.btnZoomBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi btnZoomBack được click
                    setStatus(false);
                    Intent i = new Intent(holder.itemView.getContext(),FlashcardActivity.class);
                    i.putExtra("idFlashcard", flashcard.getID_Flashcard());
                    i.putExtra("idDesk",flashcard.getID_Deck());
                    i.putExtra("flipFrom","back");
                    context.startActivity(i);
                    setStatus(true);
                }
            });
            // Bắt sự kiện click cho EasyFlipView
            holder.easyFlipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi EasyFlipView được click
                    // kiểm tra điều kiện trước khi lật EasyFlipView
                    if (isStatus()) {
                        holder.easyFlipView.flipTheView();
                    }
                }
            });
        }
        else{
            holder.btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, CreateFlashcardActivity.class);
                    i.putExtra("id_desk",flashcardArrayList.get(position).getID_Deck());
                    ((Activity) context).startActivity(i);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(flashcardArrayList!=null){
            return flashcardArrayList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == flashcardArrayList.size()-1)
            return TYPE_INTERACTIVE_LAYOUT;
        else
            return TYPE_FLASHCARD;
    }

    public class DeskTopViewHolder extends RecyclerView.ViewHolder{
        private ImageButton btnZoomFront;
        private ImageButton btnZoomBack;
        private TextView txtTerm;
        private TextView txtDefinition;
        private ImageButton btnCreate;
        private EasyFlipView easyFlipView;
        public DeskTopViewHolder(@NonNull View itemView) {
            super(itemView);
            btnZoomFront = itemView.findViewById(R.id.btn_zoom_front);
            btnZoomBack = itemView.findViewById(R.id.btn_zoom_back);
            txtTerm = itemView.findViewById(R.id.txt_term_flashcard);
            txtDefinition = itemView.findViewById(R.id.txt_definition_flashcard);
            btnCreate = itemView.findViewById(R.id.btn_create_new);
            easyFlipView = itemView.findViewById(R.id.easyFlipFlashcard);
        }
    }
}
