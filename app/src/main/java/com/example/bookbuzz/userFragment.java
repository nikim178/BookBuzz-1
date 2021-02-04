package com.example.bookbuzz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bookbuzz.models.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;


public class userFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore firestore;


    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    MyAdapter adapter;

    public userFragment() {
    }


    public static userFragment newInstance(String param1, String param2) {
        userFragment fragment = new userFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recview = (RecyclerView) view.findViewById(R.id.list);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("users");
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class)
                .build();
        adapter=new MyAdapter(options);
        recview.setAdapter(adapter);
        //for search
        EditText searchbox= view.findViewById(R.id.editTextTextPersonName);
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
                            .whereEqualTo("userZipcode", s.toString());
                }
                FirestoreRecyclerOptions<UserModel> options= new FirestoreRecyclerOptions.Builder<UserModel>()
                        .setQuery(query,UserModel.class)
                        .build();
                adapter.updateOptions(options);


            }
        });//till here



        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
