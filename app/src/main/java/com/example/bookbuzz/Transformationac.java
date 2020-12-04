package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Transformationac extends AppCompatActivity {

    ViewPager viewPager;
    Adapter pagerAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformationac);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        pagerAdapter = new Adapter(getSupportFragmentManager());
        addingFragmentsTOpagerAdapter();
        viewPager.setAdapter(pagerAdapter);


        DepthTrans depthTransformation = new DepthTrans();

        viewPager.setPageTransformer(true, depthTransformation);

    }
    public void skip(View v){
        Intent i= new Intent(this,Home.class);
        startActivity(i);
    }


    private void addingFragmentsTOpagerAdapter() {
        pagerAdapter.addFragments(new Fragment1());
        pagerAdapter.addFragments(new Fragment2());
        pagerAdapter.addFragments(new Fragment3());
        pagerAdapter.addFragments(new Fragment4());

    }


}