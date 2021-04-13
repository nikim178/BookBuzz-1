package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin2 extends AppCompatActivity {
    private ImageView logoImage;
    private TextView welcome,signText;
    private TextInputEditText uEmail,uPass;
    private FirebaseAuth mAuth;
    private FloatingActionButton uSignInButton;
    private Button newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login2);
        logoImage=findViewById(R.id.logoimage);
        welcome=findViewById(R.id.logo_name);
        signText=findViewById(R.id.signText);
        uEmail=findViewById(R.id.username);
        uPass=findViewById(R.id.password);
        uSignInButton=findViewById(R.id.signIn);
        newUser=findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        uSignInButton.setOnClickListener((v)-> {

            loginUser();
        });

        newUser.setOnClickListener((v)-> {
            Intent intent=new Intent(UserLogin2.this,UserRegistration2.class);
            Pair[] pairs=new Pair[7];
            pairs[0]=new Pair<View,String>(logoImage,"logo_image");
            pairs[1]=new Pair<View,String>(welcome,"logo_text");
            pairs[2]=new Pair<View,String>(uEmail,"user_trans");
            pairs[3]=new Pair<View,String>(uPass,"pass_trans");
            pairs[4]=new Pair<View,String>(signText,"sign_trans");
            pairs[5]=new Pair<View,String>(uSignInButton,"float_text");
            pairs[6]=new Pair<View,String>(newUser,"new_trans");
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(UserLogin2.this,pairs);
            startActivity(intent,options.toBundle());

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
                                Toast.makeText(UserLogin2.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserLogin2.this, Startpage.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserLogin2.this, "Login Failed", Toast.LENGTH_SHORT).show();
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