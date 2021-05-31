package com.example.bookbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
       // anim = AnimationUtils.LoadAnimation(this,R.anim.intro2);
        logoname= findViewById(R.id.logo_name);
        logo=findViewById(R.id.logo);
        lottieAnimationView=findViewById(R.id.intro);
        bg=findViewById(R.id.bg);
        bg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logoname.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

    }
}