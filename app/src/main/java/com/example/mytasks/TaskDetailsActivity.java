package com.example.mytasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    TextView textViewDescription, textViewStatus, textViewTitle;

    ImageView imageViewTask;

    Button btnSaveImage;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        setupView();
        getExtreIntent();
        getPhotos();


    }

    private void getPhotos() {
        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

    }

    private void getExtreIntent() {

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            boolean status = intent.getBooleanExtra("status", false);

            textViewTitle.setText(" عنوان وظیفه : "+title);
            textViewDescription.setText("  توضیح در مورد آن : "+description);
            textViewStatus.setText(status ? "انجام شده" : "در انتظار");
        }
    }

    private void setupView() {
        imageViewTask = findViewById(R.id.imageViewTask);
        btnSaveImage = findViewById(R.id.btnSaveImage);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewStatus = findViewById(R.id.textViewStatus);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageViewTask.setImageBitmap(bitmap);
                Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}