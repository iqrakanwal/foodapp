package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.Interface.ItemClickListener;
import com.example.hp.myapplication.Interface.MyCallback;
import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.ViewHolder.MenuViewHolder;
import com.example.hp.myapplication.adaptors.CategoryAdapter;
import com.example.hp.myapplication.adaptors.CatagoryAdaptor;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MyCallback {
RecyclerView recycler_menu;
    private CategoryAdapter adapter;
    private DatabaseReference databaseReference;
   // DatabaseReference  database;
    DatabaseReference category;

    TextView txtFullName;
    RecyclerView recycle_menu;
 //   Button add;

    GridLayoutManager layoutManager;
    private CatagoryAdaptor animalAdaptor;
    private ArrayList<Category> arrayList;
 //   private LinearLayoutManager layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        //add= findViewById(R.id.add);
        setSupportActionBar(toolbar);
        recycler_menu = findViewById(R.id.recycler_menu);
        arrayList = new ArrayList<>();
        //Firebase:
        category = FirebaseDatabase.getInstance().getReference().child("catagory");
       // category = database.getReference().child("cheese");
       // DatabaseReference rootRef =
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(Home.this,Cart.class);
                startActivity(cart);
            }
        });
/*        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this,AddItemtofire.class );
                startActivity(intent);
            }
        });*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        txtFullName = (TextView)headerview.findViewById(R.id.txtFullName);
     //   txtFullName.setText(Common.currentUser.getName());
        //Menu
        recycle_menu  = (RecyclerView)findViewById(R.id.recycler_menu);
        recycle_menu.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recycle_menu.setLayoutManager(layoutManager);
       // loadMenu();
        getAllAnimals(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
      //  adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //adapter.stopListening();
    }
    private void loadMenu() {

        FirebaseDatabase.getInstance().getReference().child("catagory").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
           /*     adapter = new CategoryAdapter(options);
                recycle_menu.setAdapter(adapter);*/
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("catagory");
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(databaseReference, Category.class)
                .build();

        adapter = new CategoryAdapter(options);
        recycle_menu.setAdapter(adapter);
      /*  Query query =  category.getRef();
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
            adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder viewHolder, int position, @NonNull Category model) {
                viewHolder.txtMenuName.setText(model.getName());
                //Picasso.get(getApplicationContext()).load(model.getImage()).into(viewHolder.imageView);
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodlist = new Intent(Home.this,FoodList.class);
                        foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodlist);
                    }
                });
            }


        };
        recycle_menu.setAdapter(adapter);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_log_out) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCallback(ArrayList<Category> animals) {
        Toast.makeText(this, ""+animals.size(), Toast.LENGTH_SHORT).show();
        if (animals.size() == 0) {
            Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
        } else {
            arrayList.addAll(animals);
            animalAdaptor = new CatagoryAdaptor(this, arrayList, new CatagoryAdaptor.AnimalClickListener() {
                @Override
                public void onAnimalClick(Category animal) {

                    Toast.makeText(Home.this,""+animal.getName(),Toast.LENGTH_SHORT).show();
                    //start activity
                    Intent foodDetails = new Intent(Home.this,FoodList.class);
                  //  DataHolder.getInstance().setSharedData(animal);
                   foodDetails.putExtra("CategoryId",animal.getName());
                    startActivity(foodDetails);
                }
            });
            recycler_menu.setLayoutManager(layoutManager);
            RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
            recycler_menu.addItemDecoration(dividerItemDecoration);
            recycler_menu.setAdapter(animalAdaptor);
        }


    }


    public void getAllAnimals(final MyCallback callback) {
        final ArrayList<Category> animals = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("catagory");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Category animal = snapshot.getValue(Category.class);
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
