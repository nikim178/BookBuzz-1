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

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {
    private EditText uEmail,uPass;
    private TextView uName,uLocation;
    private TextView uLogin;
    private Button uRegisterButton;
    private FirebaseAuth mAuth;
    private Button uGoogle;
    GoogleSignInClient mGoogleSignInClient;
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
        uLogin=findViewById(R.id.textView);
        uRegisterButton=findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        uGoogle=findViewById(R.id.button4);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        uGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
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
    //code for google sign in
    int RC_SIGN_IN=66;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent intent=new Intent(UserRegistration.this,Startpage.class);
                            startActivity(intent);
                            Toast.makeText(UserRegistration.this,"Sign in with google",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    //code for signin through email&password
    private void createUser()
    {
        String email=uEmail.getText().toString();
        String password=uPass.getText().toString();
        String name=uName.getText().toString();
        String location=uLocation.getText().toString();

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