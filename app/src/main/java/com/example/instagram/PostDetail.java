package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

        final ParcelPost post = (ParcelPost) Parcels.unwrap(getIntent().getParcelableExtra(ParcelPost.class.getSimpleName()));
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvDate = findViewById(R.id.tvDate);
        ImageView ivImage = findViewById(R.id.ivImage);
        ImageView ibPfImage = findViewById(R.id.ibPfImagePost);
        Button btnUserDetail = findViewById(R.id.btnUserDetail);

        tvUsername.setText(post.username);
        tvDescription.setText(post.description);
        tvDate.setText(post.date);
        ParseFile image = post.image;
        if (image != null) {
            Glide.with(this)
                    .load(image.getUrl())
                    .into(ivImage);
        }

        ParseFile pfImage = (ParseFile) post.user.get("profilePicture");
        if (pfImage != null) {
            Glide.with(this)
                    .load(pfImage.getUrl())
                    .placeholder(R.drawable.ic_user_filled)
                    .error(R.drawable.ic_user_filled)
                    .into(ibPfImage);
        } else {
            Glide.with(this)
                    .load(R.drawable.ic_user_filled)
                    .placeholder(R.drawable.ic_user_filled)
                    .error(R.drawable.ic_user_filled)
                    .into(ibPfImage);
        }

        btnUserDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.targetUser = post.user;
                Intent intent = new Intent(PostDetail.this, UserDetails.class);
                startActivity(intent);
            }
        });
    }
}
