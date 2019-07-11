package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.HomeFragment;
import com.example.instagram.fragments.UserFragment;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static ParseUser targetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // defining fragments
        final Fragment fragment1 = new HomeFragment();
        final Fragment fragment2 = new ComposeFragment();
        final Fragment fragment3 = new UserFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.btnHome:
                        ((ComposeFragment) fragment2).homeItem = menuItem;
                        fragment = fragment1;
                        break;
                    case R.id.btnCompose:
                        fragment = fragment2;
                        break;
                    case R.id.btnUserDetail:
                    default:
                        HomeActivity.targetUser = ParseUser.getCurrentUser();
                        ((UserFragment) fragment3).userItem = menuItem;
                        fragment = fragment3;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.btnHome);

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itLogout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Log.d("HomeActivity", "Logout");

        ParseUser.logOut();
        HomeActivity.targetUser = ParseUser.getCurrentUser();
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
