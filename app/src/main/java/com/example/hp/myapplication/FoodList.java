package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.Interface.ItemClickListener;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //get intent here
        if(getIntent()!=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId!=null)
        {
            loadListFood(categoryId);
        }
    }

    private void loadListFood(String categoryId) {
        Query query = foodList.getRef().orderByChild("MenuId")
                .equalTo(categoryId);
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options)
            //is like select query
        {


            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder, int position, @NonNull Food model) {
                viewHolder.food_name.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //start activity
                        Intent foodDetails = new Intent(FoodList.this,FoodDetail.class);
                        foodDetails.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetails);

                    }
                });
            }

/*            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, final int position) {
                viewHolder.food_name.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();

                        //start activity
                        Intent foodDetails = new Intent(FoodList.this,FoodDetail.class);
                        foodDetails.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetails);

                    }
                });

            }*/
        };
        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }
}
