package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookbuzz.EditProfile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookbuzz.model.ChatActivity;
import com.example.bookbuzz.model.Friend;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PendingRequest extends AppCompatActivity {
    private RecyclerView rvFriend;
    private LinearLayoutManager mLayoutManager;
    private FirestoreRecyclerAdapter<Friend, FriendViewHolder> adapter;
    private String gUid;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private SparseBooleanArray hideButtons = new SparseBooleanArray();
    private ArrayList<Friend> friendArrayList;
    private String current_user_name;
    private String current_user_image;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String uidFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);
        rvFriend = findViewById(R.id.rvFriend);
        mLayoutManager = new LinearLayoutManager(this);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        gUid = currentUser.getUid();
      /* DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvFriend.getContext(), mLayoutManager.getOrientation());
       rvFriend.addItemDecoration(dividerItemDecoration);*/
       rvFriend.setHasFixedSize(true);
       rvFriend.setLayoutManager(mLayoutManager);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        Query query=db.collection("users").document(gUid).collection("friend_req"+"/")
                .whereEqualTo("request_type","received");
        FirestoreRecyclerOptions<Friend> options = new FirestoreRecyclerOptions.Builder<Friend>()
                .setQuery(query,Friend.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Friend, FriendViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendViewHolder holder, int position, @NonNull Friend model) {
                uidFriend= getSnapshots().getSnapshot(position).getId();
                model.setDocumentId(uidFriend);
                String name= model.getName();
                String profile= model.getProfile();
                holder.txtName.setText(model.getName());
                Glide.with(holder.imageProfile.getContext()).load(model.getProfile()).into(holder.imageProfile);

                holder.accept
                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                acceptRequest(model.getDocumentId(),name,profile);
                                            }
                                        });
                holder.decline
                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                declineRequest(model.getDocumentId());
                                            }
                                        });

            }
            @NonNull
            @Override
            public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list2, parent,false);
                return new FriendViewHolder(view);
            }

        };
        rvFriend.setAdapter(adapter);
    }
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
    public void acceptRequest(String uid, String name, String profile){
        String uidFriend=uid;
        String uname=name;
        String uprofile=profile;
        fStore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        String uname = documentSnapshot.get("userName", String.class);
                        String uprofile= documentSnapshot.get("userProfileURI",String.class);
                        current_user_name=uname;
                        current_user_image=uprofile;

                    }
                }
            }
        });
    DocumentReference documentReference=db.collection("users").document(gUid)
            .collection("friends").document();
    String userAccepted=documentReference.getId();
    Map currentId=new HashMap();
                   currentId.put("idChatRoom",gUid+uidFriend);
                   currentId.put("name",uname);
                   currentId.put("profile",uprofile);
                   documentReference.set(currentId).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            DocumentReference documentReference1=db.collection("users").document(uidFriend)
                    .collection("friends").document(gUid);
            Map otherId=new HashMap();
            otherId.put("idChatRoom",gUid+uidFriend);
            otherId.put("name",current_user_name);
            otherId.put("profile",current_user_image);

            documentReference1.set(otherId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    DocumentReference documentReference2=db.collection("users").document(gUid).collection("friend_req").document(uidFriend);

                    documentReference2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference documentReference3= db.collection("users").document(uidFriend).collection("friend_req").document(gUid);
                            documentReference3.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(PendingRequest.this,"request accept successfully",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            });
        }
    });
    }
   public void declineRequest(String uid){
        String uidFriend= uid;
       DocumentReference documentReference2=db.collection("users").document(gUid).collection("friend_req").document(uidFriend);

       documentReference2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               DocumentReference documentReference3= db.collection("users").document(uidFriend).collection("friend_req").document(gUid);
               documentReference3.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(PendingRequest.this,"request decilned successfully",Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });
   }

    public class FriendViewHolder extends RecyclerView.ViewHolder  {
        TextView accept;
        View mView;
        TextView decline;
        ImageView imageProfile;
        TextView txtName;
        public FriendViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            imageProfile= itemView.findViewById(R.id.img1);
            txtName = itemView.findViewById(R.id.txtName);
            accept = itemView.findViewById(R.id.accept_request);
            decline=itemView.findViewById(R.id.decline_request);

        }
    }
}