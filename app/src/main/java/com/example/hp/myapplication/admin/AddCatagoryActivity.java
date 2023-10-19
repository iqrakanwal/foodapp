package com.example.hp.myapplication.admin;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.myapplication.AddItemtofire;
import com.example.hp.myapplication.Model.Category;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCatagoryActivity extends AppCompatActivity {
    MaterialEditText name;
    ImageView addimagedoctor;
    CircleImageView profileimage;
    private FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
    private DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
Button addcatagory;


    public static final int PICK_IMAGE = 1;
    private String doctorPath = "";
    private Uri doctorFilePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catagory);
        addimagedoctor = findViewById(R.id.addimagecatagry);
        profileimage = findViewById(R.id.profileimage);
        name= findViewById(R.id.name);
        addcatagory= findViewById(R.id.addcatagory);
        addimagedoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        addcatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText())) {
                    name.setError("required");
                    name.requestFocus();
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
        Category doctor = new Category();
        doctor.setName(name.getText().toString());
        doctor.setImage(s);
        addDoctor(doctor);
        // adViewModel.adDoctor(doctor, this::Done);
    }


    public void addDoctor(Category doctor) {
        DatabaseReference databaseReference = mFirebaseDatabase.child("catagory");
        databaseReference.push().setValue(doctor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddCatagoryActivity.this, "kjhkejhr", Toast.LENGTH_SHORT);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }



}