package com.example.bookbuzz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;


public class indiFragment extends Fragment  {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name, location, zipcode, email, imageURL;

    public indiFragment() {

    }
    public indiFragment(String name, String location, String zipcode, String email, String imageURL) {
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

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private String gUid;
    private FirebaseFirestore db;
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
        mAuth=FirebaseAuth.getInstance();
        Button b= view.findViewById(R.id.request);
        Button decline=view.findViewById(R.id.declinerequest);
        FirebaseUser currentUser=mAuth.getCurrentUser();
        gUid=currentUser.getUid();
        db=FirebaseFirestore.getInstance();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setTitle("Enter User id");
                dialog.setContentView(R.layout.dialog_add);
                dialog.show();

                EditText edtID=dialog.findViewById(R.id.edtID);
                Button btnOk=dialog.findViewById(R.id.btnOk);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String idUser=edtID.getText().toString();

                        if (TextUtils.isEmpty(idUser)) {
                            edtID.setError("required");
                        } else {
                            db.collection("users").whereEqualTo("id",idUser)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(queryDocumentSnapshots.isEmpty())
                                    {
                                        edtID.setError("Id not found");
                                    }
                                    else
                                    {
                                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                                        {
                                            String uidFriend=documentSnapshot.getId();
                                            if(gUid.equals(uidFriend))
                                            {
                                                    edtID.setError("wrong ID");
                                                } else {
                                                    dialog.cancel();
                                                    checkFriendExist(uidFriend);
                                                }

                                        }
                                    }
                                }
                            });

                        }
                    }
                });

            }
        });

        return view;
    }

    private void checkFriendExist(final String uidFriend) {
        db.collection("users").document(gUid).collection("friend").document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        String idChatRoom = documentSnapshot.get("idChatRoom", String.class);
                        goChatRoom(idChatRoom,uidFriend);
                    } else {
                        createNewChatRoom(uidFriend);
                    }
                }
            }
        });
    }

    private void goChatRoom(String idChatRoom, String uidFriend) {
    }

    private void createNewChatRoom(final String uidFriend) {
        HashMap<String,Object> dataChatRoom = new HashMap<>();
        dataChatRoom.put("dateAdded", FieldValue.serverTimestamp());
        db.collection("chatRoom").document(gUid+uidFriend).set(dataChatRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //write user data
                HashMap<String,Object> dataFriend = new HashMap<>();
                dataFriend.put("idChatRoom",gUid+uidFriend);
                db.collection("users").document(gUid).collection("friend").document(uidFriend).set(dataFriend).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //write on user's friend data
                        HashMap<String,Object> dataUserFriend = new HashMap<>();
                        dataUserFriend.put("idChatRoom",gUid+uidFriend);
                        db.collection("users").document(uidFriend).collection("friend").document(gUid).set(dataUserFriend).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                goChatRoom(gUid+uidFriend,uidFriend);
                            }
                        });
                    }
                });
            }
        });
    }

    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.wrapper,new userFragment()).addToBackStack(null).commit();
    }



}