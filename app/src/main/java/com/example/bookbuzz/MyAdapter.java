package com.example.bookbuzz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookbuzz.models.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends FirestoreRecyclerAdapter<UserModel,MyAdapter.viewHolder>{

        public MyAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options) {
            super(options);
        }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull UserModel model) {
        String documentID=getSnapshots().getSnapshot(position).getId();
        model.setDocumentId(documentID);
        holder.userName.setText(model.getUserName());
        holder.userLocation.setText(model.getUserLocation());
        holder.userZipcode.setText(model.getUserZipcode());
        holder.userEmail.setText(model.getUserEmail());
        Glide.with(holder.userProfileURI.getContext()).load(model.getUserProfileURI()).into(holder.userProfileURI);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.wrapper,new indiFragment(model.getDocumentId(),model.getUserName(),model.getUserLocation(),model.getUserZipcode(),model.getUserEmail(),model.getUserProfileURI())).addToBackStack(null).commit();

            }
        });

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new MyAdapter.viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView userName;
        private TextView userLocation;
        private TextView userZipcode;
        private TextView userEmail;
        private CircleImageView userProfileURI;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            userName=itemView.findViewById(R.id.name);
            userLocation=itemView.findViewById(R.id.location);
            userZipcode=itemView.findViewById(R.id.zip);
            userEmail=itemView.findViewById(R.id.email);
            userProfileURI= itemView.findViewById(R.id.img1);
        }
    }
    }
