package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.ParcelPost;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ParcelPost post = (ParcelPost) Parcels.unwrap(getIntent().getParcelableExtra(ParcelPost.class.getSimpleName()));
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvDate = findViewById(R.id.tvDate);
        ImageView ivImage = findViewById(R.id.ivImage);

        tvUsername.setText(post.username);
        tvDescription.setText(post.description);
        tvDate.setText(post.date);
        ParseFile image = post.image;
        if (image != null) {
            Glide.with(this)
                    .load(image.getUrl())
                    .into(ivImage);
        }
    }
}
