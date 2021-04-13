package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserRegistration2 extends AppCompatActivity {
    private TextInputEditText uEmail,uPass,uZipcode,uName;
    private FloatingActionButton uSignUpButton;
    private TextView oldUser;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration2);
        uEmail=findViewById(R.id.email);
        uPass=findViewById(R.id.password);
        uZipcode=findViewById(R.id.zipcode);
        uName=findViewById(R.id.username);
        uSignUpButton=findViewById(R.id.signUp);
        oldUser=findViewById(R.id.signInUser);
        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        oldUser.setOnClickListener((v)-> {

            startActivity(new Intent(UserRegistration2.this,UserLogin2.class));
        });

        uSignUpButton.setOnClickListener((v)-> {

            createUser();
        });
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            startActivity(new Intent(UserRegistration2.this,Startpage.class));

        }
    }
    private void createUser()
    {
        String email=uEmail.getText().toString();
        String password=uPass.getText().toString();
        String name=uName.getText().toString();
        String zipCode=uZipcode.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            if(!password.isEmpty())
            {
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(UserRegistration2.this,"Registered Successfully!!",Toast.LENGTH_SHORT).show();

                                userID=mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fStore.collection("users").document(userID);
                                Map<String,Object> user= new HashMap<>();
                                user.put("userName",name);
                                user.put("userEmail",email);
                                user.put("userPassword",password);
                                user.put("userZipcode",zipCode);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onSuccess: user profile is created"+userID);
                                    }
                                });
                                startActivity(new Intent(UserRegistration2.this,UserLogin2.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserRegistration2.this,"Registered unsuccessfully!!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            else
            {
                uPass.setError("Set Password");
            }
        }else if(email.isEmpty())
        {
            uEmail.setError("Empty fields not allowed");
        }
        else
        {
            uEmail.setError("Please enter correct Email");
        }

    }

}