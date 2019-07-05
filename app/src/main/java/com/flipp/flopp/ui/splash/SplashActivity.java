/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 12:11 PM
 *
 * Last modified 7/1/19 12:10 PM
 */

package com.flipp.flopp.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

    private ImageView ivLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        fadeIn(ivLogo);
    }

    private void fadeIn(View view){
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //On animation complete go to main activity
                        Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    }
                });
    }
}
