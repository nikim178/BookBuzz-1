package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookbuzz.model.ChatActivity;
import com.example.bookbuzz.model.Friend;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PendingRequest extends AppCompatActivity {

    private RecyclerView rvFriend;
    private LinearLayoutManager mLayoutManager;
    private FirestoreRecyclerAdapter<Friend, FriendViewHolder> adapter;
    private String gUid;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);
        rvFriend = findViewById(R.id.rvFriend);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        goMain();
    }

    private void goMain() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        gUid = currentUser.getUid();

        rvFriend = findViewById(R.id.rvFriend);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvFriend.getContext(), mLayoutManager.getOrientation());
        rvFriend.addItemDecoration(dividerItemDecoration);
        rvFriend.setHasFixedSize(true);
        rvFriend.setLayoutManager(mLayoutManager);

        FirestoreRecyclerOptions<Friend> options = new FirestoreRecyclerOptions.Builder<Friend>()
                .setQuery(db.collection("users").document(gUid).collection("friend"),Friend.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Friend, FriendViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendViewHolder holder, int position, @NonNull Friend model) {
                String uidFriend = getSnapshots().getSnapshot(position).getId();
                holder.setList(uidFriend);
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goChatRoom(model.getIdChatRoom(),uidFriend);
                    }
                });
            }

            @NonNull
            @Override
            public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list, parent,false);
                return new FriendViewHolder(view);
            }
        };

        rvFriend.setAdapter(adapter);
        adapter.startListening();

           }
    private void goChatRoom(String idChatRoom, String uidFriend) {
        Intent i= new Intent(this, ChatActivity.class);
        i.putExtra("idChatRoom",idChatRoom);
        i.putExtra("uidFriend",uidFriend);
        startActivity(i);
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView imageProfile;
        TextView txtName;


        public FriendViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imageProfile= itemView.findViewById(R.id.imagegholder);
            txtName = mView.findViewById(R.id.txtName);
        }
        public void setList(String uidFriend) {
            db.collection("users").document(uidFriend).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if(documentSnapshot.exists())
                        {
                            String name=documentSnapshot.get("userName",String.class);
                            txtName.setText(name);

                        }

                    }
                }
            });
        }
    }
}