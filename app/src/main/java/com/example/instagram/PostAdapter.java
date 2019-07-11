package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.ParcelPost;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    private boolean isGrid;

    public PostAdapter(Context context, List<Post> posts, boolean isGrid) {
        this.context = context;
        this.posts = posts;
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (isGrid) {
            view = LayoutInflater.from(context).inflate(R.layout.item_post_grid, viewGroup, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvDate;
        private ImageView ibPfImage;
        private Button btnUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            if (!isGrid) {
                tvUsername = itemView.findViewById(R.id.tvUsername);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvDate = itemView.findViewById(R.id.tvDate);
                ibPfImage = itemView.findViewById(R.id.ibPfImagePost);
                btnUser = itemView.findViewById(R.id.btnUserDetail);
            }

            itemView.setOnClickListener(this);
        }

        public void bind(final Post post) {
            if (!isGrid) {
                tvUsername.setText(post.getUser().getUsername());
                tvDescription.setText(post.getDescription());
                tvDate.setText(Post.getRelativeTimeAgo(post.getCreatedAt()));

                ParseFile pfImage = (ParseFile) post.getUser().get("profilePicture");
                if (pfImage != null) {
                    Glide.with(context)
                            .load(pfImage.getUrl())
                            .placeholder(R.drawable.ic_user_filled)
                            .error(R.drawable.ic_user_filled)
                            .into(ibPfImage);
                } else {
                    Glide.with(context)
                            .load(R.drawable.ic_user_filled)
                            .placeholder(R.drawable.ic_user_filled)
                            .error(R.drawable.ic_user_filled)
                            .into(ibPfImage);
                }

                btnUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeActivity.targetUser = post.getUser();
                        Intent intent = new Intent(context, UserDetails.class);
                        context.startActivity(intent);
                    }
                });
            }

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Post post = posts.get(position);
                ParcelPost parcelPost = new ParcelPost(post.getUser().getUsername(),
                        post.getDescription(), Post.getRelativeTimeAgo(post.getCreatedAt()),
                        post.getImage(), post.getUser());

                Intent intent = new Intent(context, PostDetail.class);
                intent.putExtra(ParcelPost.class.getSimpleName(), Parcels.wrap(parcelPost));
                context.startActivity(intent);
            }
        }
    }
}
