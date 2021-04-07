package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private Button uSignOutButton, myProfile,friend;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        uSignOutButton=findViewById(R.id.button3);
        myProfile= findViewById(R.id.button9);
        friend=findViewById(R.id.button12);

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

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Profile.class));
                finish();
            }
        });

    }
        public void genre(View v){
        Intent i=new Intent(this,SelectGenre.class);
        startActivity(i);
    }

    public void homePage(View v){
        Intent i=new Intent( this, HomeActivity.class);
        startActivity(i);
    }
    public void pendingRequest(View v){
        Intent i=new Intent(this,PendingRequest.class);
        startActivity(i);
    }
    public void searchUser(View v){
        Intent i=new Intent(this, SearchUsers.class);
        startActivity(i);
    }

    public void search(View v){
        Intent i=new Intent(this,SearchBook.class);
        startActivity(i);
        }


    public void booklist(View v){
        Intent i=new Intent(this,Booklist.class);
        startActivity(i);
    }

    public void wishlist(View v){
        Intent i=new Intent(this,Wishlist.class);
        startActivity(i);
        }
    public void friends(View v){
        Intent i=new Intent(this,Friend_Message.class);
        startActivity(i);
    }


}


