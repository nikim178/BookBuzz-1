package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(
            Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set the content of the activity
        // to use the activity_main.xml
        // layout file
        setContentView(R.layout.activity_splash);

        // Find the view pager that will
        // allow the user to swipe
        // between fragments
        ViewPager viewPager
                = (ViewPager)findViewById(
                R.id.viewpager);

        // Create an adapter that
        // knows which fragment should
        // be shown on each page
        SwipeAdapter
                adapter
                = new SwipeAdapter(
                getSupportFragmentManager());

        // Set the adapter onto
        // the view pager
        viewPager.setAdapter(adapter);
    }
    public void shift(View v){
        Intent i= new Intent(this,Home.class);
        startActivity(i);
    }
}