package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bookbuzz.ui.home.HomeFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName ( );
    //Initialize variable
    DrawerLayout drawerLayout;

    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home2 );

        bottomNav = findViewById ( R.id.bottom_nav );

        if ( savedInstanceState == null ) {
            bottomNav.setItemSelected ( R.id.nav_home , true );
            fragmentManager = getSupportFragmentManager ( );
            HomeFragment homeFragment = new HomeFragment ( );
            fragmentManager.beginTransaction ( )
                           .replace ( R.id.fragment_container , homeFragment )
                           .commit ( );
        }


        bottomNav.setOnItemSelectedListener ( new ChipNavigationBar.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected ( int id ) {
                Fragment fragment = null;
                switch ( id ) {
                    case R.id.home:
                        fragment = new HomeFragment ( );
                        break;
                    case R.id.chat:
                        fragment = new ChatFragment ( );
                        break;
                    case R.id.search_user:
                        fragment = new SearchUserFragment ( );
                        break;
                    case R.id.request:
                        fragment = new RequestsFragment ();
                        break;

                }

                if ( fragment != null ) {

                    fragmentManager = getSupportFragmentManager ( );
                    fragmentManager.beginTransaction ( )
                                   .replace ( R.id.fragment_container , fragment )
                                   .commit ( );
                }
                else {

                    Log.e ( TAG , "Error in creating fragment" );
                }
            }
        } );

        //Assign variable
        drawerLayout = findViewById ( R.id.drawer_layout );
    }

    @Override
    public boolean onCreateOptionsMenu ( Menu menu ) {
        getMenuInflater ().inflate ( R.menu.menu_top_right,menu );
        return true;

    }

    @Override
    public boolean onOptionsItemSelected ( @NonNull MenuItem item ) {
       switch ( item.getItemId () )
       {
           case R.id.settings:
               Toast.makeText ( this,"Settings",Toast.LENGTH_SHORT ).show ();
               return true;
           default:
               return super.onOptionsItemSelected ( item );

       }

    }
    public void settings(View view){
        redirectActivity ( this,Settings.class );
    }

    public void ClickMenu ( View view ) {
        //open drawer
        openDrawer ( drawerLayout );

    }

    private static void openDrawer ( DrawerLayout drawerLayout ) {
        //open drawer layout
        drawerLayout.openDrawer ( GravityCompat.START );
    }

    public void ClickLogo ( View view ) {
        //close drawer
        closeDrawer ( drawerLayout );
    }

    private static void closeDrawer ( DrawerLayout drawerLayout ) {
        //close drawer layout
        if ( drawerLayout.isDrawerOpen ( GravityCompat.START ) ) {

            drawerLayout.closeDrawer ( GravityCompat.START );

        }
    }

    public void clickProfile ( View view ) {
        //recreate activity
        //recreate ( );
        redirectActivity ( this , Profile.class );
    }

    public void onClick ( View view ) {
        //Redirect activity to Search book
        redirectActivity ( this , SearchBook.class );
    }

    public void genre ( View view ) {
        //Redirect activity to Select genre
        redirectActivity ( this , SelectGenre.class );
    }

    public void booklist ( View view ) {
        //Redirect activity to Book List
        redirectActivity ( this , Booklist.class );
    }

    public void wishlist ( View view ) {
        //Redirect activity to Wish List
        redirectActivity ( this , Wishlist.class );
    }

    public void help (View view){
        //Redirect activity to about us/ help
        redirectActivity ( this,Help.class );
    }

    public void skip ( View view ) {
        //Close app
        logout ( this );
    }

    private void logout ( Activity activity ) {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder ( activity );
        //Set title
        builder.setTitle ( "Logout" );
        //Set message
        builder.setMessage ( "Are you sure you want to logout?" );
        //Positive yes button
        builder.setPositiveButton ( "YES" , new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick ( DialogInterface dialog , int which ) {
                startActivity ( new Intent ( HomeActivity.this , UserLogin.class ) );
                finish ( );
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
        builder.show ( );
    }

    private static void redirectActivity ( Activity activity , Class aClass ) {
        //Initialize intent
        Intent intent = new Intent ( activity , aClass );
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



























