package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;
import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    private ImageView iview;
    private EditText uname, uemail, ulocation, ubio;
    private Button update;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Member member;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //private DocumentRefrence refrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        uname = findViewById(R.id.editTextTextPersonName2);
        uemail = findViewById(R.id.editTextTextEmailAddress3);
        ulocation = findViewById(R.id.editTextTextPersonName4);
        ubio = findViewById(R.id.editTextTextPersonName5);
        update = (Button) findViewById(R.id.button7);
        iview = findViewById(R.id.imageView2);

        databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Member");/*{
            @NonNull
            @Override
            public Class<?> getDeclaringClass() {
                return null;
            }

            @NonNull
            @Override
            public String getName() {
                return null;
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public boolean isSynthetic() {
                return false;
            }
        };*/




       /* update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }


            }

            private void validateAndsave() {
                if(TextUtils.isEmpty(uname.getText().toString()))
                {
                    Toast.makeText(EditProfile.this,"Please enter your name",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(uemail.getText().toString()))
                {
                    Toast.makeText(EditProfile.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(ulocation.getText().toString()))
                {
                    Toast.makeText(EditProfile.this, "Please enter your location", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(ubio.getText().toString()))
                {
                    Toast.makeText(EditProfile.this, "Please enter your biography", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String,Object> userMap = new HashMap<>();
                    userMap.put("name",uname.getText().toString());
                    userMap.put("email",uemail.getText().toString());
                    userMap.put("loction",ulocation.getText().toString());
                    userMap.put("biography",ubio.getText().toString());

                    databaseReference.
                }
            }
        });
    }*/
    }
}