package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class SearchUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*Extra alternate code Don't delete
        Intent i= getIntent();
       String name= i.getStringExtra("name");
        String location= i.getStringExtra("location");
        String zipcode= i.getStringExtra("zipcode");
        String email= i.getStringExtra("email");
        String url= i.getStringExtra("url");
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new indiFragment(name,location,zipcode,email,url)).commit();*/

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new userFragment()).commit();

    }
}