package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookbuzz.models.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchUser extends AppCompatActivity {

    private RecyclerView list;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        list=findViewById(R.id.list);
        firestore=FirebaseFirestore.getInstance();
        Query query= firestore.collection("users");
        FirestoreRecyclerOptions<UserModel> options= new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query,UserModel.class)
                .build();
        adapter= new FirestoreRecyclerAdapter<UserModel, viewHolder>(options) {
            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
                return new viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull UserModel model) {
                holder.userName.setText(model.getUserName());
                holder.userLocation.setText(model.getUserLocation().toLowerCase());
                holder.userEmail.setText(model.getUserEmail());


            }
        };
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        EditText searchbox= findViewById(R.id.editTextTextPersonName);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Query query;
                Log.d("one","searchbox changed to"+s.toString());
                if(s.toString().isEmpty()){
                    query= firestore.collection("users");

                }
                else {
                    query = firestore.collection("users")
                            .whereEqualTo("userLocation", s.toString().toUpperCase());
                }
                FirestoreRecyclerOptions<UserModel> options= new FirestoreRecyclerOptions.Builder<UserModel>()
                        .setQuery(query,UserModel.class)
                        .build();
                adapter.updateOptions(options);


            }
        });




    }

    private class viewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView userLocation;
        private TextView userEmail;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.name);
            userLocation=itemView.findViewById(R.id.location);
            userEmail=itemView.findViewById(R.id.email);
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