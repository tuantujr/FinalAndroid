package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.ute.fragments.VouchersFragment;
import com.example.ute.fragments.HomeFragment;
import com.example.ute.fragments.ProfileFragment;
import com.example.ute.utils.SessionManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    
    private static final String TAG = "MainActivity";
    
    private BottomNavigationView bottomNavigationView;
    private ImageView ivSearch;
    private ImageView ivAccount;
    private ImageView ivCart;
    private TextView tvCartBadge;
    
    private SessionManager sessionManager;
    
    private Fragment homeFragment;
    private Fragment vouchersFragment;
    private Fragment cartFragment;
    private Fragment profileFragment;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate started");
        
        try {
            setContentView(R.layout.activity_main);
            Log.d(TAG, "setContentView done");
            
            sessionManager = new SessionManager(this);
            
            initViews();
            Log.d(TAG, "initViews done");
            
            setupBottomNavigation();
            Log.d(TAG, "setupBottomNavigation done");
            
            setupToolbarActions();
            Log.d(TAG, "setupToolbarActions done");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
        }
    }
    
    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ivSearch = findViewById(R.id.ivSearch);
        ivAccount = findViewById(R.id.ivAccount);
        ivCart = findViewById(R.id.ivCart);
        tvCartBadge = findViewById(R.id.tvCartBadge);
        
        // Make search bar clickable
        View cardSearch = findViewById(R.id.cardSearch);
        if (cardSearch != null) {
            cardSearch.setOnClickListener(v -> {
                startActivity(new Intent(this, SearchActivity.class));
            });
        }
    }
    
    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(this);
        
        // Set up fragments
        homeFragment = new HomeFragment();
        vouchersFragment = new VouchersFragment();
        cartFragment = new CartFragment();
        profileFragment = new ProfileFragment();
        activeFragment = homeFragment;
        
        // Add all fragments but hide all except home
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, profileFragment, "profile").hide(profileFragment)
                .add(R.id.fragmentContainer, cartFragment, "cart").hide(cartFragment)
                .add(R.id.fragmentContainer, vouchersFragment, "vouchers").hide(vouchersFragment)
                .add(R.id.fragmentContainer, homeFragment, "home")
                .commit();
    }
    
    private void setupToolbarActions() {
        // Search icon click
        if (ivSearch != null) {
            ivSearch.setOnClickListener(v -> {
                startActivity(new Intent(this, SearchActivity.class));
            });
        }
        
        // Account icon click
        if (ivAccount != null) {
            ivAccount.setOnClickListener(v -> {
                if (sessionManager.isLoggedIn()) {
                    bottomNavigationView.setSelectedItemId(R.id.nav_profile);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
            });
        }
        
        // Cart icon click - using layoutCart wrapper
        View layoutCart = findViewById(R.id.layoutCart);
        if (layoutCart != null) {
            layoutCart.setOnClickListener(v -> {
                bottomNavigationView.setSelectedItemId(R.id.nav_cart);
            });
        } else if (ivCart != null) {
            ivCart.setOnClickListener(v -> {
                bottomNavigationView.setSelectedItemId(R.id.nav_cart);
            });
        }
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            selectedFragment = homeFragment;
        } else if (itemId == R.id.nav_vouchers) {
            selectedFragment = vouchersFragment;
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
