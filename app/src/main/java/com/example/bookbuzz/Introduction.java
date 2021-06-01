package com.example.bookbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Introduction extends AppCompatActivity {
    Animation anim;
    ImageView bg;
    ImageView logo;
    TextView logoname;
    LottieAnimationView lottieAnimationView;
    private static final int numpages=3;
    private ViewPager viewpager;
    private ScreenSlidePageAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introduction);
        logoname= findViewById(R.id.logo_name);
        logo=findViewById(R.id.logo);
        lottieAnimationView=findViewById(R.id.intro);
        bg=findViewById(R.id.bg);
        bg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logoname.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        viewpager=findViewById(R.id.liquidpager);
        pagerAdapter=new ScreenSlidePageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        anim = AnimationUtils.loadAnimation(this,R.anim.intro);
        viewpager.startAnimation(anim);

    }
    public void skip(View view){
        Intent i= new Intent(this, HomeActivity.class);
        startActivity(i);
    }
    private class ScreenSlidePageAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
           switch (position){
               case 0:
                   Intro_fragment1 tab1= new Intro_fragment1();
                   return tab1;
               case 1:
                   Intro_fragment2 tab2= new Intro_fragment2();
                   return tab2;
               case 2:
                   Intro_fragment3 tab3= new Intro_fragment3();
                   return tab3;
           }
           return null;
        }

        @Override
        public int getCount() {
            return numpages;
        }
    }
}