package id.fando;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

public class GDPRChecker  {
    private ConsentInformation consentInformation;
    private ConsentForm consentForm;
    private String appid;
    public static   GDPRChecker instance;
    private Context context;
    private  ConsentDebugSettings debugSettings;
    private Activity activity;

    public GDPRChecker(Context context) {
        this.context=context;


    }

    public GDPRChecker() {

    }


    public GDPRChecker withContext(Context context) {

        instance = new GDPRChecker(context);
        return instance;
    }



    public GDPRChecker withActivity(Activity activty) {
        this.activity=activty;
        return instance;
    }

    public GDPRChecker withDebug(){
           debugSettings = new ConsentDebugSettings.Builder(context)
                .setDebugGeography(ConsentDebugSettings
                        .DebugGeography
                        .DEBUG_GEOGRAPHY_EEA)
                .build();
        if (instance == null)
            throw new NullPointerException("Please call withContext first");
        return instance;
    }


    public GDPRChecker withAppId(String appid) {
        this.appid = appid;
        if (instance == null)
            throw new NullPointerException("Please call withContext first");
        return instance;
    }


    public   void check (){
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setAdMobAppId(appid)
                .setConsentDebugSettings(debugSettings)
                .build();
        consentInformation = UserMessagingPlatform.getConsentInformation(context);
        consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        if (consentInformation.isConsentFormAvailable()) {
                            loadForm(context,activity);
                        }
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        Log.e("Errrrr",formError.getErrorCode()+formError.getMessage());
                        // Handle the error.
                    }
                });


    }

    private void loadForm(final Context context, final Activity activity){
        UserMessagingPlatform.loadConsentForm(
                context,
                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        GDPRChecker.this.consentForm = consentForm;

                        if(consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm.show(activity,
                                    new ConsentForm.OnConsentFormDismissedListener() {
                                        @Override
                                        public void onConsentFormDismissed(@Nullable FormError formError) {
                                            // Handle dismissal by reloading form.
                                            loadForm(context, activity);
                                        }
                                    });

                        }

                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        /// Handle Error.
                    }
                }
        );
    }
}
