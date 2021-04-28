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

public class BooklistAdapter extends RecyclerView.Adapter<BooklistAdapter.BooklistViewHolder> {

    ArrayList<BooklistHelperClass> booklistLocations;

    public BooklistAdapter ( ArrayList < BooklistHelperClass > booklistLocations ) {
        this.booklistLocations = booklistLocations;
    }

    @NonNull
    @Override
    public BooklistViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.book_list_1,parent,false );
        BooklistViewHolder booklistViewHolder = new BooklistViewHolder ( view );
        return booklistViewHolder;
    }

    @Override
    public void onBindViewHolder ( @NonNull BooklistViewHolder holder , int position ) {

        BooklistHelperClass booklistHelperClass = booklistLocations.get ( position );

        holder.image.setImageResource ( booklistHelperClass.getImage () );
        holder.title.setText ( booklistHelperClass.getTitle () );

    }

    @Override
    public int getItemCount ( ) {
        return booklistLocations.size ();
    }


    public static class BooklistViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;


        public BooklistViewHolder ( @NonNull View itemView ) {
            super ( itemView );

            //Hooks

            image = itemView.findViewById ( R.id.book1 );
            title = itemView.findViewById ( R.id.book1_title );






        }
    }





}
