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
import com.example.bookbuzz.models.ItemModel;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class SwipeGenre extends AppCompatActivity {
    private static final String TAG = "SwipeGenre";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    ArrayList<String> storeResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_genre);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    Toast.makeText(SwipeGenre.this, "Direction Right", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Top){
                    Toast.makeText(SwipeGenre.this, "Direction Top", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left){
                    Toast.makeText(SwipeGenre.this, "Direction Left", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Bottom){
                    Toast.makeText(SwipeGenre.this, "Direction Bottom", Toast.LENGTH_SHORT).show();
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
    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        storeResult = (ArrayList<String>) getIntent().getSerializableExtra("book");
        // Single Button
        /* (storeResult.contains("NonFiction")) {
            items.add(new ItemModel(R.drawable.nonfiction, "Sapiens"));
            items.add(new ItemModel(R.drawable.nonfiction2, "A Brief History Of Time"));
            items.add(new ItemModel(R.drawable.nonfiction3, "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel(R.drawable.nonfiction4, "Into Thin Air"));
            items.add(new ItemModel(R.drawable.nonfiction5, "Bad Blood"));
            items.add(new ItemModel(R.drawable.nonfiction6, "The Diary Of Young Girl"));
            items.add(new ItemModel(R.drawable.nonfiction7, "Man's Search For Meaning"));
            return items;
        }*/
       /*else if(storeResult.contains("Romance")) {
            items.add(new ItemModel(R.drawable.romance, "The Notebook"));
            items.add(new ItemModel(R.drawable.romance2, "The Fault in Our Stars"));
            items.add(new ItemModel(R.drawable.romance3, "Gone with the Wind"));
            items.add(new ItemModel(R.drawable.romance4, "Beautiful Disaster"));
            items.add(new ItemModel(R.drawable.romance5, "Outlander"));
            items.add(new ItemModel(R.drawable.romance6, "Jane Eyre"));
            items.add(new ItemModel(R.drawable.romance7, "A Long Petal of the Sea"));
            return items;
        }
        else if(storeResult.contains("Fantasy")) {
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));
            return items;
        }
        else if(storeResult.contains("SelfHelp")){
            items.add(new ItemModel(R.drawable.selfhelp, "The 7 Habits"));
            items.add(new ItemModel(R.drawable.selfhelp2, "How to Win Friends"));
            items.add(new ItemModel(R.drawable.selfhelp3, "Think and Grow Rich"));
            items.add(new ItemModel(R.drawable.selfhelp4, "The 4-Hour Work Week"));
            items.add(new ItemModel(R.drawable.selfhelp5, "The Secret"));
            items.add(new ItemModel(R.drawable.selfhelp6, "Rich Dad Poor Dad"));
            items.add(new ItemModel(R.drawable.selfhelp7, "Atomic Habits"));
            return items;
        }
        else if(storeResult.contains("Detective")){
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("Comic")) {
            items.add(new ItemModel(R.drawable.comic, "Nimona"));
            items.add(new ItemModel(R.drawable.comic2, "Justic League"));
            items.add(new ItemModel(R.drawable.comic3, "Batman"));
            items.add(new ItemModel(R.drawable.comic4, "Daytripper"));
            items.add(new ItemModel(R.drawable.comic5, "Once & Future"));
            items.add(new ItemModel(R.drawable.comic6, "The Sherif Of Babylon"));
            items.add(new ItemModel(R.drawable.comic7, "Iron Man"));
            return items;
        }*/
        // Any one with nonfiction
       if(storeResult.contains("NonFiction") && storeResult.contains("Romance"))
        {
            items.add(new ItemModel(R.drawable.nonfiction, "Sapiens"));
            items.add(new ItemModel(R.drawable.nonfiction2, "A Brief History Of Time"));
            items.add(new ItemModel(R.drawable.nonfiction3, "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel(R.drawable.nonfiction4, "Into Thin Air"));
            items.add(new ItemModel(R.drawable.nonfiction5, "Bad Blood"));
            items.add(new ItemModel(R.drawable.nonfiction6, "The Diary Of Young Girl"));
            items.add(new ItemModel(R.drawable.nonfiction7, "Man's Search For Meaning"));
            items.add(new ItemModel(R.drawable.romance, "The Notebook"));
            items.add(new ItemModel(R.drawable.romance2, "The Fault in Our Stars"));
            items.add(new ItemModel(R.drawable.romance3, "Gone with the Wind"));
            items.add(new ItemModel(R.drawable.romance4, "Beautiful Disaster"));
            items.add(new ItemModel(R.drawable.romance5, "Outlander"));
            items.add(new ItemModel(R.drawable.romance6, "Jane Eyre"));
            items.add(new ItemModel(R.drawable.romance7, "A Long Petal of the Sea"));
            return items;
        }
        else if(storeResult.contains("NonFiction") && storeResult.contains("Fantasy"))
        {
            items.add(new ItemModel(R.drawable.nonfiction, "Sapiens"));
            items.add(new ItemModel(R.drawable.nonfiction2, "A Brief History Of Time"));
            items.add(new ItemModel(R.drawable.nonfiction3, "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel(R.drawable.nonfiction4, "Into Thin Air"));
            items.add(new ItemModel(R.drawable.nonfiction5, "Bad Blood"));
            items.add(new ItemModel(R.drawable.nonfiction6, "The Diary Of Young Girl"));
            items.add(new ItemModel(R.drawable.nonfiction7, "Man's Search For Meaning"));
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));
            return items;
        }
        else if(storeResult.contains("NonFiction") && storeResult.contains("SelfHelp"))
        {
            items.add(new ItemModel(R.drawable.nonfiction, "Sapiens"));
            items.add(new ItemModel(R.drawable.nonfiction2, "A Brief History Of Time"));
            items.add(new ItemModel(R.drawable.nonfiction3, "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel(R.drawable.nonfiction4, "Into Thin Air"));
            items.add(new ItemModel(R.drawable.nonfiction5, "Bad Blood"));
            items.add(new ItemModel(R.drawable.nonfiction6, "The Diary Of Young Girl"));
            items.add(new ItemModel(R.drawable.nonfiction7, "Man's Search For Meaning"));
            items.add(new ItemModel(R.drawable.selfhelp, "The 7 Habits"));
            items.add(new ItemModel(R.drawable.selfhelp2, "How to Win Friends"));
            items.add(new ItemModel(R.drawable.selfhelp3, "Think and Grow Rich"));
            items.add(new ItemModel(R.drawable.selfhelp4, "The 4-Hour Work Week"));
            items.add(new ItemModel(R.drawable.selfhelp5, "The Secret"));
            items.add(new ItemModel(R.drawable.selfhelp6, "Rich Dad Poor Dad"));
            items.add(new ItemModel(R.drawable.selfhelp7, "Atomic Habits"));
            return items;
        }
        else if(storeResult.contains("NonFiction") && storeResult.contains("Detective"))
        {
            items.add(new ItemModel(R.drawable.nonfiction, "Sapiens"));
            items.add(new ItemModel(R.drawable.nonfiction2, "A Brief History Of Time"));
            items.add(new ItemModel(R.drawable.nonfiction3, "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel(R.drawable.nonfiction4, "Into Thin Air"));
            items.add(new ItemModel(R.drawable.nonfiction5, "Bad Blood"));
            items.add(new ItemModel(R.drawable.nonfiction6, "The Diary Of Young Girl"));
            items.add(new ItemModel(R.drawable.nonfiction7, "Man's Search For Meaning"));
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("NonFiction") && storeResult.contains("Comic"))
        {
            items.add(new ItemModel(R.drawable.nonfiction, "Sapiens"));
            items.add(new ItemModel(R.drawable.nonfiction2, "A Brief History Of Time"));
            items.add(new ItemModel(R.drawable.nonfiction3, "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel(R.drawable.nonfiction4, "Into Thin Air"));
            items.add(new ItemModel(R.drawable.nonfiction5, "Bad Blood"));
            items.add(new ItemModel(R.drawable.nonfiction6, "The Diary Of Young Girl"));
            items.add(new ItemModel(R.drawable.nonfiction7, "Man's Search For Meaning"));
            items.add(new ItemModel(R.drawable.comic, "Nimona"));
            items.add(new ItemModel(R.drawable.comic2, "Justic League"));
            items.add(new ItemModel(R.drawable.comic3, "Batman"));
            items.add(new ItemModel(R.drawable.comic4, "Daytripper"));
            items.add(new ItemModel(R.drawable.comic5, "Once & Future"));
            items.add(new ItemModel(R.drawable.comic6, "The Sherif Of Babylon"));
            items.add(new ItemModel(R.drawable.comic7, "Iron Man"));
            return items;
        }
        //Any one with Romance
        else if(storeResult.contains("Romance") && storeResult.contains("Fantasy")){
            items.add(new ItemModel(R.drawable.romance, "The Notebook"));
            items.add(new ItemModel(R.drawable.romance2, "The Fault in Our Stars"));
            items.add(new ItemModel(R.drawable.romance3, "Gone with the Wind"));
            items.add(new ItemModel(R.drawable.romance4, "Beautiful Disaster"));
            items.add(new ItemModel(R.drawable.romance5, "Outlander"));
            items.add(new ItemModel(R.drawable.romance6, "Jane Eyre"));
            items.add(new ItemModel(R.drawable.romance7, "A Long Petal of the Sea"));
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));
            return items;
        }
        else if(storeResult.contains("Romance") && storeResult.contains("SelfHelp")){
            items.add(new ItemModel(R.drawable.romance, "The Notebook"));
            items.add(new ItemModel(R.drawable.romance2, "The Fault in Our Stars"));
            items.add(new ItemModel(R.drawable.romance3, "Gone with the Wind"));
            items.add(new ItemModel(R.drawable.romance4, "Beautiful Disaster"));
            items.add(new ItemModel(R.drawable.romance5, "Outlander"));
            items.add(new ItemModel(R.drawable.romance6, "Jane Eyre"));
            items.add(new ItemModel(R.drawable.romance7, "A Long Petal of the Sea"));
            items.add(new ItemModel(R.drawable.selfhelp, "The 7 Habits"));
            items.add(new ItemModel(R.drawable.selfhelp2, "How to Win Friends"));
            items.add(new ItemModel(R.drawable.selfhelp3, "Think and Grow Rich"));
            items.add(new ItemModel(R.drawable.selfhelp4, "The 4-Hour Work Week"));
            items.add(new ItemModel(R.drawable.selfhelp5, "The Secret"));
            items.add(new ItemModel(R.drawable.selfhelp6, "Rich Dad Poor Dad"));
            items.add(new ItemModel(R.drawable.selfhelp7, "Atomic Habits"));
            return items;
        }
        else if(storeResult.contains("Romance") && storeResult.contains("Detective")){
            items.add(new ItemModel(R.drawable.romance, "The Notebook"));
            items.add(new ItemModel(R.drawable.romance2, "The Fault in Our Stars"));
            items.add(new ItemModel(R.drawable.romance3, "Gone with the Wind"));
            items.add(new ItemModel(R.drawable.romance4, "Beautiful Disaster"));
            items.add(new ItemModel(R.drawable.romance5, "Outlander"));
            items.add(new ItemModel(R.drawable.romance6, "Jane Eyre"));
            items.add(new ItemModel(R.drawable.romance7, "A Long Petal of the Sea"));
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("Romance") && storeResult.contains("Comic")){
            items.add(new ItemModel(R.drawable.romance, "The Notebook"));
            items.add(new ItemModel(R.drawable.romance2, "The Fault in Our Stars"));
            items.add(new ItemModel(R.drawable.romance3, "Gone with the Wind"));
            items.add(new ItemModel(R.drawable.romance4, "Beautiful Disaster"));
            items.add(new ItemModel(R.drawable.romance5, "Outlander"));
            items.add(new ItemModel(R.drawable.romance6, "Jane Eyre"));
            items.add(new ItemModel(R.drawable.romance7, "A Long Petal of the Sea"));
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));

        }
        //Any one with Fantasy
        else if(storeResult.contains("Fantasy") && storeResult.contains("SelfHelp")){
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));
            items.add(new ItemModel(R.drawable.selfhelp, "The 7 Habits"));
            items.add(new ItemModel(R.drawable.selfhelp2, "How to Win Friends"));
            items.add(new ItemModel(R.drawable.selfhelp3, "Think and Grow Rich"));
            items.add(new ItemModel(R.drawable.selfhelp4, "The 4-Hour Work Week"));
            items.add(new ItemModel(R.drawable.selfhelp5, "The Secret"));
            items.add(new ItemModel(R.drawable.selfhelp6, "Rich Dad Poor Dad"));
            items.add(new ItemModel(R.drawable.selfhelp7, "Atomic Habits"));
            return items;

        }
        else if(storeResult.contains("Fantasy") && storeResult.contains("Detective")){
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("Fantasy") && storeResult.contains("Comic")){
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));
            items.add(new ItemModel(R.drawable.comic, "Nimona"));
            items.add(new ItemModel(R.drawable.comic2, "Justic League"));
            items.add(new ItemModel(R.drawable.comic3, "Batman"));
            items.add(new ItemModel(R.drawable.comic4, "Daytripper"));
            items.add(new ItemModel(R.drawable.comic5, "Once & Future"));
            items.add(new ItemModel(R.drawable.comic6, "The Sherif Of Babylon"));
            items.add(new ItemModel(R.drawable.comic7, "Iron Man"));
            return items;
        }
        //Any one with Selphelp
        else if(storeResult.contains("SelfHelp") && storeResult.contains("Detective")){
            items.add(new ItemModel(R.drawable.selfhelp, "The 7 Habits"));
            items.add(new ItemModel(R.drawable.selfhelp2, "How to Win Friends"));
            items.add(new ItemModel(R.drawable.selfhelp3, "Think and Grow Rich"));
            items.add(new ItemModel(R.drawable.selfhelp4, "The 4-Hour Work Week"));
            items.add(new ItemModel(R.drawable.selfhelp5, "The Secret"));
            items.add(new ItemModel(R.drawable.selfhelp6, "Rich Dad Poor Dad"));
            items.add(new ItemModel(R.drawable.selfhelp7, "Atomic Habits"));
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            return items;
        }
        else if(storeResult.contains("SelfHelp") && storeResult.contains("Comic")){
            items.add(new ItemModel(R.drawable.selfhelp, "The 7 Habits"));
            items.add(new ItemModel(R.drawable.selfhelp2, "How to Win Friends"));
            items.add(new ItemModel(R.drawable.selfhelp3, "Think and Grow Rich"));
            items.add(new ItemModel(R.drawable.selfhelp4, "The 4-Hour Work Week"));
            items.add(new ItemModel(R.drawable.selfhelp5, "The Secret"));
            items.add(new ItemModel(R.drawable.selfhelp6, "Rich Dad Poor Dad"));
            items.add(new ItemModel(R.drawable.selfhelp7, "Atomic Habits"));
            items.add(new ItemModel(R.drawable.comic, "Nimona"));
            items.add(new ItemModel(R.drawable.comic2, "Justic League"));
            items.add(new ItemModel(R.drawable.comic3, "Batman"));
            items.add(new ItemModel(R.drawable.comic4, "Daytripper"));
            items.add(new ItemModel(R.drawable.comic5, "Once & Future"));
            items.add(new ItemModel(R.drawable.comic6, "The Sherif Of Babylon"));
            items.add(new ItemModel(R.drawable.comic7, "Iron Man"));
            return items;
        }
        //Any one with Detective
        else if(storeResult.contains("Detective") && storeResult.contains("Comic")){
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            items.add(new ItemModel(R.drawable.comic, "Nimona"));
            items.add(new ItemModel(R.drawable.comic2, "Justic League"));
            items.add(new ItemModel(R.drawable.comic3, "Batman"));
            items.add(new ItemModel(R.drawable.comic4, "Daytripper"));
            items.add(new ItemModel(R.drawable.comic5, "Once & Future"));
            items.add(new ItemModel(R.drawable.comic6, "The Sherif Of Babylon"));
            items.add(new ItemModel(R.drawable.comic7, "Iron Man"));
            return items;

        }
        else
        {
            items.add(new ItemModel(R.drawable.nonfiction, "Sapiens"));
            items.add(new ItemModel(R.drawable.nonfiction2, "A Brief History Of Time"));
            items.add(new ItemModel(R.drawable.nonfiction3, "I Know Why The Caged Bird Sings"));
            items.add(new ItemModel(R.drawable.nonfiction4, "Into Thin Air"));
            items.add(new ItemModel(R.drawable.nonfiction5, "Bad Blood"));
            items.add(new ItemModel(R.drawable.nonfiction6, "The Diary Of Young Girl"));
            items.add(new ItemModel(R.drawable.nonfiction7, "Man's Search For Meaning"));
            items.add(new ItemModel(R.drawable.romance, "The Notebook"));
            items.add(new ItemModel(R.drawable.romance2, "The Fault in Our Stars"));
            items.add(new ItemModel(R.drawable.romance3, "Gone with the Wind"));
            items.add(new ItemModel(R.drawable.romance4, "Beautiful Disaster"));
            items.add(new ItemModel(R.drawable.romance5, "Outlander"));
            items.add(new ItemModel(R.drawable.romance6, "Jane Eyre"));
            items.add(new ItemModel(R.drawable.romance7, "A Long Petal of the Sea"));
            items.add(new ItemModel(R.drawable.fantasy, "Harry Potter"));
            items.add(new ItemModel(R.drawable.fantasy2, "The Hobbit"));
            items.add(new ItemModel(R.drawable.fantasy3, "A Game of Thrones"));
            items.add(new ItemModel(R.drawable.fantasy4, "The Palace Of Illusions"));
            items.add(new ItemModel(R.drawable.fantasy5, "Malgudi Day's"));
            items.add(new ItemModel(R.drawable.fantasy6, "Alice's Adventures in Wonderland"));
            items.add(new ItemModel(R.drawable.fantasy7, "The Time Machine"));
            items.add(new ItemModel(R.drawable.selfhelp, "The 7 Habits"));
            items.add(new ItemModel(R.drawable.selfhelp2, "How to Win Friends"));
            items.add(new ItemModel(R.drawable.selfhelp3, "Think and Grow Rich"));
            items.add(new ItemModel(R.drawable.selfhelp4, "The 4-Hour Work Week"));
            items.add(new ItemModel(R.drawable.selfhelp5, "The Secret"));
            items.add(new ItemModel(R.drawable.selfhelp6, "Rich Dad Poor Dad"));
            items.add(new ItemModel(R.drawable.selfhelp7, "Atomic Habits"));
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            items.add(new ItemModel(R.drawable.detective, "Murder on the Orient Express"));
            items.add(new ItemModel(R.drawable.detective1, "Sherlock Holmes"));
            items.add(new ItemModel(R.drawable.detective2, "The Girl with the Dragon Tatoo"));
            items.add(new ItemModel(R.drawable.detective3, "The murder of Roger Ackroyd"));
            items.add(new ItemModel(R.drawable.detective4, "The Murders in the Rue Morgue"));
            items.add(new ItemModel(R.drawable.detective5, "The Guest List"));
            items.add(new ItemModel(R.drawable.detective7, "A Study in Scarlet"));
            return items;
        }
            return null;

    }

}