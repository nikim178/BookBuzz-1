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
        CheckBox cb1,cb2,cb3,cb4,cb5,cb6;
        Button btn1;
        TextView mResult;
        ArrayList<String> storeResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_genre);
        cb1=findViewById(R.id.checkBox);
        cb2=findViewById(R.id.checkBox2);
        cb3=findViewById(R.id.checkBox3);
        cb4=findViewById(R.id.checkBox4);
        cb5=findViewById(R.id.checkBox5);
        cb6=findViewById(R.id.checkBox6);
        btn1=findViewById(R.id.button);
        mResult=findViewById(R.id.textView);

        storeResult=new ArrayList<String>();
        mResult.setEnabled(false);

        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb1.isChecked())
                    storeResult.add("NonFiction");
                else
                    storeResult.remove("NonFiction");
            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb2.isChecked())
                    storeResult.add("Romance");
                else
                    storeResult.remove("Romance");
            }
        });
        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb3.isChecked())
                    storeResult.add("Fantasy");
                else
                    storeResult.remove("Fantasy");
            }
        });
        cb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb4.isChecked())
                    storeResult.add("SelfHelp");
                else
                    storeResult.remove("SelfHelp");
            }
        });
        cb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb5.isChecked())
                    storeResult.add("Detective");
                else
                    storeResult.remove("Detective");
            }
        });
        cb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb6.isChecked())
                    storeResult.add("Comic");
                else
                    storeResult.remove("Comic");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder=new StringBuilder(6);
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
