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
        if (storeResult.contains("Fiction") && storeResult.contains("romantic")) {
            items.add(new ItemModel(R.drawable.fiction, "Fiction"));
            items.add(new ItemModel(R.drawable.mystery, "romantic"));
            return items;
        }
        else if (storeResult.contains("romantic") && storeResult.contains("horror")) {
            items.add(new ItemModel(R.drawable.mystery, "romantic"));
            items.add(new ItemModel(R.drawable.novel, "horror"));
            return items;
        }
        else if (storeResult.contains("horror") && storeResult.contains("Suspense")) {
            items.add(new ItemModel(R.drawable.novel, "horror"));
            items.add(new ItemModel(R.drawable.sciencefiction, "Suspense"));
            return items;
        }
        else if (storeResult.contains("Suspense") && storeResult.contains("poetry")) {
            items.add(new ItemModel(R.drawable.sciencefiction, "Suspense"));
            items.add(new ItemModel(R.drawable.sciencefiction, "poetry"));
            return items;
        }
        else if (storeResult.contains("poetry")){
            items.add(new ItemModel(R.drawable.sciencefiction, "poetry"));
            return items;
        }
        return null;

    }

}