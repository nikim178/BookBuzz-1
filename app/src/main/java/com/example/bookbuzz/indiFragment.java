package com.example.bookbuzz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


public class indiFragment extends Fragment  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String name, location, zipcode, email, imageURL, documentId;
    //
    public Button request;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private String current_user_id;
    private String current_user_name;
    private String currentState;

    public indiFragment() {

    }
    public indiFragment(String documentId, String name, String location, String zipcode, String email, String imageURL) {
        this.documentId=documentId;
        this.name=name;
        this.location=location;
        this.zipcode=zipcode;
        this.email=email;
        this.imageURL=imageURL;

    }
    public static indiFragment newInstance(String param1, String param2) {
        indiFragment fragment = new indiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_indiragment, container, false);
        ImageView imageholder= view.findViewById(R.id.imagegholder);
        TextView nameholder= view.findViewById(R.id.name);
        TextView locationholder= view.findViewById(R.id.location);
        TextView zipcodeholder= view.findViewById(R.id.zipcode);
        TextView emailholder= view.findViewById(R.id.email);
        nameholder.setText(name);
        locationholder.setText(location);
        zipcodeholder.setText(zipcode);
        emailholder.setText(email);
        Glide.with(getContext()).load(imageURL).into(imageholder);
       Button booklist=(Button) view.findViewById(R.id.booklist);
       Button wishlist=(Button) view.findViewById(R.id.wishlist);
       //
        request= view.findViewById(R.id.request);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        current_user_id=mAuth.getCurrentUser().getUid();
        currentState="not_friends";
        //
     /*   firebaseFirestore.collection("users").document(current_user_id +"friends"+documentId).
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists())
                {
                    currentState="friends";
                    request.setText("Unfriend");
                }
            }
        });
        // check if req id is pending
        firebaseFirestore.collection("users").document(current_user_id + "/" +"friend_req"+ "/" +documentId).
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists())
                {
                    currentState="cancel_req";
                    request.setText("Cancel Request");
                }

            }
        });
        //detect if they are friends
        firebaseFirestore.collection("users").document(current_user_id + "/" +"friend_req" +"/"+documentId).
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(!task.getResult().exists())
                {
                    currentState="not_friends";
                    request.setText("Request");
                }

            }
        });
        //*/
       booklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper,new userbooklist(documentId)).addToBackStack(null).commit();

            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper,new userwishlist(documentId)).addToBackStack(null).commit();

            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setEnabled(false);
                if(currentState.equals("not_friends")) {
                    addFriend();
                    request.setText("Cancel Request");
                }
                if(currentState.equals("req_sent")) {
                    deleteFriend();
                    request.setText("Request");
                }
            }
        });

        return view;
    }
    public void addFriend()
    {
       DocumentReference documentReference= firebaseFirestore.collection("users").document(current_user_id)
               .collection("friend_req").document(documentId);
        Map other=new HashMap();
        other.put("request_type","sent");
        documentReference.set(other).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    DocumentReference documentReference1= firebaseFirestore.collection("users").document(documentId)
                            .collection("friend_req").document(current_user_id);

                    Map current=new HashMap();
                   current.put("request_type","received");
                   current.put("name",current_user_name);
                   documentReference1.set(current).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            request.setEnabled(true);
                           currentState="req_sent";
                            Toast.makeText(getActivity(),"request Sent succesfully!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(),"request unsuccesfully!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //delete friend req
    public void deleteFriend()
    {
        try{
            firebaseFirestore.collection("users").document(current_user_id+"/"
                    +"friend_req"+"/"+documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    firebaseFirestore.collection("users").document(documentId + "/"
                            + "friend_req" + "/" + current_user_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            request.setEnabled(true);
                            currentState = "not_friends";
                            Toast.makeText(getActivity(),"request cacel succesfully!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //unfriend
   public void unFriend()
    {
        try{
            firebaseFirestore.collection("users").document(current_user_id+"/"
                    +"friends"+"/"+documentId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    firebaseFirestore.collection("users").document(documentId+"/"
                            +"friends"+"/"+current_user_id).delete();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.wrapper,new userFragment()).addToBackStack(null).commit();
    }



}