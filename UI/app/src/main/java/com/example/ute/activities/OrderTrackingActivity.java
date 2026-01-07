package com.example.ute.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ute.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class OrderTrackingActivity extends AppCompatActivity {

    private TextInputEditText etOrderCode;
    private MaterialButton btnTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        setupToolbar();
        initViews();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.order_tracking);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        etOrderCode = findViewById(R.id.etOrderCode);
        btnTrack = findViewById(R.id.btnTrack);

        btnTrack.setOnClickListener(v -> trackOrder());
    }

    private void trackOrder() {
        String orderCode = etOrderCode.getText().toString().trim();
        if (orderCode.isEmpty()) {
            etOrderCode.setError(getString(R.string.field_required));
            return;
        }

        // TODO: Track order API call
        Toast.makeText(this, R.string.loading, Toast.LENGTH_SHORT).show();
    }
}
