/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 12:11 PM
 *
 * Last modified 7/1/19 12:10 PM
 */

package com.flipp.flopp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.flipp.flopp.R;
import com.flipp.flopp.ui.main.DetailActivity;
import com.flipp.flopp.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

/***
 * Splash displaying Flopp logo
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //When visible show the logo with alpha animation
        ImageView ivLogo = (ImageView) findViewById(R.id.ivLogo);
        ivLogo.setVisibility(View.INVISIBLE);
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setFillAfter(true);
        animation1.setDuration(500);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // pass it visible before starting the animation
                ivLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {    }
            @Override
            public void onAnimationEnd(Animation animation) {
                //On animation complete go to main activity
                Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();

            }
        });
        ivLogo.startAnimation(animation1);
    }
}
