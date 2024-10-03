package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_detail);

        // initialize the views
        TextView postsTitle = findViewById(R.id.titleDetail);
        ImageView postsImage = findViewById(R.id.postsImageDetail);

        // set the text from the intent extra
        postsTitle.setText(getIntent().getStringExtra("title"));
        // load the image using glide and intent extra
        Glide.with(this).load(getIntent().getIntExtra("image_resource",0))
                .into(postsImage);
    }
}