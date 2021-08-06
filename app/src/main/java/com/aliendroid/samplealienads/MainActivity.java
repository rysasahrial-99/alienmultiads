package com.aliendroid.samplealienads;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidInitialize;
import com.aliendroid.alienads.AliendroidIntertitial;

public class MainActivity extends AppCompatActivity {

    /*
    APPLOVIN_BANNER = "db4d5e8718b97d78";
    APPLOVIN_INTER = "518cd97722c60b52";

    BANNER_MOPUB = "b195f8dd8ded45fe847ad89ed1d016da";
    INTER_MOPUB = "24534e1901884e398f1253216226017e";

    ADMOB_INTER = "ca-app-pub-3940256099942544/1033173712";
    ADMOB_BANNER = "ca-app-pub-3940256099942544/6300978111";

    IRON_BANNER="DefaultBanner";
    IRON_INTERTITIAL="Game_Screen";
    IRON_APPID="10301c6e9";

    STARTAPPID="123456789";
     */

    String SELECT_ADS = "STARTAPP";
    String BANNER_ID = "null";
    String MEDIUM_BANNER_ID = "null";
    String INTERTITIAL_ID = "null";
    String IdInitialize = "123456789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout layAds = findViewById(R.id.layAds);
        RelativeLayout layAdsMedium = findViewById(R.id.layAdsMedium);

        AliendroidInitialize.SelectAds(MainActivity.this, SELECT_ADS, IdInitialize);

        AliendroidBanner.SmallBanner(MainActivity.this, layAds, SELECT_ADS,
                BANNER_ID);

        AliendroidBanner.MediumBanner(MainActivity.this, layAdsMedium, SELECT_ADS,
                MEDIUM_BANNER_ID);


        AliendroidIntertitial.LoadIntertitial(MainActivity.this, SELECT_ADS,
                INTERTITIAL_ID);

    }

    public void showInter(View view) {
        AliendroidIntertitial.ShowIntertitial(MainActivity.this, SELECT_ADS,
                INTERTITIAL_ID, 0);
    }
}