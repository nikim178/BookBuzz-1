package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button test;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test=findViewById(R.id.button6);
        text=findViewById(R.id.textView5);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            // This is just for test purpose
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,UserLogin2.class);
                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(test,"logo_image");
                pairs[1]=new Pair<View,String>(text,"logo_text");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }


}