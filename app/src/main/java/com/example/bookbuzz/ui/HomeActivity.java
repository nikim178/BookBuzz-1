package com.example.bookbuzz.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bookbuzz.Booklist;
import com.example.bookbuzz.R;
import com.example.bookbuzz.SearchBook;
import com.example.bookbuzz.SelectGenre;
import com.example.bookbuzz.UserLogin;
import com.example.bookbuzz.Wishlist;

public class HomeActivity extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home2 );

        //Assign variable
        drawerLayout = findViewById (R.id.drawer_layout);
    }

    public void ClickMenu( View view){
        //open drawer
        openDrawer(drawerLayout);

    }

    private static void openDrawer ( DrawerLayout drawerLayout ) {
    //open drawer layout
        drawerLayout.openDrawer ( GravityCompat.START );
    }

    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer ( DrawerLayout drawerLayout ) {
        //close drawer layout
        if ( drawerLayout.isDrawerOpen ( GravityCompat.START ) ){

            drawerLayout.closeDrawer ( GravityCompat.START );

        }
    }

    public void ClickProfile(View view){
        //recreate activity
        recreate ();
    }

    public void onClick(View view){
        //Redirect activity to Search book
        redirectActivity ( this, SearchBook.class );
    }

    public void genre(View view){
        //Redirect activity to Select genre
        redirectActivity ( this, SelectGenre.class );
    }

    public void booklist(View view){
        //Redirect activity to Book List
        redirectActivity ( this, Booklist.class );
    }

    public void wishlist(View view){
        //Redirect activity to Wish List
        redirectActivity ( this, Wishlist.class );
    }

    public void skip(View view){
        //Close app
        logout(this);
    }

    private static void logout (Activity activity ) {
        //Initialize slert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder ( activity );
        //Set title
        builder.setTitle ( "Logout" );
        //Set message
        builder.setMessage ( "Are you sure you want to logout?" );
        //Positive yes button
        builder.setPositiveButton ( "YES" , new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick ( DialogInterface dialog , int which ) {
                //Finish activity
                activity.finishAffinity ();
                //Exit app
                System.exit ( 0 );
            }
        } ).setNegativeButton ( "NO" , new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick ( DialogInterface dialog , int which ) {
                //Dismiss dialog
                dialog.dismiss ( );
            }
        } );
        //Negative no button
        //Show dialog
        builder.show ();
    }

    private static void redirectActivity ( Activity activity,Class aClass ) {
        //Initialize intent
        Intent intent = new Intent ( activity,aClass );
        //Set flag
        intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
        //Start activity
        activity.startActivity ( intent );

    }

    @Override
    protected void onPause ( ) {
        super.onPause ( );
        //Close drawer
        closeDrawer ( drawerLayout );
    }
}

