package com.mazmaz.sharedhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScrActivity extends AppCompatActivity {

    private ImageView logoSplash;
    private Animation anim1, anim2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_scr);
        init();


        logoSplash.startAnimation(anim1);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logoSplash.startAnimation(anim2);
                anim2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        logoSplash.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                        startActivity(new Intent(SplashScrActivity.this,LoginActivity.class));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }


    private void init(){

        logoSplash = findViewById(R.id.ivLogoSplash);
        anim1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        anim2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout);
    }


}