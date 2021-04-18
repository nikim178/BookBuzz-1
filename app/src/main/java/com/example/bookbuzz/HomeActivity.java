package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.bookbuzz.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;




public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = HomeActivity.class.getSimpleName ( );
    private Button uSignOutButton;
    private FirebaseAuth mAuth;
    //Initialize variable
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    Toolbar toolbar;


    //ChipNavigationBar bottomNav;
    //FragmentManager fragmentManager;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home2 );
        drawerLayout = ( DrawerLayout ) findViewById ( R.id.drawer );
        navigationView = ( NavigationView ) findViewById ( R.id.navigationview );
        toolbar = ( Toolbar ) findViewById ( R.id.toolbar );
        getSupportActionBar ( toolbar );
        mAuth = FirebaseAuth.getInstance ( );

        navigationView.setNavigationItemSelectedListener ( this );

        Toggle = new ActionBarDrawerToggle ( this , drawerLayout , R.string.open , R.string.close );
        drawerLayout.addDrawerListener ( Toggle );
        Toggle.syncState ( );
        getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( true );
    }

    private ActionBar getSupportActionBar ( Toolbar toolbar ) {
        return null;
    }






    /* Toolbar toolbar = findViewById ( R.id.toolbar_menu );
       setSupportActionBar ( toolbar );

       drawerLayout = findViewById ( R.id.drawer_layout );
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this , drawerLayout ,
                                                                R.string.navigation_drawer_open ,
                                                              R.string.navigation_drawer_close
       );

       drawerLayout.addDrawerListener ( toggle );
        toggle.syncState ( );
        DrawerLayout drawer = findViewById ( R.id.drawer_layout );
       NavigationView navigationView = findViewById ( R.id.nav_home );

       ----------------------------------------------------------------------
        bottomNav = findViewById ( R.id.bottom_nav );
       mAuth = FirebaseAuth.getInstance ( );

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
               fragment = new RequestsFragment ( );
              break;



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
*/

    @Override
    public boolean onCreateOptionsMenu ( Menu menu ) {
        getMenuInflater ( ).inflate ( R.menu.home2 , menu );
        return true;

    }
    //public void Settings ( View view ) {
    //}

    @Override
    public boolean onOptionsItemSelected ( MenuItem item ) {
        if ( Toggle.onOptionsItemSelected ( item ) ) {
            return true;
        }
        switch ( item.getItemId ( ) ) {
            case R.id.settings:
                Toast.makeText ( this , "Settings" , Toast.LENGTH_SHORT ).show ( );
                redirectActivity ( this , Settings.class );
                return true;
            default:
                return super.onOptionsItemSelected ( item );

        }

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


    /* public void clickProfile ( View view ) {
         //recreate activity
         //recreate ( );
         redirectActivity ( this , Profile.class );
     }

     public void searchBook ( View view ) {
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

     public void aboutUs ( View view ) {
         //Redirect activity to about us/ help
         redirectActivity ( this , Help.class );
     }
*/
     /*public void skip ( View view ) {
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
                mAuth.signOut ( );
                startActivity ( new Intent ( HomeActivity.this , UserLogin2.class ) );
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
*/
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


    private void setSupportActionBar ( Toolbar toolbar ) {
    }


    @Override
    public void onBackPressed ( ) {

        if ( drawerLayout.isDrawerOpen ( GravityCompat.START ) ) {

            drawerLayout.closeDrawer ( GravityCompat.START );

        }
        else {

            super.onBackPressed ( );
        }
    }

    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem item ) {
        int id = item.getItemId ( );

        switch ( id ) {

            case R.id.profile:

                Toast.makeText ( getApplicationContext ( ) , "Profile" , Toast.LENGTH_LONG ).show ( );
                redirectActivity ( this , Profile.class );

                break;

            case R.id.search_book:

                Toast.makeText ( getApplicationContext ( ) , "Search Book" , Toast.LENGTH_LONG ).show ( );
                redirectActivity ( this , SearchBook.class );

                break;

            case R.id.genre:

                Toast.makeText ( getApplicationContext ( ) , "Select Genre" , Toast.LENGTH_LONG ).show ( );
                redirectActivity ( this , SelectGenre.class );

                break;

            case R.id.booklist:

                Toast.makeText ( getApplicationContext ( ) , "Booklist" , Toast.LENGTH_LONG ).show ( );
                redirectActivity ( this , Booklist.class );

                break;

            case R.id.wishlist:

                Toast.makeText ( getApplicationContext ( ) , "Wishlist" , Toast.LENGTH_LONG ).show ( );
                redirectActivity ( this , Wishlist.class );

                break;

            case R.id.about_us:

                Toast.makeText ( getApplicationContext ( ) , "About Us" , Toast.LENGTH_LONG ).show ( );
                redirectActivity ( this , Help.class );

                break;

            case R.id.logout:

                Toast.makeText ( getApplicationContext ( ) , "Logout" , Toast.LENGTH_LONG ).show ( );
                skip ( );
                break;

        }
        drawerLayout.closeDrawer ( GravityCompat.START );

        return true;
    }

             public void skip(){
                //Close app
                logout ( this );
            }

            private void logout( Activity activity ) {
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
                        mAuth.signOut ( );
                        startActivity ( new Intent ( HomeActivity.this , UserLogin2.class ) );
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











}



























