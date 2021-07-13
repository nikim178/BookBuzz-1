package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookbuzz.adapter.CardStackAdapter;
import com.example.bookbuzz.models.Book;
import com.example.bookbuzz.models.BookItem;
import com.example.bookbuzz.models.Item;
import com.example.bookbuzz.models.ItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwipeGenre extends AppCompatActivity {
    private static final String TAG = "SwipeGenre";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    ArrayList<String> storeResult;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swipe_genre);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d("one", "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
                position= manager.getTopPosition();
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d("one", "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    if (direction == Direction.Right){
                        List<ItemModel> books= adapter.getItems();
                        ItemModel book=books.get(position);
                        Item b= new Item();
                        b.title= book.getName();
                        b.imageLink=book.getImage();
                        addToWishlist(b);

                    }

                    Toast.makeText(SwipeGenre.this, "Added to Whishlist", Toast.LENGTH_SHORT).show();

                }
                if (direction == Direction.Left){
                    Toast.makeText(SwipeGenre.this, "Removed", Toast.LENGTH_SHORT).show();
                }


                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }
    private void addToWishlist(Item b) {
        FirebaseAuth mAuth;
        FirebaseFirestore fstore;
        FirebaseUser user;
        StorageReference storageReference;
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        Item mBook=b;
        DocumentReference docReference = fstore.collection("users").document(user.getUid()).collection("wishlist").document();
        Map<String, Object> wish = new HashMap<>();
        wish.put("BookTitle", mBook.title);
        wish.put("image",mBook.imageLink);
        docReference.set(wish).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SwipeGenre.this, "added book", Toast.LENGTH_SHORT).show();
                Log.d("one", "done2 ");
            }
        });
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        storeResult = (ArrayList<String>) getIntent().getSerializableExtra("book");

        // Any one with nonfiction
       if(storeResult.contains("NonFiction") && storeResult.contains("Romance"))
        {
            items.add(new ItemModel("https://scholarlykitchen.sspnet.org/wp-content/uploads/2016/06/c4499-sapiens.jpg", "Sapiens"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a3/BriefHistoryTime.jpg", "A Brief History Of Time"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d0/Caged_Bird_cover.jpg", "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/46/Into_Thin_Air.jpg", "Into Thin Air"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/4e/Bad_Blood_%28John_Carreyrou%29.png", "Bad Blood"));
            items.add(new ItemModel("http://4.bp.blogspot.com/--rgW7v0x7-E/UYIZEGzeHRI/AAAAAAAABow/cLfye-KR4M8/s1600/diary.jpg", "The Diary Of Young Girl"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1357174224l/17204679.jpg", "Man's Search For Meaning"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d9/The_Notebook_Cover.jpg", "The Notebook"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a9/The_Fault_in_Our_Stars.jpg", "The Fault in Our Stars"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/6/6b/Gone_with_the_Wind_cover.jpg", "Gone with the Wind"));
            items.add(new ItemModel("https://img.wattpad.com/cover/202700371-352-k527170.jpg", "Beautiful Disaster"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/0/0b/Outlander-1991_1st_Edition_cover.jpg", "Outlander"));
            items.add(new ItemModel("https://www.juggernaut.in/image/filters:format(webp):quality(60)/book_covers/aa314a7748584007b1867976a7488729.jpg", "Jane Eyre"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1600456282l/45897568._SY475_.jpg", "A Long Petal of the Sea"));
            return items;
        }
       else if(storeResult.contains("NonFiction") && storeResult.contains("Fantasy"))
        {
            items.add(new ItemModel("https://scholarlykitchen.sspnet.org/wp-content/uploads/2016/06/c4499-sapiens.jpg", "Sapiens"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a3/BriefHistoryTime.jpg", "A Brief History Of Time"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d0/Caged_Bird_cover.jpg", "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/46/Into_Thin_Air.jpg", "Into Thin Air"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/4e/Bad_Blood_%28John_Carreyrou%29.png", "Bad Blood"));
            items.add(new ItemModel("http://4.bp.blogspot.com/--rgW7v0x7-E/UYIZEGzeHRI/AAAAAAAABow/cLfye-KR4M8/s1600/diary.jpg", "The Diary Of Young Girl"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1357174224l/17204679.jpg", "Man's Search For Meaning"));
           items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/b/b6/Harry_Potter_and_the_Goblet_of_Fire_cover.png", "Harry Potter"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Hobbit_cover.JPG/170px-Hobbit_cover.JPG", "The Hobbit"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/9/93/AGameOfThrones.jpg", "A Game of Thrones"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/1/1f/The_Palace_of_Illusions.jpg", "The Palace Of Illusions"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/fd/Malgudi_Days.jpg", "Malgudi Day's"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ibuulJlYL._SX361_BO1,204,203,200_.jpg", "Alice's Adventures in Wonderland"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/912-IVfwS-L.jpg", "The Time Machine"));
            return items;
        }
        else if(storeResult.contains("NonFiction") && storeResult.contains("SelfHelp"))
        {
           items.add(new ItemModel("https://scholarlykitchen.sspnet.org/wp-content/uploads/2016/06/c4499-sapiens.jpg", "Sapiens"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a3/BriefHistoryTime.jpg", "A Brief History Of Time"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d0/Caged_Bird_cover.jpg", "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/46/Into_Thin_Air.jpg", "Into Thin Air"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/4e/Bad_Blood_%28John_Carreyrou%29.png", "Bad Blood"));
            items.add(new ItemModel("http://4.bp.blogspot.com/--rgW7v0x7-E/UYIZEGzeHRI/AAAAAAAABow/cLfye-KR4M8/s1600/diary.jpg", "The Diary Of Young Girl"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1357174224l/17204679.jpg", "Man's Search For Meaning"));
           items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a2/The_7_Habits_of_Highly_Effective_People.jpg", "The 7 Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51X7dEUFgoL._AC_SY400_.jpg", "How to Win Friends"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71UypkUjStL.jpg", "Think and Grow Rich"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71ql4xBmpAL.jpg", "The 4-Hour Work Week"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51FGx8kUv-L._SX258_BO1,204,203,200_.jpg", "The Secret"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91VokXkn8hL.jpg", "Rich Dad Poor Dad"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91pR9wKJ3zL.jpg", "Atomic Habits"));
            return items;
        }
        else if(storeResult.contains("NonFiction") && storeResult.contains("Detective"))
        {
            items.add(new ItemModel("https://scholarlykitchen.sspnet.org/wp-content/uploads/2016/06/c4499-sapiens.jpg", "Sapiens"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a3/BriefHistoryTime.jpg", "A Brief History Of Time"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d0/Caged_Bird_cover.jpg", "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/46/Into_Thin_Air.jpg", "Into Thin Air"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/4e/Bad_Blood_%28John_Carreyrou%29.png", "Bad Blood"));
            items.add(new ItemModel("http://4.bp.blogspot.com/--rgW7v0x7-E/UYIZEGzeHRI/AAAAAAAABow/cLfye-KR4M8/s1600/diary.jpg", "The Diary Of Young Girl"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1357174224l/17204679.jpg", "Man's Search For Meaning"));
           items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/61WLupnyuML.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71+WebeovJL.jpg", "Sherlock Holmes"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81UhKOBDrCL.jpg", "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81ZcuwQXSvL.jpg", "The murder of Roger Ackroyd"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51N74pdzjFL.jpg", "The Murders in the Rue Morgue"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1580194251l/51933429.jpg", "The Guest List"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51kjk79ygyL.jpg", "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("NonFiction") && storeResult.contains("Comic"))
        {
            items.add(new ItemModel("https://scholarlykitchen.sspnet.org/wp-content/uploads/2016/06/c4499-sapiens.jpg", "Sapiens"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a3/BriefHistoryTime.jpg", "A Brief History Of Time"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d0/Caged_Bird_cover.jpg", "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/46/Into_Thin_Air.jpg", "Into Thin Air"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/4e/Bad_Blood_%28John_Carreyrou%29.png", "Bad Blood"));
            items.add(new ItemModel("http://4.bp.blogspot.com/--rgW7v0x7-E/UYIZEGzeHRI/AAAAAAAABow/cLfye-KR4M8/s1600/diary.jpg", "The Diary Of Young Girl"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1357174224l/17204679.jpg", "Man's Search For Meaning"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51UA4cTPAhL._SX321_BO1,204,203,200_.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ZkifaRuVL._SX334_BO1,204,203,200_.jpg", "Nimona"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A1qOMoBTIUL.jpg", "Justic League"));
            items.add(new ItemModel("https://i.pinimg.com/736x/f0/33/38/f03338d562c9df31d14fcdd67024bf50.jpg", "Batman"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81VGoukzRZL.jpg", "Daytripper"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81N2JTJhzFL.jpg", "Once & Future"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A12mMQqXVjL.jpg", "The Sherif Of Babylon"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/f4/Ironman001.jpg", "Iron Man"));
            return items;
        }
        //Any one with Romance
        else if(storeResult.contains("Romance") && storeResult.contains("Fantasy")){
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d9/The_Notebook_Cover.jpg", "The Notebook"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a9/The_Fault_in_Our_Stars.jpg", "The Fault in Our Stars"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/6/6b/Gone_with_the_Wind_cover.jpg", "Gone with the Wind"));
            items.add(new ItemModel("https://img.wattpad.com/cover/202700371-352-k527170.jpg", "Beautiful Disaster"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/0/0b/Outlander-1991_1st_Edition_cover.jpg", "Outlander"));
            items.add(new ItemModel("https://www.juggernaut.in/image/filters:format(webp):quality(60)/book_covers/aa314a7748584007b1867976a7488729.jpg", "Jane Eyre"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1600456282l/45897568._SY475_.jpg", "A Long Petal of the Sea"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/b/b6/Harry_Potter_and_the_Goblet_of_Fire_cover.png", "Harry Potter"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Hobbit_cover.JPG/170px-Hobbit_cover.JPG", "The Hobbit"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/9/93/AGameOfThrones.jpg", "A Game of Thrones"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/1/1f/The_Palace_of_Illusions.jpg", "The Palace Of Illusions"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/fd/Malgudi_Days.jpg", "Malgudi Day's"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ibuulJlYL._SX361_BO1,204,203,200_.jpg", "Alice's Adventures in Wonderland"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/912-IVfwS-L.jpg", "The Time Machine"));
            return items;
        }
        else if(storeResult.contains("Romance") && storeResult.contains("SelfHelp")){
           items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d9/The_Notebook_Cover.jpg", "The Notebook"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a9/The_Fault_in_Our_Stars.jpg", "The Fault in Our Stars"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/6/6b/Gone_with_the_Wind_cover.jpg", "Gone with the Wind"));
            items.add(new ItemModel("https://img.wattpad.com/cover/202700371-352-k527170.jpg", "Beautiful Disaster"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/0/0b/Outlander-1991_1st_Edition_cover.jpg", "Outlander"));
            items.add(new ItemModel("https://www.juggernaut.in/image/filters:format(webp):quality(60)/book_covers/aa314a7748584007b1867976a7488729.jpg", "Jane Eyre"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1600456282l/45897568._SY475_.jpg", "A Long Petal of the Sea"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a2/The_7_Habits_of_Highly_Effective_People.jpg", "The 7 Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51X7dEUFgoL._AC_SY400_.jpg", "How to Win Friends"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71UypkUjStL.jpg", "Think and Grow Rich"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71ql4xBmpAL.jpg", "The 4-Hour Work Week"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51FGx8kUv-L._SX258_BO1,204,203,200_.jpg", "The Secret"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91VokXkn8hL.jpg", "Rich Dad Poor Dad"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91pR9wKJ3zL.jpg", "Atomic Habits"));
            return items;
        }
        else if(storeResult.contains("Romance") && storeResult.contains("Detective")){
          items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d9/The_Notebook_Cover.jpg", "The Notebook"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a9/The_Fault_in_Our_Stars.jpg", "The Fault in Our Stars"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/6/6b/Gone_with_the_Wind_cover.jpg", "Gone with the Wind"));
            items.add(new ItemModel("https://img.wattpad.com/cover/202700371-352-k527170.jpg", "Beautiful Disaster"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/0/0b/Outlander-1991_1st_Edition_cover.jpg", "Outlander"));
            items.add(new ItemModel("https://www.juggernaut.in/image/filters:format(webp):quality(60)/book_covers/aa314a7748584007b1867976a7488729.jpg", "Jane Eyre"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1600456282l/45897568._SY475_.jpg", "A Long Petal of the Sea"));
           items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/61WLupnyuML.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71+WebeovJL.jpg", "Sherlock Holmes"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81UhKOBDrCL.jpg", "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81ZcuwQXSvL.jpg", "The murder of Roger Ackroyd"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51N74pdzjFL.jpg", "The Murders in the Rue Morgue"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1580194251l/51933429.jpg", "The Guest List"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51kjk79ygyL.jpg", "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("Romance") && storeResult.contains("Comic")){
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d9/The_Notebook_Cover.jpg", "The Notebook"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a9/The_Fault_in_Our_Stars.jpg", "The Fault in Our Stars"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/6/6b/Gone_with_the_Wind_cover.jpg", "Gone with the Wind"));
            items.add(new ItemModel("https://img.wattpad.com/cover/202700371-352-k527170.jpg", "Beautiful Disaster"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/0/0b/Outlander-1991_1st_Edition_cover.jpg", "Outlander"));
            items.add(new ItemModel("https://www.juggernaut.in/image/filters:format(webp):quality(60)/book_covers/aa314a7748584007b1867976a7488729.jpg", "Jane Eyre"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1600456282l/45897568._SY475_.jpg", "A Long Petal of the Sea"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51UA4cTPAhL._SX321_BO1,204,203,200_.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ZkifaRuVL._SX334_BO1,204,203,200_.jpg", "Nimona"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A1qOMoBTIUL.jpg", "Justic League"));
            items.add(new ItemModel("https://i.pinimg.com/736x/f0/33/38/f03338d562c9df31d14fcdd67024bf50.jpg", "Batman"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81VGoukzRZL.jpg", "Daytripper"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81N2JTJhzFL.jpg", "Once & Future"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A12mMQqXVjL.jpg", "The Sherif Of Babylon"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/f4/Ironman001.jpg", "Iron Man"));
            return items;

        }
        //Any one with Fantasy
        else if(storeResult.contains("Fantasy") && storeResult.contains("SelfHelp")){
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/b/b6/Harry_Potter_and_the_Goblet_of_Fire_cover.png", "Harry Potter"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Hobbit_cover.JPG/170px-Hobbit_cover.JPG", "The Hobbit"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/9/93/AGameOfThrones.jpg", "A Game of Thrones"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/1/1f/The_Palace_of_Illusions.jpg", "The Palace Of Illusions"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/fd/Malgudi_Days.jpg", "Malgudi Day's"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ibuulJlYL._SX361_BO1,204,203,200_.jpg", "Alice's Adventures in Wonderland"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/912-IVfwS-L.jpg", "The Time Machine"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a2/The_7_Habits_of_Highly_Effective_People.jpg", "The 7 Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51X7dEUFgoL._AC_SY400_.jpg", "How to Win Friends"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71UypkUjStL.jpg", "Think and Grow Rich"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71ql4xBmpAL.jpg", "The 4-Hour Work Week"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51FGx8kUv-L._SX258_BO1,204,203,200_.jpg", "The Secret"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91VokXkn8hL.jpg", "Rich Dad Poor Dad"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91pR9wKJ3zL.jpg", "Atomic Habits"));
            return items;

        }
        else if(storeResult.contains("Fantasy") && storeResult.contains("Detective")){
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/b/b6/Harry_Potter_and_the_Goblet_of_Fire_cover.png", "Harry Potter"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Hobbit_cover.JPG/170px-Hobbit_cover.JPG", "The Hobbit"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/9/93/AGameOfThrones.jpg", "A Game of Thrones"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/1/1f/The_Palace_of_Illusions.jpg", "The Palace Of Illusions"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/fd/Malgudi_Days.jpg", "Malgudi Day's"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ibuulJlYL._SX361_BO1,204,203,200_.jpg", "Alice's Adventures in Wonderland"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/912-IVfwS-L.jpg", "The Time Machine"));
           items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/61WLupnyuML.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71+WebeovJL.jpg", "Sherlock Holmes"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81UhKOBDrCL.jpg", "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81ZcuwQXSvL.jpg", "The murder of Roger Ackroyd"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51N74pdzjFL.jpg", "The Murders in the Rue Morgue"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1580194251l/51933429.jpg", "The Guest List"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51kjk79ygyL.jpg", "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("Fantasy") && storeResult.contains("Comic")){
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/b/b6/Harry_Potter_and_the_Goblet_of_Fire_cover.png", "Harry Potter"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Hobbit_cover.JPG/170px-Hobbit_cover.JPG", "The Hobbit"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/9/93/AGameOfThrones.jpg", "A Game of Thrones"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/1/1f/The_Palace_of_Illusions.jpg", "The Palace Of Illusions"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/fd/Malgudi_Days.jpg", "Malgudi Day's"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ibuulJlYL._SX361_BO1,204,203,200_.jpg", "Alice's Adventures in Wonderland"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/912-IVfwS-L.jpg", "The Time Machine"));
           items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51UA4cTPAhL._SX321_BO1,204,203,200_.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ZkifaRuVL._SX334_BO1,204,203,200_.jpg", "Nimona"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A1qOMoBTIUL.jpg", "Justic League"));
            items.add(new ItemModel("https://i.pinimg.com/736x/f0/33/38/f03338d562c9df31d14fcdd67024bf50.jpg", "Batman"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81VGoukzRZL.jpg", "Daytripper"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81N2JTJhzFL.jpg", "Once & Future"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A12mMQqXVjL.jpg", "The Sherif Of Babylon"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/f4/Ironman001.jpg", "Iron Man"));
            return items;
        }
        //Any one with Selphelp
        else if(storeResult.contains("SelfHelp") && storeResult.contains("Detective")){
           items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a2/The_7_Habits_of_Highly_Effective_People.jpg", "The 7 Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51X7dEUFgoL._AC_SY400_.jpg", "How to Win Friends"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71UypkUjStL.jpg", "Think and Grow Rich"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71ql4xBmpAL.jpg", "The 4-Hour Work Week"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51FGx8kUv-L._SX258_BO1,204,203,200_.jpg", "The Secret"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91VokXkn8hL.jpg", "Rich Dad Poor Dad"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91pR9wKJ3zL.jpg", "Atomic Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/61WLupnyuML.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71+WebeovJL.jpg", "Sherlock Holmes"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81UhKOBDrCL.jpg", "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81ZcuwQXSvL.jpg", "The murder of Roger Ackroyd"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51N74pdzjFL.jpg", "The Murders in the Rue Morgue"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1580194251l/51933429.jpg", "The Guest List"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51kjk79ygyL.jpg", "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("SelfHelp") && storeResult.contains("Comic")){
           items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a2/The_7_Habits_of_Highly_Effective_People.jpg", "The 7 Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51X7dEUFgoL._AC_SY400_.jpg", "How to Win Friends"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71UypkUjStL.jpg", "Think and Grow Rich"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71ql4xBmpAL.jpg", "The 4-Hour Work Week"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51FGx8kUv-L._SX258_BO1,204,203,200_.jpg", "The Secret"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91VokXkn8hL.jpg", "Rich Dad Poor Dad"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91pR9wKJ3zL.jpg", "Atomic Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51UA4cTPAhL._SX321_BO1,204,203,200_.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ZkifaRuVL._SX334_BO1,204,203,200_.jpg", "Nimona"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A1qOMoBTIUL.jpg", "Justic League"));
            items.add(new ItemModel("https://i.pinimg.com/736x/f0/33/38/f03338d562c9df31d14fcdd67024bf50.jpg", "Batman"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81VGoukzRZL.jpg", "Daytripper"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81N2JTJhzFL.jpg", "Once & Future"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A12mMQqXVjL.jpg", "The Sherif Of Babylon"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/f4/Ironman001.jpg", "Iron Man"));
            return items;
        }
        //Any one with Detective
        else if(storeResult.contains("Detective") && storeResult.contains("Comic")){
           items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/61WLupnyuML.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71+WebeovJL.jpg", "Sherlock Holmes"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81UhKOBDrCL.jpg", "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81ZcuwQXSvL.jpg", "The murder of Roger Ackroyd"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51N74pdzjFL.jpg", "The Murders in the Rue Morgue"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1580194251l/51933429.jpg", "The Guest List"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51kjk79ygyL.jpg", "A Study in Scarlet"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51UA4cTPAhL._SX321_BO1,204,203,200_.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ZkifaRuVL._SX334_BO1,204,203,200_.jpg", "Nimona"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A1qOMoBTIUL.jpg", "Justic League"));
            items.add(new ItemModel("https://i.pinimg.com/736x/f0/33/38/f03338d562c9df31d14fcdd67024bf50.jpg", "Batman"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81VGoukzRZL.jpg", "Daytripper"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81N2JTJhzFL.jpg", "Once & Future"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A12mMQqXVjL.jpg", "The Sherif Of Babylon"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/f4/Ironman001.jpg", "Iron Man"));
            return items;

        }
        //single options
        else if(storeResult.contains("NonFiction")) {
            items.add(new ItemModel("https://scholarlykitchen.sspnet.org/wp-content/uploads/2016/06/c4499-sapiens.jpg", "Sapiens"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a3/BriefHistoryTime.jpg", "A Brief History Of Time"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d0/Caged_Bird_cover.jpg", "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/46/Into_Thin_Air.jpg", "Into Thin Air"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/4e/Bad_Blood_%28John_Carreyrou%29.png", "Bad Blood"));
            items.add(new ItemModel("http://4.bp.blogspot.com/--rgW7v0x7-E/UYIZEGzeHRI/AAAAAAAABow/cLfye-KR4M8/s1600/diary.jpg", "The Diary Of Young Girl"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1357174224l/17204679.jpg", "Man's Search For Meaning"));
            return items;
        }
        else if(storeResult.contains("Romance")) {
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d9/The_Notebook_Cover.jpg", "The Notebook"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a9/The_Fault_in_Our_Stars.jpg", "The Fault in Our Stars"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/6/6b/Gone_with_the_Wind_cover.jpg", "Gone with the Wind"));
            items.add(new ItemModel("https://img.wattpad.com/cover/202700371-352-k527170.jpg", "Beautiful Disaster"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/0/0b/Outlander-1991_1st_Edition_cover.jpg", "Outlander"));
            items.add(new ItemModel("https://www.juggernaut.in/image/filters:format(webp):quality(60)/book_covers/aa314a7748584007b1867976a7488729.jpg", "Jane Eyre"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1600456282l/45897568._SY475_.jpg", "A Long Petal of the Sea"));

            return items;
        }
        else if(storeResult.contains("Fantasy")) {
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/b/b6/Harry_Potter_and_the_Goblet_of_Fire_cover.png", "Harry Potter"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Hobbit_cover.JPG/170px-Hobbit_cover.JPG", "The Hobbit"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/9/93/AGameOfThrones.jpg", "A Game of Thrones"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/1/1f/The_Palace_of_Illusions.jpg", "The Palace Of Illusions"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/fd/Malgudi_Days.jpg", "Malgudi Day's"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ibuulJlYL._SX361_BO1,204,203,200_.jpg", "Alice's Adventures in Wonderland"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/912-IVfwS-L.jpg", "The Time Machine"));
            return items;
        }
        else if(storeResult.contains("SelfHelp")){
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a2/The_7_Habits_of_Highly_Effective_People.jpg", "The 7 Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51X7dEUFgoL._AC_SY400_.jpg", "How to Win Friends"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71UypkUjStL.jpg", "Think and Grow Rich"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71ql4xBmpAL.jpg", "The 4-Hour Work Week"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51FGx8kUv-L._SX258_BO1,204,203,200_.jpg", "The Secret"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91VokXkn8hL.jpg", "Rich Dad Poor Dad"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91pR9wKJ3zL.jpg", "Atomic Habits"));
            return items;
        }
        else if(storeResult.contains("Detective")){
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/61WLupnyuML.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71+WebeovJL.jpg", "Sherlock Holmes"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81UhKOBDrCL.jpg", "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81ZcuwQXSvL.jpg", "The murder of Roger Ackroyd"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51N74pdzjFL.jpg", "The Murders in the Rue Morgue"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1580194251l/51933429.jpg", "The Guest List"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51kjk79ygyL.jpg", "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("Comic")) {
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51UA4cTPAhL._SX321_BO1,204,203,200_.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ZkifaRuVL._SX334_BO1,204,203,200_.jpg", "Nimona"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A1qOMoBTIUL.jpg", "Justic League"));
            items.add(new ItemModel("https://i.pinimg.com/736x/f0/33/38/f03338d562c9df31d14fcdd67024bf50.jpg", "Batman"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81VGoukzRZL.jpg", "Daytripper"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81N2JTJhzFL.jpg", "Once & Future"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A12mMQqXVjL.jpg", "The Sherif Of Babylon"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/f4/Ironman001.jpg", "Iron Man"));
            return items;
        }
        else
        {
            items.add(new ItemModel("https://scholarlykitchen.sspnet.org/wp-content/uploads/2016/06/c4499-sapiens.jpg", "Sapiens"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a3/BriefHistoryTime.jpg", "A Brief History Of Time"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d0/Caged_Bird_cover.jpg", "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/46/Into_Thin_Air.jpg", "Into Thin Air"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/4/4e/Bad_Blood_%28John_Carreyrou%29.png", "Bad Blood"));
            items.add(new ItemModel("http://4.bp.blogspot.com/--rgW7v0x7-E/UYIZEGzeHRI/AAAAAAAABow/cLfye-KR4M8/s1600/diary.jpg", "The Diary Of Young Girl"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1357174224l/17204679.jpg", "Man's Search For Meaning"));
            //
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/d/d9/The_Notebook_Cover.jpg", "The Notebook"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a9/The_Fault_in_Our_Stars.jpg", "The Fault in Our Stars"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/6/6b/Gone_with_the_Wind_cover.jpg", "Gone with the Wind"));
            items.add(new ItemModel("https://img.wattpad.com/cover/202700371-352-k527170.jpg", "Beautiful Disaster"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/0/0b/Outlander-1991_1st_Edition_cover.jpg", "Outlander"));
            items.add(new ItemModel("https://www.juggernaut.in/image/filters:format(webp):quality(60)/book_covers/aa314a7748584007b1867976a7488729.jpg", "Jane Eyre"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1600456282l/45897568._SY475_.jpg", "A Long Petal of the Sea"));
            //
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/b/b6/Harry_Potter_and_the_Goblet_of_Fire_cover.png", "Harry Potter"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Hobbit_cover.JPG/170px-Hobbit_cover.JPG", "The Hobbit"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/9/93/AGameOfThrones.jpg", "A Game of Thrones"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/1/1f/The_Palace_of_Illusions.jpg", "The Palace Of Illusions"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/fd/Malgudi_Days.jpg", "Malgudi Day's"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ibuulJlYL._SX361_BO1,204,203,200_.jpg", "Alice's Adventures in Wonderland"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/912-IVfwS-L.jpg", "The Time Machine"));
            //
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/a/a2/The_7_Habits_of_Highly_Effective_People.jpg", "The 7 Habits"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51X7dEUFgoL._AC_SY400_.jpg", "How to Win Friends"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71UypkUjStL.jpg", "Think and Grow Rich"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71ql4xBmpAL.jpg", "The 4-Hour Work Week"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51FGx8kUv-L._SX258_BO1,204,203,200_.jpg", "The Secret"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91VokXkn8hL.jpg", "Rich Dad Poor Dad"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/91pR9wKJ3zL.jpg", "Atomic Habits"));
            //
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/61WLupnyuML.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/71+WebeovJL.jpg", "Sherlock Holmes"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81UhKOBDrCL.jpg", "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81ZcuwQXSvL.jpg", "The murder of Roger Ackroyd"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51N74pdzjFL.jpg", "The Murders in the Rue Morgue"));
            items.add(new ItemModel("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1580194251l/51933429.jpg", "The Guest List"));
            items.add(new ItemModel("https://m.media-amazon.com/images/I/51kjk79ygyL.jpg", "A Study in Scarlet"));
            //
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51UA4cTPAhL._SX321_BO1,204,203,200_.jpg", "Murder on the Orient Express"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/51ZkifaRuVL._SX334_BO1,204,203,200_.jpg", "Nimona"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A1qOMoBTIUL.jpg", "Justic League"));
            items.add(new ItemModel("https://i.pinimg.com/736x/f0/33/38/f03338d562c9df31d14fcdd67024bf50.jpg", "Batman"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81VGoukzRZL.jpg", "Daytripper"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/81N2JTJhzFL.jpg", "Once & Future"));
            items.add(new ItemModel("https://images-na.ssl-images-amazon.com/images/I/A12mMQqXVjL.jpg", "The Sherif Of Babylon"));
            items.add(new ItemModel("https://upload.wikimedia.org/wikipedia/en/f/f4/Ironman001.jpg", "Iron Man"));
            return items;
        }

    }

}