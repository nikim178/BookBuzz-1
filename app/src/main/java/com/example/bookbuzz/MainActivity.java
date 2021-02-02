package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test=findViewById(R.id.button6);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            // This is just for test purpose
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Trial.class));
            }
        });
    }
}