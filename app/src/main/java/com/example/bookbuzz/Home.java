package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private Button uSignOutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        uSignOutButton=findViewById(R.id.button3);
        mAuth = FirebaseAuth.getInstance();
        //Code for Log out button
        uSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Home.this,UserLogin.class));
                finish();
            }
        });


        }
        public void search(View v){
        Intent i=new Intent(this,SearchBook.class);
        startActivity(i);
        }
    }
