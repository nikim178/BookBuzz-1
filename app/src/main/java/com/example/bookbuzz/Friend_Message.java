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

import com.bumptech.glide.Glide;
import com.example.bookbuzz.model.ChatActivity;
import com.example.bookbuzz.model.Friend;
import com.example.bookbuzz.model.FriendM;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class Friend_Message extends AppCompatActivity {

    private RecyclerView rvFriend;
    private LinearLayoutManager mLayoutManager;
    private FirestoreRecyclerAdapter<FriendM, FriendMViewHolder> adapter;
    private String gUid;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String uidFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__message);
        rvFriend = findViewById(R.id.Friends);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        gUid = currentUser.getUid();

        rvFriend = findViewById(R.id.Friends);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvFriend.getContext(), mLayoutManager.getOrientation());
        rvFriend.addItemDecoration(dividerItemDecoration);
        rvFriend.setHasFixedSize(true);
        rvFriend.setLayoutManager(mLayoutManager);
        Query query = db.collection("users").document(gUid).collection("friends" + "/");
        FirestoreRecyclerOptions<FriendM> options = new FirestoreRecyclerOptions.Builder<FriendM>()
                .setQuery(query, FriendM.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<FriendM, FriendMViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendMViewHolder holder, int position, @NonNull FriendM model) {
                uidFriend = getSnapshots().getSnapshot(position).getId();
                holder.txtName.setText(model.getName());
                Glide.with(holder.imageProfile.getContext()).load(model.getProfile()).into(holder.imageProfile);
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goChatRoom(model.getIdChatRoom(), uidFriend);
                    }
                });
            }

            @NonNull
            @Override
            public FriendMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_message, parent, false);
                return new FriendMViewHolder(view);
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

    public class FriendMViewHolder extends RecyclerView.ViewHolder {
        View mView;
        CircleImageView imageProfile;
        TextView txtName;


        public FriendMViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imageProfile= itemView.findViewById(R.id.img1);
            txtName = itemView.findViewById(R.id.Nametxt);
        }


    }
}