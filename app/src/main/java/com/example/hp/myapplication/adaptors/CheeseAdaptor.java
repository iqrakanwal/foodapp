package com.example.hp.myapplication.adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.R;

import java.util.ArrayList;

public class CheeseAdaptor extends RecyclerView.Adapter<CheeseAdaptor.ImageItem> {
    private Context context;
    private ArrayList<Food> images;
    private AnimalClickListener animalClickListener;

    public CheeseAdaptor(Context context, ArrayList<Food> images, AnimalClickListener animalClickListener) {
        this.context = context;
        this.images = images;
        this.animalClickListener = animalClickListener;
    }

    @Override
    public ImageItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.safe_box_item, parent, false);
        return new ImageItem(view);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onBindViewHolder(ImageItem holder, int position) {
        holder.name.setText(images.get(position).getName());
        holder.price.setText(images.get(position).getPrice());
        holder.catagory.setText(images.get(position).getDescription());
        holder.farmname.setText(images.get(position).getPrice());
        holder.item.setOnClickListener(view -> animalClickListener.onAnimalClick(images.get(position)));
        Glide.with(context).load(images.get(position).getImage()).into(holder.image);
    }

    public void filterList(ArrayList<Food> filterList) {
        images = filterList;
        notifyDataSetChanged();
    }

    public interface AnimalClickListener {
        void onAnimalClick(Food animal);
    }

    public class ImageItem extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView catagory;
        private TextView farmname;
        private TextView price;
        private ImageView image;
        private ConstraintLayout item;

        public ImageItem(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            catagory = view.findViewById(R.id.catagory);
            farmname = view.findViewById(R.id.farmname);
            price = view.findViewById(R.id.price);
            image = view.findViewById(R.id.image);
            item = view.findViewById(R.id.item);
        }
    }
}
