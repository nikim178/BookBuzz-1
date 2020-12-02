package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Profile extends AppCompatActivity {
    private ImageButton eprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        eprofile = (ImageButton) findViewById(R.id.imageButton);
        eprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }

            private void openEditProfile() {
                Intent intent = new Intent(Profile.this,EditProfile.class);
                startActivity(intent);
            }
        });
    }


}