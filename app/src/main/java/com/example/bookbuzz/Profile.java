package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    private ImageButton eprofile;
    private TextView name,email,loc,bio;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.TextView6);
        email = findViewById(R.id.TextView5);
        loc = findViewById(R.id.TextView7);
        bio = findViewById(R.id.TextView8);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(fAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
               name.setText("Name: " + userProfile.getuName());
               email.setText("Email: " + userProfile.getuEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        eprofile = (ImageButton) findViewById(R.id.imageButton);
        eprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }

            private void openEditProfile() {
                //Intent intent = new Intent(Profile.this,EditProfile.class);
                //startActivity(intent);
            }
        });
    }
}