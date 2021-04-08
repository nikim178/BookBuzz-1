package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookbuzz.models.BookItem;
import com.example.bookbuzz.models.BookModel;
import com.example.bookbuzz.models.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Booklist extends AppCompatActivity {

    private RecyclerView list;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter adapter;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);
        list=findViewById(R.id.list);
        firestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth;
        FirebaseUser user;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId=user.getUid();
        Query query= firestore.collection("users").document(userId).collection("booklist");

        FirestoreRecyclerOptions<BookModel> options= new FirestoreRecyclerOptions.Builder<BookModel>()
                .setQuery(query, BookModel.class)
                .build();
        adapter= new FirestoreRecyclerAdapter<BookModel, viewHolder>(options) {
            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listsrows,parent,false);
                return new Booklist.viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull BookModel model) {
                String bookid= getSnapshots().getSnapshot(position).getId();
                model.setDocumentId(bookid);
                holder.BookTitle.setText(model.getBookTitle());
                holder.BookAuth.setText(model.getBookAuth());
                Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       removeBook(model.getDocumentId(),model.getBookTitle());
                    }
                });

            }
        };

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        Log.d("one", "done ");

    }

    private void removeBook(String documentId, String booktitle) {
        String BookId=documentId;
        String bookTitle=booktitle.toLowerCase();
        DocumentReference documentReference=firestore.collection("users").document(userId).collection("booklist").document(BookId);
        DocumentReference documentReference1=firestore.collection("users").document(userId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                documentReference1.update("Books", FieldValue.arrayRemove(bookTitle)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Booklist.this,"Book Removed Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private class viewHolder extends RecyclerView.ViewHolder{
        private TextView BookTitle;
        private TextView BookAuth;
        private CircleImageView image;
        private Button remove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            BookAuth = itemView.findViewById(R.id.BookAuth);
            BookTitle = itemView.findViewById(R.id.BookTitle);
            image = itemView.findViewById(R.id.image);
            remove=itemView.findViewById(R.id.remove);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}