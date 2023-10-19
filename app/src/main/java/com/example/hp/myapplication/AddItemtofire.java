package com.example.hp.myapplication;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static com.example.hp.myapplication.Common.Common.currentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.myapplication.Interface.MyCatagoryCallback;
import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.Model.Food;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddItemtofire extends AppCompatActivity {
    private ArrayList<String> farmName;
    private ArrayList<Category> farmsd;
    ImageView addimagedoctor;
    CircleImageView profileimage;
    EditText nameofcheese;
    EditText price;
    Spinner catagories;
    Button saveinformation;
    EditText description_et;
EditText dis_et;
EditText menu_et;
    private FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
    private DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    public static final int PICK_IMAGE = 1;
    private String doctorPath = "";
    private Uri doctorFilePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemtofire);
        addimagedoctor = findViewById(R.id.addimagedoctor);
        nameofcheese = findViewById(R.id.name_et);
        description_et = findViewById(R.id.description_et);
        farmName = new ArrayList<>();
        price = findViewById(R.id.price_et);
        dis_et= findViewById(R.id.dis_et);
        catagories= findViewById(R.id.catagoryspinner);
        getAllCatagory(new MyCatagoryCallback() {
            @Override
            public void onCallback(ArrayList<Category> animals) {
            //    farmName = new ArrayList<>();
                Toast.makeText(AddItemtofire.this, String.valueOf(animals.size()), Toast.LENGTH_SHORT).show();

                for (Category number : animals) {
                    farmName.add(number.getName());
                    Log.e("szdjsdkj", number.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        AddItemtofire.this,
                        android.R.layout.simple_spinner_item,
                        farmName
                );
           //     adapter.setDropDownViewResource(android.);
              adapter.setDropDownViewResource(                        android.R.layout.simple_spinner_item);
                catagories.setAdapter(adapter);
                catagories.getSelectedItem().toString();
            }
        });
        saveinformation = findViewById(R.id.saveinformation);
        profileimage = findViewById(R.id.profileimage);
        addimagedoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        // nameofcheese.setText(currentUser.getName());
        //    description.setText(currentUser.getEmail());

        saveinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(description_et.getText())) {
                    description_et.setError("required");
                    description_et.requestFocus();
                } else {
                    uploadProfileImage(doctorFilePath);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (data == null || data.getData() == null) {
                return;
            }
            doctorFilePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        doctorFilePath
                );
                profileimage.setImageBitmap(bitmap);
                //uploadImage(coverFilePath); // Uncomment if needed

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadProfileImage(Uri filePath) {
            uploadImage(filePath, this);
    }

    public void uploadImage(Uri bitmap, Context context) {
        Log.e("reference", storageReference.toString());
        if (bitmap != null) {
            StorageReference ref = storageReference.child("uploads/" + UUID.randomUUID().toString());
            UploadTask uploadTask = ref.putFile(bitmap);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        uploadedProfilePhoto(downloadUri.toString());
                    } else {
                        // Handle failures
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    private void uploadedProfilePhoto(String s) {
        Food doctor = new Food();
        doctor.setName(nameofcheese.getText().toString());
        doctor.setDescription(description_et.getText().toString());
        doctor.setImage(s);
        doctor.setPrice(price.getText().toString());
        doctor.setDiscount(dis_et.getText().toString());
        doctor.setCategory(
                new Category(catagories.getSelectedItem().toString(),
                        farmsd.get(catagories.getSelectedItemPosition()).getImage().toString())  );
        addDoctor(doctor);
        // adViewModel.adDoctor(doctor, this::Done);
    }


    public void addDoctor(Food doctor) {
        DatabaseReference databaseReference = mFirebaseDatabase.child("Food");
        databaseReference.push().setValue(doctor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddItemtofire.this, "kjhkejhr", Toast.LENGTH_SHORT);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    private void Done(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }


    public void getAllCatagory(final MyCatagoryCallback callback) {
        farmsd = new ArrayList<>();
        mFirebaseDatabase.getRef().child("catagory").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category day = snapshot.getValue(Category.class);
                    farmsd.add(day);
                }
                callback.onCallback(farmsd);
         //       callback.onSuccess(farmsd);
            }
        });


    }


}