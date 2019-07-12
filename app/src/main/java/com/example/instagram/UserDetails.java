package com.example.instagram;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.example.instagram.utils.EndlessRecyclerViewScrollListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class UserDetails extends AppCompatActivity {
    private RecyclerView rvPosts;
    private PostAdapter adapter;
    private List<Post> mPosts;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        mPosts = new ArrayList<>();
        rvPosts = findViewById(R.id.rvPosts);
        adapter = new PostAdapter(this, mPosts, true);
        rvPosts.setAdapter(adapter);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        GridLayoutManager glm = new GridLayoutManager(this, 3);
        rvPosts.setLayoutManager(glm);

        TextView tvUsernamePage = findViewById(R.id.tvUsernamePage);
        tvUsernamePage.setText(HomeActivity.targetUser.getUsername());

        ImageView ivProfile = findViewById(R.id.ivProfile);
        ParseFile imageFile = HomeActivity.targetUser.getParseFile("profilePicture");
        if (imageFile != null) {
            Glide.with(this)
                    .load(imageFile.getUrl())
                    .placeholder(R.drawable.ic_user_filled)
                    .error(R.drawable.ic_user_filled)
                    .into(ivProfile);
        } else {
            Glide.with(this)
                    .load(R.drawable.ic_user_filled)
                    .placeholder(R.drawable.ic_user_filled)
                    .error(R.drawable.ic_user_filled)
                    .into(ivProfile);
        }

        scrollListener = new EndlessRecyclerViewScrollListener(glm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                HomeActivity.offset += 20;
                loadTopPosts(HomeActivity.offset);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvPosts.addOnScrollListener(scrollListener);

        loadTopPosts(HomeActivity.offset);
    }

    private void refresh() {
        HomeActivity.offset = 0;
        mPosts.clear();
        adapter.notifyDataSetChanged();
        loadTopPosts(HomeActivity.offset);
        swipeContainer.setRefreshing(false);
    }

    private void loadTopPosts(int offset) {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();
        postQuery.setLimit(20);
        postQuery.setSkip(offset);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.whereEqualTo(Post.KEY_USER, HomeActivity.targetUser);

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    mPosts.addAll(objects);
                    adapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
