package com.isroot.echocaulator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {
    public Object binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResource());
    }

    protected abstract int getLayoutResource();

    protected Object getBinding() {
        return binding;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

