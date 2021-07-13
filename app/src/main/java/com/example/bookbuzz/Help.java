package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class Help extends AppCompatActivity {
    androidx.appcompat.app.ActionBar actionBar;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
            actionBar=getSupportActionBar();
            ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#62A6BF"));
            actionBar.setBackgroundDrawable(colorDrawable);
                }
    }
