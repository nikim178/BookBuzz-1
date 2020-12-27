package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.bookbuzz.adapter.MainRecyclerAdapter;
import com.example.bookbuzz.model.AllCategory;
import com.example.bookbuzz.model.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class Home3Activity extends AppCompatActivity {

    RecyclerView mainCategoryRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);



        List<CategoryItem> categoryItemList = new ArrayList<>();
        categoryItemList.add(new CategoryItem(1,R.drawable.book1));
        categoryItemList.add(new CategoryItem(1,R.drawable.book2));
        categoryItemList.add(new CategoryItem(1,R.drawable.book3));
        categoryItemList.add(new CategoryItem(1,R.drawable.book4));


        List<CategoryItem> categoryItemList2 = new ArrayList<>();
        categoryItemList2.add(new CategoryItem(1,R.drawable.book1));
        categoryItemList2.add(new CategoryItem(1,R.drawable.book2));
        categoryItemList2.add(new CategoryItem(1,R.drawable.book3));
        categoryItemList2.add(new CategoryItem(1,R.drawable.book4));

        List<CategoryItem> categoryItemList3 = new ArrayList<>();
        categoryItemList3.add(new CategoryItem(1,R.drawable.book1));
        categoryItemList3.add(new CategoryItem(1,R.drawable.book2));
        categoryItemList3.add(new CategoryItem(1,R.drawable.book3));
        categoryItemList3.add(new CategoryItem(1,R.drawable.book4));

        List<CategoryItem> categoryItemList4 = new ArrayList<>();
        categoryItemList4.add(new CategoryItem(1,R.drawable.book1));
        categoryItemList4.add(new CategoryItem(1,R.drawable.book2));
        categoryItemList4.add(new CategoryItem(1,R.drawable.book3));
        categoryItemList4.add(new CategoryItem(1,R.drawable.book4));

        List<CategoryItem> categoryItemList5 = new ArrayList<>();
        categoryItemList5.add(new CategoryItem(1,R.drawable.book1));
        categoryItemList5.add(new CategoryItem(1,R.drawable.book2));
        categoryItemList5.add(new CategoryItem(1,R.drawable.book3));
        categoryItemList5.add(new CategoryItem(1,R.drawable.book4));


        List<AllCategory> allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory("Novels", categoryItemList));
        allCategoryList.add(new AllCategory("Autobiography", categoryItemList2));
        allCategoryList.add(new AllCategory("Storybooks", categoryItemList3));
        allCategoryList.add(new AllCategory("CAT4", categoryItemList4));
        allCategoryList.add(new AllCategory("CAT5", categoryItemList5));

        setMainCategoryRecycler(allCategoryList);


    }

    private void setMainCategoryRecycler(List<AllCategory> allCategoryList){

        mainCategoryRecycler = findViewById(R.id.main_recycler1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

    }

}