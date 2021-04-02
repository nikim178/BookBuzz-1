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
    public Button request;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private String current_user_id;
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
           /*  switch (currentState) {
                    case "not_friends":
                        addFriend();
                        break;
                    case "friends":
                        unFriend();
                        break;
                    case "cancel_req":
                        deleteFriend();
                        break;
                }*/
                /*firebaseFirestore.collection("users").document(current_user_id +"friends"+documentId).
                            get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult().exists())
                            {
                                request.setText("Unfriend");
                                currentState="friends";
                            }
                        }
                    });
                    firebaseFirestore.collection("users").document(current_user_id + "/" +"friend_req" +"/"+documentId).
                            get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(!task.getResult().exists())
                            {
                                request.setText("Request");
                                currentState="not_friends";
                            }

                        }
                    });
                    firebaseFirestore.collection("users").document(current_user_id + "/" +"friend_req"+ "/" +documentId).
                            get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult().exists())
                            {
                                request.setText("Cancel_Request");
                                currentState="cancel_req";
                            }

                        }
                    });*/

            }
        });

        return view;
    }
 /*  @Override
    public void onStart()
    {
        super.onStart();
        firebaseFirestore.collection("users").document(current_user_id +"friends"+documentId).
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists())
                {
                    currentState="friends";
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
                }

            }
        });
    }*/
    //add friends
    public void addFriend()
    {
        Map other=new HashMap();
        other.put("request_type","sent");
        other.put("oid",documentId);
        firebaseFirestore.collection("users").document(current_user_id+"/"
                +"friend_req"+"/"+"user_id").set(other).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Map current=new HashMap();
                    current.put("cid",current_user_id);
                    firebaseFirestore.collection("users").document(documentId+"/"
                            +"friend_req"+"/"+"user_id").set(current).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    +"friend_req"+"/"+"user_id").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    firebaseFirestore.collection("users").document(documentId + "/"
                            + "friend_req" + "/" + "user_id").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            request.setEnabled(true);
                            currentState = "not_friends";

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
  /*  public void unFriend()
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
    //accept
    public void acceptRequest()
    {
        Map map=new HashMap();
        try{
            firebaseFirestore.collection("users").
                    document(current_user_id+"/"+"friends").set(documentId);
            firebaseFirestore.collection("users").
                    document(documentId+"/"+"friends").set(current_user_id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

  /*  private void sendingRequest() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Enter User id");
        dialog.setContentView(R.layout.dialog_add);
        dialog.show();

        EditText edtID = dialog.findViewById(R.id.edtID);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUser = edtID.getText().toString();

                if (TextUtils.isEmpty(idUser)) {
                    edtID.setError("required");
                } else {
                    db.collection("users").whereEqualTo("id", idUser)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                edtID.setError("Id not found");
                            } else {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                    String uidFriend = documentSnapshot.getId();
                                    if (gUid.equals(uidFriend)) {
                                        edtID.setError("wrong ID");
                                    } else {
                                        checkFriendExist(uidFriend);
                                        dialog.cancel();

                                    }

                                }
                            }
                        }

                    });

                }
            }
        });
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
    }*/

    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.wrapper,new userFragment()).addToBackStack(null).commit();
    }



}