package com.aliendroid.alienads;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.mediation.ads.MaxInterstitialAd;
import com.google.ads.mediation.facebook.FacebookAdapter;
import com.google.ads.mediation.facebook.FacebookExtras;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ironsource.mediationsdk.IronSource;
import com.mopub.mobileads.MoPubInterstitial;
import com.startapp.sdk.adsbase.StartAppAd;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class AliendroidIntertitial {
    public static InterstitialAd mInterstitialAd;
    public static MaxInterstitialAd interstitialAd;
    public static MoPubInterstitial mInterstitial;
    public static int counter = 0;

    public static void LoadIntertitial(Activity activity, String selectAds, String idIntertitial) {
        switch (selectAds) {
            case "ADMOB":
                Bundle extras = new FacebookExtras()
                        .setNativeBanner(true)
                        .build();
                AdRequest request = new AdRequest.Builder()
                        .addNetworkExtrasBundle(FacebookAdapter.class, extras)
                        .build();
                InterstitialAd.load(activity, idIntertitial, request,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd;
                                Log.i(TAG, "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                Log.i(TAG, loadAdError.getMessage());
                                mInterstitialAd = null;
                            }
                        });
                break;
            case "APPLOVIN":
                interstitialAd = new MaxInterstitialAd(idIntertitial, activity);
                interstitialAd.loadAd();
                break;
            case "MOPUB":
                mInterstitial = new MoPubInterstitial((Activity) activity, idIntertitial);
                mInterstitial.load();
                break;
            case "IRON":
                IronSource.isInterstitialPlacementCapped(idIntertitial);
                IronSource.loadInterstitial();
                break;

        }
    }

    public static void ShowIntertitial(Activity activity, String selectAds, String idIntertitial, int interval) {
        if (counter >= interval) {
            switch (selectAds) {
                case "ADMOB":
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(activity);
                        LoadIntertitial(activity, selectAds, idIntertitial);
                    } else {
                        LoadIntertitial(activity, selectAds, idIntertitial);
                    }
                    break;
                case "APPLOVIN":
                    if (interstitialAd.isReady()) {
                        interstitialAd.showAd();
                        interstitialAd.loadAd();
                    } else {
                        interstitialAd.loadAd();
                    }
                    break;
                case "MOPUB":
                    if (mInterstitial.isReady()) {
                        mInterstitial.show();
                        mInterstitial.load();
                    } else {
                        mInterstitial.load();
                    }
                    break;
                case "IRON":
                    IronSource.showInterstitial(idIntertitial);
                    break;
                case "STARTAPP":
                    StartAppAd.showAd(activity);
                    break;
            }
            counter = 0;
        } else {
            counter++;
        }
    }
}
