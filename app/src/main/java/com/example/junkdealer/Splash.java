package com.example.junkdealer;



import android.content.Intent;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class Splash extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {
//        ActionBar actionB


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        configSplash.setBackgroundColor(R.color.colorAccent);
        configSplash.setAnimCircularRevealDuration(1500);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);

        configSplash.setLogoSplash(R.drawable.splash_icon);
        configSplash.setAnimLogoSplashDuration(1500);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);

        configSplash.setTitleSplash("Junk O' Scrap");
        configSplash.setTitleTextColor(R.color.colorPrimary);
        configSplash.setTitleTextSize(50f);
        configSplash.setAnimTitleDuration(500);
        configSplash.setAnimTitleTechnique(Techniques.BounceIn);
    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(Splash.this,Login.class));
        finish();
    }
}



//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.Menu;
//
//public class Splash extends Activity {
//
//    /** Duration of wait **/
//    private final int SPLASH_DISPLAY_LENGTH = 1000;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle icicle) {
//        super.onCreate(icicle);
//        setContentView(R.layout.activity_splash);
//
//        /* New Handler to start the Menu-Activity
//         * and close this Splash-Screen after some seconds.*/
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(Splash.this, Login.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
//    }
//}