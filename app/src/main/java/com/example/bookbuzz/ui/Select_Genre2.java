package com.example.bookbuzz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookbuzz.R;
import com.example.bookbuzz.SwipeGenre;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Select_Genre2 extends AppCompatActivity {
    @BindView(R.id.checkBox)
    MaterialCheckBox cb1;
    @BindView(R.id.checkBox2)
    MaterialCheckBox cb2;
    @BindView(R.id.checkBox3)
    MaterialCheckBox cb3;
    @BindView(R.id.checkBox4)
    MaterialCheckBox cb4;
    @BindView(R.id.checkBox5)
    MaterialCheckBox cb5;
    @BindView(R.id.checkBox6)
    MaterialCheckBox cb6;
    Button btn1;

    ArrayList<String> storeResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__genre2);
        ButterKnife.bind(this);
        btn1=findViewById(R.id.button);


        storeResult=new ArrayList<String>();

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

                Intent intent = new Intent(Select_Genre2.this, SwipeGenre.class);
                intent.putExtra("book",storeResult);
                startActivity(intent);

            }
        });
    }


}