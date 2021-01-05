package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {
    private EditText uEmail,uPass,uZipcode;
    private TextView uName,uLocation;
    private TextView uLogin;
    private Button uRegisterButton;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        uEmail=findViewById(R.id.editTextTextEmailAddress);
        uPass=findViewById(R.id.editTextTextPassword);
        uName=findViewById(R.id.editTextTextPersonName);
        uLocation=findViewById(R.id.editTextTextPersonName3);
        uZipcode=findViewById(R.id.editTextNumberSigned2);
        uLogin=findViewById(R.id.textView);
        uRegisterButton=findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        uLogin.setOnClickListener((v)-> {

            startActivity(new Intent(UserRegistration.this,UserLogin.class));
        });

        uRegisterButton.setOnClickListener((v)-> {

            createUser();
        });
        FirebaseUser firebaseUser=mAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            startActivity(new Intent(UserRegistration.this,Startpage.class));

        }
    }

    //code for signin through email&password
    private void createUser()
    {
        String email=uEmail.getText().toString();
        String password=uPass.getText().toString();
        String name=uName.getText().toString();
        String location=uLocation.getText().toString();
        String zipCode=uZipcode.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            if(!password.isEmpty())
            {
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(UserRegistration.this,"Registered Successfully!!",Toast.LENGTH_SHORT).show();

                                userID=mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fStore.collection("users").document(userID);
                                Map<String,Object> user= new HashMap<>();
                                user.put("userName",name);
                                user.put("userEmail",email);
                                user.put("userLocation",location);
                                user.put("userPassword",password);
                                user.put("userZipcode",zipCode);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onSuccess: user profile is created"+userID);
                                    }
                                });
                                startActivity(new Intent(UserRegistration.this,UserLogin.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserRegistration.this,"Registered unsuccessfully!!",Toast.LENGTH_SHORT).show();
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