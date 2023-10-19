package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hp.myapplication.Interface.MyCallback;
import com.example.hp.myapplication.Interface.MyFoodCallback;
import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.adaptors.CatagoryAdaptor;
import com.example.hp.myapplication.adaptors.FoodAdaptor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity {
ArrayList<Food> foodArrayList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId;
    FoodAdaptor animalAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");
        foodArrayList= new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //get intent here
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            getAllFood(
                    new MyFoodCallback() {
                        @Override
                        public void onCallback(ArrayList<Food> animals) {
                            Toast.makeText(FoodList.this, ""+animals.size(), Toast.LENGTH_SHORT).show();
                            if (animals.size() == 0) {
                                Toast.makeText(FoodList.this, "Not Available", Toast.LENGTH_SHORT).show();
                            } else {
                                foodArrayList.addAll(animals);
                                animalAdaptor = new FoodAdaptor(FoodList.this, foodArrayList, new FoodAdaptor.AnimalClickListener() {
                                    @Override
                                    public void onAnimalClick(Food animal) {
                                        Intent foodDetails = new Intent(FoodList.this, FoodDetail.class);
                                        foodDetails.putExtra("FoodId",animal.getKey());
                                        startActivity(foodDetails);
                                     /*   Toast.makeText(Home.this,""+animal.getName(),Toast.LENGTH_SHORT).show();
                                        //start activity
                                        Intent foodDetails = new Intent(Home.this,FoodList.class);
                                        //  DataHolder.getInstance().setSharedData(animal);

                                        startActivity(foodDetails);*/
                                    }
                                });
                                recyclerView.setLayoutManager(layoutManager);
                         //       RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
                            //    recyclerView.addItemDecoration(dividerItemDecoration);
                                recyclerView.setAdapter(animalAdaptor);
                            }
                        }
                    });
        }
    }
    public void getAllFood(final MyFoodCallback callback) {
        final ArrayList<Food> animals = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Food");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food animal = snapshot.getValue(Food.class);
                    animal.setKey(snapshot.getKey());
                    if (animal != null) {
                        animals.add(animal);
                    }
                }
                callback.onCallback(animals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });


    }


}
