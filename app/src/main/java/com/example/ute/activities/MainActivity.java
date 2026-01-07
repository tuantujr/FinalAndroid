package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ute.R;
import com.example.ute.fragments.CartFragment;
import com.example.ute.fragments.CategoriesFragment;
import com.example.ute.fragments.HomeFragment;
import com.example.ute.fragments.ProfileFragment;
import com.example.ute.utils.SessionManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    
    private BottomNavigationView bottomNavigationView;
    private ImageView ivSearch;
    private ImageView ivAccount;
    private ImageView ivFavorites;
    private ImageView ivCart;
    private TextView tvCartBadge;
    
    private SessionManager sessionManager;
    
    private Fragment homeFragment;
    private Fragment categoriesFragment;
    private Fragment cartFragment;
    private Fragment profileFragment;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sessionManager = new SessionManager(this);
        
        initViews();
        setupBottomNavigation();
        setupToolbarActions();
        
        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }
    
    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ivSearch = findViewById(R.id.ivSearch);
        ivAccount = findViewById(R.id.ivAccount);
        ivFavorites = findViewById(R.id.ivFavorites);
        ivCart = findViewById(R.id.ivCart);
        tvCartBadge = findViewById(R.id.tvCartBadge);
    }
    
    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(this);
        
        // Set up fragments
        homeFragment = new HomeFragment();
        categoriesFragment = new CategoriesFragment();
        cartFragment = new CartFragment();
        profileFragment = new ProfileFragment();
        activeFragment = homeFragment;
        
        // Add all fragments but hide all except home
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, profileFragment, "profile").hide(profileFragment)
                .add(R.id.fragmentContainer, cartFragment, "cart").hide(cartFragment)
                .add(R.id.fragmentContainer, categoriesFragment, "categories").hide(categoriesFragment)
                .add(R.id.fragmentContainer, homeFragment, "home")
                .commit();
    }
    
    private void setupToolbarActions() {
        ivSearch.setOnClickListener(v -> {
            // Open search activity
            startActivity(new Intent(this, SearchActivity.class));
        });
        
        ivAccount.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
        
        ivFavorites.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                startActivity(new Intent(this, FavoritesActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
        
        ivCart.setOnClickListener(v -> {
            bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        });
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            selectedFragment = homeFragment;
        } else if (itemId == R.id.nav_categories) {
            selectedFragment = categoriesFragment;
        } else if (itemId == R.id.nav_cart) {
            selectedFragment = cartFragment;
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = profileFragment;
        }
        
        if (selectedFragment != null && selectedFragment != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment)
                    .show(selectedFragment)
                    .commit();
            activeFragment = selectedFragment;
            return true;
        }
        
        return false;
    }
    
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
    
    public void updateCartBadge(int count) {
        if (count > 0) {
            tvCartBadge.setVisibility(View.VISIBLE);
            tvCartBadge.setText(count > 99 ? "99+" : String.valueOf(count));
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Update cart badge when activity resumes
        // TODO: Fetch cart count from API or local storage
    }
}
