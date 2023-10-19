package com.example.hp.myapplication.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Category model) {
        // Bind the data to the ViewHolder
        holder.bind(model);

        holder.categoryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView categoryImageView;
        private final TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.categoryImageView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
        }

        public void bind(Category category) {
            // Display category name
            categoryNameTextView.setText(category.getName());
            categoryImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                }
            });
            // Load and display the category image using Picasso or your preferred image loading library
            if (category.getImage() != null && !category.getImage().isEmpty()) {
                Picasso.get().load(category.getImage()).into(categoryImageView);
            }
        }
    }
}