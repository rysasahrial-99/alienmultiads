package com.aliendroid.alienads;

import android.app.Activity;
import android.util.Log;

import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.FacebookBanner;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.HashMap;
import java.util.Map;


public class AliendroidInitialize {
    public static void SelectAds(Activity activity, String selectAds, String idInitialize) {
        switch (selectAds) {
            case "ADMOB":
                MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                        Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                        for (String adapterClass : statusMap.keySet()) {
                            AdapterStatus status = statusMap.get(adapterClass);
                            Log.d("MyApp", String.format(
                                    "Adapter name: %s, Description: %s, Latency: %d",
                                    adapterClass, status.getDescription(), status.getLatency()));
                        }
                    }
                });
                break;
            case "APPLOVIN":
                AdSettings.setDataProcessingOptions(new String[]{});
                AppLovinSdk.getInstance(activity).setMediationProvider(AppLovinMediationProvider.MAX);
                AppLovinSdk sdk = AppLovinSdk.getInstance(activity);
                sdk.getSettings().setMuted(!sdk.getSettings().isMuted());
                break;
            case "MOPUB":
                Map<String, String> facebookBanner = new HashMap<>();
                facebookBanner.put("native_banner", "true");
                SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(idInitialize);
                configBuilder.withMediatedNetworkConfiguration(FacebookBanner.class.getName(), facebookBanner);
                MoPub.initializeSdk(activity, configBuilder.build(), initSdkListener());
                break;
            case "IRON":
                IronSource.init(activity, idInitialize);
                IntegrationHelper.validateIntegration(activity);
                break;
            case "STARTAPP":
                StartAppSDK.init(activity, idInitialize, true);
                StartAppAd.disableSplash();
                StartAppSDK.setUserConsent(activity,
                        "pas",
                        System.currentTimeMillis(),
                        true);
                break;
        }
    }

    private static SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
            }
        };
    }

}
