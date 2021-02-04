package com.example.bookbuzz;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        Button b= view.findViewById(R.id.request);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Home.class);
                startActivity(intent);

            }
        });

        return view;
    }
    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.wrapper,new userFragment()).addToBackStack(null).commit();


    }



}