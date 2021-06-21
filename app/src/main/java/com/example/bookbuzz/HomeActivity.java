package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.bookbuzz.models.BookModel;
import com.example.bookbuzz.ui.Select_Genre2;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = HomeActivity.class.getSimpleName ( );
    private Button uSignOutButton;
    private FirebaseAuth mAuth;
    //Initialize variable
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerAdapter adapter1;
    String userId;
    StorageReference storageReference;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle Toggle;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView name,email;
    CircleImageView profileImage;
    
    RecyclerView book_list,wish_list;
   // RecyclerView.Adapter adapter;
    androidx.appcompat.app.ActionBar actionBar;
    
    //ChipNavigationBar bottomNav;
    //FragmentManager fragmentManager;
    
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        actionBar=getSupportActionBar();
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#62A6BF"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>BookBuzz </font>"));
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home2 );
        drawerLayout = ( DrawerLayout ) findViewById ( R.id.drawer );
        navigationView = ( NavigationView ) findViewById ( R.id.navigationview );
        toolbar = ( Toolbar ) findViewById ( R.id.toolbar );
        getSupportActionBar ( toolbar );
        mAuth = FirebaseAuth.getInstance ( );
        View header=navigationView.getHeaderView(0);
        name = (TextView)header.findViewById(R.id.name);
        email = (TextView)header.findViewById(R.id.email);
        profileImage = (CircleImageView) header.findViewById(R.id.profilepic);
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();

        StorageReference profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid()+ "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
        userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                name.setText(documentSnapshot.getString("userName"));
                email.setText(documentSnapshot.getString("userEmail"));
            }
        });
        //Navigation view
        navigationView.setNavigationItemSelectedListener ( this );
        navigationView.setItemIconTintList ( null );

        Toggle = new ActionBarDrawerToggle ( this , drawerLayout , R.string.open , R.string.close );
        drawerLayout.addDrawerListener ( Toggle );
        Toggle.syncState ( );
        getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( true );

        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Books
        firestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth;
        FirebaseUser user;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId=user.getUid();
        String[] mColors = {"#F4ADC6","#AAC5E2","#FDFD95","#EBB8ED"};
        book_list = findViewById ( R.id.book_list);
        Query query= firestore.collection("users").document(userId).collection("booklist");

        FirestoreRecyclerOptions<BookModel> options= new FirestoreRecyclerOptions.Builder<BookModel>()
                .setQuery(query, BookModel.class)
                .build();
        adapter= new FirestoreRecyclerAdapter<BookModel, BookViewHolder>(options) {
            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_1,parent,false);
                return new HomeActivity.BookViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull BookModel model) {
                holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 4]));
                String bookid= getSnapshots().getSnapshot(position).getId();
                model.setDocumentId(bookid);
                holder.BookTitle.setText(model.getBookTitle());
                //holder.BookAuth.setText(model.getBookAuth());
                Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
            }
        };

        book_list.setHasFixedSize(true);
        book_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false ));
        book_list.setAdapter(adapter);

        wish_list = findViewById ( R.id.wish_listt);
        Query query1= firestore.collection("users").document(userId).collection("wishlist");
        FirestoreRecyclerOptions<BookModel> options1= new FirestoreRecyclerOptions.Builder<BookModel>()
                .setQuery(query1, BookModel.class)
                .build();
        adapter1=new FirestoreRecyclerAdapter<BookModel, WishViewHolder>(options1) {
            @NonNull
            @Override
            public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_1,parent,false);
                return new HomeActivity.WishViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull WishViewHolder holder, int position, @NonNull BookModel model) {
                holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 4]));
                String bookid= getSnapshots().getSnapshot(position).getId();
                model.setDocumentId(bookid);
                holder.BookTitle1.setText(model.getBookTitle());
                //holder.BookAuth.setText(model.getBookAuth());
                Glide.with(holder.image1.getContext()).load(model.getImage()).into(holder.image1);
            }
        };
        Log.d("one","works");
        wish_list.setHasFixedSize(true);
        wish_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false ));
        wish_list.setAdapter(adapter1);
        FloatingActionButton fab1 = findViewById(R.id.floatingActionButton);
        fab1.setOnClickListener(new View.OnClickListener (){

            @Override
            public void onClick(View v) {
             Intent i= new Intent(HomeActivity.this, SearchUsers.class);
             startActivity(i);
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.floatingActionButton2);
        fab2.setOnClickListener(new View.OnClickListener (){

            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeActivity.this, Select_Genre2.class);
                startActivity(i);
            }
        });
        FloatingActionButton fab3 = findViewById(R.id.floatingActionButton3);
        fab3.setOnClickListener(new View.OnClickListener (){

            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeActivity.this, PendingRequest.class);
                startActivity(i);
            }
        });
        FloatingActionButton fab4 = findViewById(R.id.floatingActionButton4);
        fab4.setOnClickListener(new View.OnClickListener (){

            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeActivity.this, Friend_Message.class);
                startActivity(i);
            }
        });



    }
    public void bookList(View view){
        Intent i=new Intent(HomeActivity.this, Booklist.class);
        startActivity(i);
    }
    public void wishList(View view){
        Intent i=new Intent(HomeActivity.this, Wishlist.class);
        startActivity(i);
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

    private class BookViewHolder extends RecyclerView.ViewHolder{
        private TextView BookTitle;
       // private TextView BookAuth;
        private ImageView image;
       // private Button remove;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
           // BookAuth = itemView.findViewById(R.id.BookAuth);
            BookTitle = itemView.findViewById(R.id.book1_title);
            image = itemView.findViewById(R.id.book1);
            //remove=itemView.findViewById(R.id.remove);

        }
    }
    private class WishViewHolder extends RecyclerView.ViewHolder{
        private TextView BookTitle1;
        // private TextView BookAuth;
        private ImageView image1;
        // private Button remove;

        public WishViewHolder(@NonNull View itemView) {
            super(itemView);
            // BookAuth = itemView.findViewById(R.id.BookAuth);
            BookTitle1 = itemView.findViewById(R.id.wish1_title);
            image1 = itemView.findViewById(R.id.wish1);
            //remove=itemView.findViewById(R.id.remove);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter1.stopListening();
    }


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
                redirectActivity ( this , Select_Genre2.class );

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



























