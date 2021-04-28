package com.example.bookbuzz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookbuzz.R;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter <WishlistAdapter.WishlistViewHolder> {

    ArrayList<WishlistHelperClass> wishlistLocations;

    public WishlistAdapter ( ArrayList < WishlistHelperClass > wishlistLocations ) {
        this.wishlistLocations = wishlistLocations;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.wish_list_1 ,parent,false);
        WishlistViewHolder wishlistViewHolder = new WishlistViewHolder ( view );
        return wishlistViewHolder;
    }

    @Override
    public void onBindViewHolder ( @NonNull WishlistViewHolder holder , int position ) {

        WishlistHelperClass wishlistHelperClass = wishlistLocations.get ( position );

        holder.image.setImageResource ( wishlistHelperClass.getImage () );
        holder.title.setText ( wishlistHelperClass.getTitle () );


    }

    @Override
    public int getItemCount ( ) {
        return wishlistLocations.size ();
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;

        public WishlistViewHolder ( @NonNull View itemView ) {
            super ( itemView );

            //Hooks
            image = itemView.findViewById ( R.id.wish1 );
            title = itemView.findViewById ( R.id.wish1_title );

        }
    }






}
