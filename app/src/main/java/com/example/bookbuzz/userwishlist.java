package com.example.bookbuzz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookbuzz.models.BookModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link userwishlist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class userwishlist extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter adapter;
    String documentId;

    public userwishlist() {
        // Required empty public constructor
    }
    public userwishlist(String documentId){
        this.documentId=documentId;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment userwishlist.
     */
    // TODO: Rename and change types and number of parameters
    public static userwishlist newInstance(String param1, String param2) {
        userwishlist fragment = new userwishlist();
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

        View view = inflater.inflate(R.layout.fragment_userbooklist, container, false);
        recview = (RecyclerView) view.findViewById(R.id.list);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore= FirebaseFirestore.getInstance();
        Query query = firestore.collection("users").document(documentId).collection("wishlist");
        //}

        FirestoreRecyclerOptions<BookModel> options= new FirestoreRecyclerOptions.Builder<BookModel>()
                .setQuery(query, BookModel.class)
                .build();
        adapter= new FirestoreRecyclerAdapter<BookModel, userwishlist.viewHolder>(options) {
            @NonNull
            @Override
            public userwishlist.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booklistrow,parent,false);
                return new userwishlist.viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull userwishlist.viewHolder holder, int position, @NonNull BookModel model) {
                holder.BookTitle.setText(model.getBookTitle());
                holder.BookAuth.setText(model.getBookAuth());
                Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);

            }
        };

        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        recview.setAdapter(adapter);
        Log.d("one", "done ");
        return view;

    }
    private class viewHolder extends RecyclerView.ViewHolder{
        private TextView BookTitle;
        private TextView BookAuth;
        private CircleImageView image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            BookAuth = itemView.findViewById(R.id.BookAuth);
            BookTitle = itemView.findViewById(R.id.BookTitle);
            image = itemView.findViewById(R.id.image);

        }
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
