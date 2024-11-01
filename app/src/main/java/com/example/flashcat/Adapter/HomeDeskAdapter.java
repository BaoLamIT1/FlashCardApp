package com.example.flashcat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcat.Activity.Desk.DeskActivity;
import com.example.flashcat.Model.Desk;
import com.example.flashcat.R;
import com.example.flashcat.TimeAgo;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeDeskAdapter extends RecyclerView.Adapter<HomeDeskAdapter.DeskViewHolder> implements Filterable {
    private ArrayList<Desk> deskArrayList;
    private ArrayList<Desk> deskListOld;
    private Context context;
    public void setData(ArrayList<Desk> deskArrayList){
        this.deskArrayList = deskArrayList;
        notifyDataSetChanged();
    }
    public HomeDeskAdapter(ArrayList<Desk> deskArrayList, Context context) {
        this.deskArrayList = deskArrayList;
        this.deskListOld = deskArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public DeskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_desk_home,parent,false);
        return new DeskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeskViewHolder holder, int position) {
        Desk desk = deskArrayList.get(position);
        if(desk == null)
            return;
        holder.txtDeskName.setText(desk.getName_deck());
        holder.txtNumberCard.setText(String.valueOf(desk.getNumber_flashcard()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDay = desk.getCreate_day();
        String formattedDateTime = createDay.format(formatter);

        holder.txtCreateDay.setText(TimeAgo.convertTimeAgo(formattedDateTime));
    }

    @Override
    public int getItemCount() {
        if(deskArrayList!=null)
            return deskArrayList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    deskArrayList = deskListOld;
                } else{
                    ArrayList<Desk> list = new ArrayList<>();
                    for(Desk desk : deskListOld){
                        if(desk.getName_deck().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(desk);
                        }
                    }
                    deskArrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = deskArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                deskArrayList = (ArrayList<Desk>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class DeskViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDeskName;
        private TextView txtNumberCard;
        private TextView txtCreateDay;
        int REQUEST_CODE =100;
        public DeskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDeskName = itemView.findViewById(R.id.txt_DeskName);
            txtDeskName.setPaintFlags(txtDeskName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            txtNumberCard = itemView.findViewById(R.id.txt_NumberCard);
            txtCreateDay = itemView.findViewById(R.id.txt_create_day);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DeskActivity.class);
                    Bundle b = new Bundle();
                    int ID= deskArrayList.get(getAdapterPosition()).getID_Deck();
                    b.putInt("ID_Desk",ID);
                    b.putString("Name_Desk" , txtDeskName.getText().toString());
                    i.putExtras(b);

                    ((Activity) context).startActivityForResult(i, REQUEST_CODE);                }
            });

        }
    }
    public void removeItem(int index){
        deskArrayList.remove(index);
        notifyItemRemoved(index);
    }
}

