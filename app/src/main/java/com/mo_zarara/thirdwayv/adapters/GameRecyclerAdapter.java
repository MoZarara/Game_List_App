package com.mo_zarara.thirdwayv.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mo_zarara.thirdwayv.R;
import com.mo_zarara.thirdwayv.pojo.GamesModel;
import com.mo_zarara.thirdwayv.pojo.GenresModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.View_Holder> {

    private List<GamesModel> list = new ArrayList<>();

    private static final String TAG = "GameRecyclerAdapter";

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new View_Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        holder.name.setText(list.get(position).getName());

        if (list.get(position).getGenres() != null) {
            String valueGenres;
            List<String> genresModelList = new ArrayList<>();

            for (int i = 0; i < list.get(position).getGenres().size(); i++) {
                genresModelList.add(list.get(position).getGenres().get(i).getName());
            }
            valueGenres = TextUtils.join(", ", genresModelList);
            holder.genres.setText(valueGenres);
        } else {
            holder.genres.setText("");
        }

        holder.released.setText(list.get(position).getReleased());

        
        Picasso.get().load(list.get(position).getBackground_image()).fit().centerCrop().
                placeholder(R.drawable.ic_baseline_crop_original_24).
                into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(List<GamesModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        public TextView released, genres, name;
        public ImageView image;


        public View_Holder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name_txt_id);
            genres = itemView.findViewById(R.id.genres_txt_id);
            released = itemView.findViewById(R.id.release_date_txt_id);
            image = itemView.findViewById(R.id.image_id);


        }
    }
}
