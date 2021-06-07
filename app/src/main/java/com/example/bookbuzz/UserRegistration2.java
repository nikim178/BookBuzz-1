package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private TextInputEditText uEmail,uPass,uZipcode,uName,uLocation;
    private FloatingActionButton uSignUpButton;
    private Button oldUser;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    private String userID;
    private ImageView logoimage;
    private TextView welcome;
    private TextView signText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration2);
        welcome=findViewById(R.id.logoTexts);
        signText=findViewById(R.id.random);
        logoimage=findViewById(R.id.logoimages);
        uEmail=findViewById(R.id.email);
        uPass=findViewById(R.id.password);
        uZipcode=findViewById(R.id.zipcode);
        uName=findViewById(R.id.username);
        uLocation=findViewById(R.id.location);
        uSignUpButton=findViewById(R.id.signUp);
        oldUser=findViewById(R.id.signInUser);
        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        oldUser.setOnClickListener((v)-> {
            Intent intent=new Intent(UserRegistration2.this,UserLogin2.class);
            Pair[] pairs=new Pair[7];
            pairs[0]=new Pair<View,String>(logoimage,"logo_image");
            pairs[1]=new Pair<View,String>(welcome,"logo_text");
            pairs[2]=new Pair<View,String>(uEmail,"user_trans");
            pairs[3]=new Pair<View,String>(uPass,"pass_trans");
            pairs[4]=new Pair<View,String>(signText,"sign_trans");
            pairs[5]=new Pair<View,String>(uSignUpButton,"float_text");
            pairs[6]=new Pair<View,String>(oldUser,"new_trans");
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(UserRegistration2.this,pairs);
            startActivity(intent,options.toBundle());
        });

        uSignUpButton.setOnClickListener((v)-> {

            createUser();
        });
    }
    private void createUser()
    {
        String email=uEmail.getText().toString();
        String password=uPass.getText().toString();
        String name=uName.getText().toString();
        String zipCode=uZipcode.getText().toString();
        String location=uLocation.getText().toString();

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
                                user.put("userLocation",location);
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