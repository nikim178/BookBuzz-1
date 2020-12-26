package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectGenre extends AppCompatActivity {
        CheckBox cb1,cb2,cb3,cb4,cb5;
        Button btn1;
        TextView mResult;
        ArrayList<String> storeResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_genre);
        cb1=findViewById(R.id.checkBox1);
        cb2=findViewById(R.id.checkBox2);
        cb3=findViewById(R.id.checkBox3);
        cb4=findViewById(R.id.checkBox4);
        cb5=findViewById(R.id.checkBox5);
        btn1=findViewById(R.id.button);
        mResult=findViewById(R.id.textView);

        storeResult=new ArrayList<String>();
        mResult.setEnabled(false);

        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb1.isChecked())
                    storeResult.add("Fiction");
                else
                    storeResult.remove("Fiction");
            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb2.isChecked())
                    storeResult.add("romantic");
                else
                    storeResult.remove("romantic");
            }
        });
        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb3.isChecked())
                    storeResult.add("horror");
                else
                    storeResult.remove("horror");
            }
        });
        cb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb4.isChecked())
                    storeResult.add("Suspense");
                else
                    storeResult.remove("Suspense");
            }
        });
        cb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb5.isChecked())
                    storeResult.add("poetry");
                else
                    storeResult.remove("poetry");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder=new StringBuilder(5);
                for(String s: storeResult)
                    stringBuilder.append(s).append("\n");
                mResult.setText(stringBuilder.toString());
                mResult.setEnabled(false);
                Intent intent = new Intent(SelectGenre.this, SwipeGenre.class);
                intent.putExtra("book",storeResult);
                startActivity(intent);

            }
        });
    }
    }
