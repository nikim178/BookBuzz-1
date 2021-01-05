package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {
    private EditText uEmail,uPass;
    private Button uSignInButton;
    private FirebaseAuth mAuth;
    private TextView uLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        uEmail=findViewById(R.id.editTextTextEmailAddress2);
        uPass=findViewById(R.id.editTextTextPassword2);
        uSignInButton=findViewById(R.id.button2);
        uLogin=findViewById(R.id.textView2);
        mAuth = FirebaseAuth.getInstance();


        uSignInButton.setOnClickListener((v)-> {

            loginUser();
        });

        uLogin.setOnClickListener((v)-> {

            startActivity(new Intent(UserLogin.this,UserRegistration.class));
        });

    }
    private void loginUser() {
        String email = uEmail.getText().toString();
        String password = uPass.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(UserLogin.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserLogin.this, Startpage.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                uPass.setError("Set Password");
            }
        } else if (email.isEmpty()) {
            uEmail.setError("Empty fields not allowed");
        } else {
            uEmail.setError("Please enter correct Email");
        }
    }
}