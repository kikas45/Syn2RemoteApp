package sync2app.com.syncapplive;

import static sync2app.com.syncapplive.WebActivity.hasPermissions;
import static sync2app.com.syncapplive.constants.AllowOnlyHostUrlInApp;
import static sync2app.com.syncapplive.constants.ChangeBottombarBgColor;
import static sync2app.com.syncapplive.constants.ChangeDrawerHeaderBgColor;
import static sync2app.com.syncapplive.constants.ChangeHeaderTextColor;
import static sync2app.com.syncapplive.constants.ChangeTittleTextColor;
import static sync2app.com.syncapplive.constants.ChangeToolbarBgColor;
import static sync2app.com.syncapplive.constants.EnableWelcomeSlider;
import static sync2app.com.syncapplive.constants.ForceUpdate;
import static sync2app.com.syncapplive.constants.NewVersion;
import static sync2app.com.syncapplive.constants.Notifx_service;
import static sync2app.com.syncapplive.constants.OnesigID;
import static sync2app.com.syncapplive.constants.ServerUrl;
import static sync2app.com.syncapplive.constants.ShowAdmobInterstitial;
import static sync2app.com.syncapplive.constants.ShowBottomBar;
import static sync2app.com.syncapplive.constants.ShowDrawer;
import static sync2app.com.syncapplive.constants.ShowServerUrlSetUp;
import static sync2app.com.syncapplive.constants.ShowToolbar;
import static sync2app.com.syncapplive.constants.ShowWebBtn;
import static sync2app.com.syncapplive.constants.ToolbarBgColor;
import static sync2app.com.syncapplive.constants.ToolbarTitleText;
import static sync2app.com.syncapplive.constants.ToolbarTitleTextColor;
import static sync2app.com.syncapplive.constants.UpdateAvailable;
import static sync2app.com.syncapplive.constants.UpdateMessage;
import static sync2app.com.syncapplive.constants.UpdateTitle;
import static sync2app.com.syncapplive.constants.UpdateUrl;
import static sync2app.com.syncapplive.constants.Web_button_Img_link;
import static sync2app.com.syncapplive.constants.Web_button_link;
import static sync2app.com.syncapplive.constants.bottomBtn1ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn2ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn3ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn4ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn5ImgUrl;
import static sync2app.com.syncapplive.constants.bottomBtn6ImgUrl;
import static sync2app.com.syncapplive.constants.bottomUrl1;
import static sync2app.com.syncapplive.constants.bottomUrl2;
import static sync2app.com.syncapplive.constants.bottomUrl3;
import static sync2app.com.syncapplive.constants.bottomUrl4;
import static sync2app.com.syncapplive.constants.bottomUrl5;
import static sync2app.com.syncapplive.constants.bottomUrl6;
import static sync2app.com.syncapplive.constants.drawerHeaderBgColor;
import static sync2app.com.syncapplive.constants.drawerHeaderImgCommand;
import static sync2app.com.syncapplive.constants.drawerHeaderImgUrl;
import static sync2app.com.syncapplive.constants.drawerHeaderText;
import static sync2app.com.syncapplive.constants.drawerHeaderTextColor;
import static sync2app.com.syncapplive.constants.drawerMenuBtnUrl;
import static sync2app.com.syncapplive.constants.drawerMenuImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem1ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem1Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem1Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem2ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem2Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem2Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem3ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem3Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem3Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem4ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem4Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem4Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem5ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem5Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem5Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem6ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem6Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem6Url;
import static sync2app.com.syncapplive.constants.drawerMenuItem7ImgUrl;
import static sync2app.com.syncapplive.constants.drawerMenuItem7Text;
import static sync2app.com.syncapplive.constants.drawerMenuItem7Url;
import static sync2app.com.syncapplive.constants.filterdomain;
import static sync2app.com.syncapplive.constants.jsonUrl;
import static sync2app.com.syncapplive.constants.screen1BgColor;
import static sync2app.com.syncapplive.constants.screen1Desc;
import static sync2app.com.syncapplive.constants.screen1Img;
import static sync2app.com.syncapplive.constants.screen1TextColor;
import static sync2app.com.syncapplive.constants.screen1TitleText;
import static sync2app.com.syncapplive.constants.screen2BgColor;
import static sync2app.com.syncapplive.constants.screen2Desc;
import static sync2app.com.syncapplive.constants.screen2Img;
import static sync2app.com.syncapplive.constants.screen2TextColor;
import static sync2app.com.syncapplive.constants.screen2TitleText;
import static sync2app.com.syncapplive.constants.screen3BgColor;
import static sync2app.com.syncapplive.constants.screen3Desc;
import static sync2app.com.syncapplive.constants.screen3Img;
import static sync2app.com.syncapplive.constants.screen3TextColor;
import static sync2app.com.syncapplive.constants.screen3TitleText;
import static sync2app.com.syncapplive.constants.screen4BgColor;
import static sync2app.com.syncapplive.constants.screen4Desc;
import static sync2app.com.syncapplive.constants.screen4Img;
import static sync2app.com.syncapplive.constants.screen4TextColor;
import static sync2app.com.syncapplive.constants.screen4TitleText;
import static sync2app.com.syncapplive.constants.splashScreenUrl;
import static sync2app.com.syncapplive.constants.splashUrl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import sync2app.com.syncapplive.additionalSettings.utils.Constants;



public class Splash extends AppCompatActivity{



    String Jsonurl = ServerUrl;

    TextView infotext;
    ProgressBar progressBar;
    Button retryBtn;


    int clickcount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        } catch (Exception e) {
            e.printStackTrace();
        }


        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences1.getBoolean("darktheme", false)) {
            setTheme(R.style.DarkTheme);
        }
        setContentView(R.layout.activity_splash);
//        preferences1 = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("surl", "");

        if (name.equals("")){

        }else {
            if (name.startsWith("http://")|name.startsWith("https://")&name.endsWith("json")){
                ServerUrl = name;
                Log.d("Remote Execution", "Using custom server address");
            } else {
                Log.d("Remote Execution", "Invalid server url"+name);
            }

        }





        //THIS IS THE SPLASH ACTIVITY
//        rootlayout = findViewById(R.id.splash);
//        if (preferences.getBoolean("darktheme", false)) {
//            rootlayout.setBackgroundColor(getResources().getColor(R.color.darkthemeColor));
//        }





        @SuppressLint("CommitPrefEdits")
        SharedPreferences simpleSavedPassword = getSharedPreferences(Constants.SIMPLE_SAVED_PASSWORD, Context.MODE_PRIVATE);

        String CheckForPassword = simpleSavedPassword.getString(Constants.onCreatePasswordSaved, "");

        if (CheckForPassword.isEmpty()) {
            SharedPreferences.Editor editor = simpleSavedPassword.edit();
            editor.putString(Constants.onCreatePasswordSaved, "onCreatePasswordSaved");
            editor.putString(Constants.simpleSavedPassword, "1234");
            editor.apply();

        }




        ImageView view = findViewById(R.id.splash_image);
        infotext = findViewById(R.id.splash_sub);
        progressBar = findViewById(R.id.splash_progress);
        retryBtn = findViewById(R.id.retryntn);

        String splashLoadStatus = preferences.getString("splashStarted", null);
        String filename = "splash.png";
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File splashImg = new File(path +File.separator+ filename);


        if (hasPermissions(Splash.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if(splashImg.length()==0) {

                Log.d("Remote Execution", "Invalid splash img");
            }else {
                try {
                    if (splashLoadStatus.equals("finished")) {
                        Drawable drw = Drawable.createFromPath(splashImg.getAbsolutePath());
                        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                        view.setImageDrawable(drw);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }




//        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_fadein);
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setRepeatCount(Animation.INFINITE);
//        animation.setDuration(1300);
//
//
//
//        view.startAnimation(animation);
        int SPLASH_TIME_OUT = 1300;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ApiCall(Splash.this, Jsonurl);
                progressBar.setVisibility(View.VISIBLE);

            }
        }, SPLASH_TIME_OUT);
    }

    public void ApiCall(Context context, String url) {

        infotext.setText(R.string.connecting);
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        infotext.setText(R.string.initializing);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject remoteJson = jsonObject.getJSONObject("remoteConfig");

                            String homeurl = remoteJson.getString("homeUrl");

                            //BOTTOM BAR
                            ShowBottomBar = remoteJson.getBoolean("ShowBottomBar");
                            ChangeBottombarBgColor = remoteJson.getBoolean("ChangeBottomBarBgColor");
                            constants.bottomBarBgColor = remoteJson.getString("bottomBarBackgroundColor");

                            //Bottom Menu Actions
                            bottomUrl1 = remoteJson.getString("bottom1");
                            bottomUrl2 = remoteJson.getString("bottom2");
                            bottomUrl3 = remoteJson.getString("bottom3");
                            bottomUrl4 = remoteJson.getString("bottom4");
                            bottomUrl5 = remoteJson.getString("bottom5");
                            bottomUrl6 = remoteJson.getString("bottom6");

                            //Bottom Menu icons
                            bottomBtn1ImgUrl = remoteJson.getString("bottom1_img_url");
                            bottomBtn2ImgUrl = remoteJson.getString("bottom2_img_url");
                            bottomBtn3ImgUrl = remoteJson.getString("bottom3_img_url");
                            bottomBtn4ImgUrl = remoteJson.getString("bottom4_img_url");
                            bottomBtn5ImgUrl = remoteJson.getString("bottom5_img_url");
                            bottomBtn6ImgUrl = remoteJson.getString("bottom6_img_url");

                            //DRAWER MENU
                            ChangeDrawerHeaderBgColor = remoteJson.getBoolean("ChangeDrawerHeaderColor");
                            ChangeHeaderTextColor= remoteJson.getBoolean("ChangeDrawerHeaderTextColor");

                            ShowDrawer = remoteJson.getBoolean("ShowDrawerMenu");
                            drawerMenuBtnUrl = remoteJson.getString("DrawerMenuUrl");
                            drawerMenuImgUrl = remoteJson.getString("DrawerMenuImgUrl");

                            drawerMenuItem1ImgUrl = remoteJson.getString("DrawerMenuImg1Url");
                            drawerMenuItem2ImgUrl = remoteJson.getString("DrawerMenuImg2Url");
                            drawerMenuItem3ImgUrl = remoteJson.getString("DrawerMenuImg3Url");
                            drawerMenuItem4ImgUrl = remoteJson.getString("DrawerMenuImg4Url");
                            drawerMenuItem5ImgUrl = remoteJson.getString("DrawerMenuImg5Url");
                            drawerMenuItem6ImgUrl = remoteJson.getString("DrawerMenuImg6Url");

                            drawerMenuItem1Url = remoteJson.getString("DrawerMenuItem1Url");
                            drawerMenuItem2Url = remoteJson.getString("DrawerMenuItem2Url");
                            drawerMenuItem3Url = remoteJson.getString("DrawerMenuItem3Url");
                            drawerMenuItem4Url = remoteJson.getString("DrawerMenuItem4Url");
                            drawerMenuItem5Url = remoteJson.getString("DrawerMenuItem5Url");
                            drawerMenuItem6Url = remoteJson.getString("DrawerMenuItem6Url");


                            drawerMenuItem1Text = remoteJson.getString("DrawerMenuItem1Title");
                            drawerMenuItem2Text = remoteJson.getString("DrawerMenuItem2Title");
                            drawerMenuItem3Text = remoteJson.getString("DrawerMenuItem3Title");
                            drawerMenuItem4Text = remoteJson.getString("DrawerMenuItem4Title");
                            drawerMenuItem5Text = remoteJson.getString("DrawerMenuItem5Title");
                            drawerMenuItem6Text = remoteJson.getString("DrawerMenuItem6Title");

                            drawerHeaderImgUrl = remoteJson.getString("DrawerHeaderImgUrl");
                            drawerHeaderText = remoteJson.getString("DrawerHeaderText");
                            drawerHeaderImgCommand = remoteJson.getString("DrawerHeaderImgCommand");
                            drawerHeaderBgColor = remoteJson.getString("DrawerHeaderBgColor");
                            drawerHeaderTextColor =remoteJson.getString("DrawerHeaderTextColor");



                            //TOOLBAR
                            ShowToolbar = remoteJson.getBoolean("ShowToolbar");
                            ToolbarTitleText = remoteJson.getString("ToolbarTitleText");
                            ToolbarTitleTextColor = remoteJson.getString("ToolbarTitleTextColor");
                            ToolbarBgColor = remoteJson.getString("ToolbarBgColor");

                            ChangeToolbarBgColor = remoteJson.getBoolean("ChangeToolbarBgColor");
                            ChangeTittleTextColor = remoteJson.getBoolean("ChangeToolbarTitleTextColor");


                            //FLOATING BUTTON
                            Web_button_link = remoteJson.getString("webBtnUrl");
                            Web_button_Img_link = remoteJson.getString("webBtnImgUrl");
                            ShowWebBtn = remoteJson.getBoolean("ShowWebBtn");


                            //ADS
                            constants.ShowAdmobBanner = remoteJson.getBoolean("admobBanner");
                            ShowAdmobInterstitial = remoteJson.getBoolean("admobInter");

                            //Notifications
                            OnesigID = remoteJson.getString("onesigID");
                            splashUrl = remoteJson.getString("splashUrl");
                            Notifx_service = remoteJson.getBoolean("NotifXService");

                            //MORE
                            ShowServerUrlSetUp = remoteJson.getBoolean("AllowChangingServerUrl");
                            AllowOnlyHostUrlInApp = remoteJson.getBoolean("allowOnlyHostUrl");



                            //App Update
                            UpdateAvailable = remoteJson.getBoolean("UpdateAvailable");
                            ForceUpdate = remoteJson.getBoolean("ForceUpdate");
                            UpdateTitle = remoteJson.getString("Updatetitle");
                            UpdateMessage = remoteJson.getString("UpdateMsg");
                            UpdateUrl = remoteJson.getString("UpdateUrl");
                            NewVersion = remoteJson.getString("NewVersion");

//                            WELCOME SCREEN
                            EnableWelcomeSlider = remoteJson.getBoolean("AllowWelcomeSlider");

                            //screen title texts
                            screen1TitleText = remoteJson.getString("Screen1Title");
                            screen2TitleText = remoteJson.getString("Screen2Title");
                            screen3TitleText = remoteJson.getString("Screen3Title");
                            screen4TitleText = remoteJson.getString("Screen4Title");

                            //screen desc texts
                            screen1Desc = remoteJson.getString("screen1Desc");
                            screen2Desc = remoteJson.getString("screen2Desc");
                            screen3Desc = remoteJson.getString("screen3Desc");
                            screen4Desc = remoteJson.getString("screen4Desc");

                            //screen BG colors
                            screen1BgColor = remoteJson.getString("Screen1bgColor");
                            screen2BgColor = remoteJson.getString("Screen2bgColor");
                            screen3BgColor = remoteJson.getString("Screen3bgColor");
                            screen4BgColor = remoteJson.getString("Screen4bgColor");

                            //screen Text colors
                            screen1TextColor = remoteJson.getString("Screen1TxtColor");
                            screen2TextColor = remoteJson.getString("Screen2TxtColor");
                            screen3TextColor = remoteJson.getString("Screen3TxtColor");
                            screen4TextColor = remoteJson.getString("Screen4TxtColor");

                            //screen Text colors
                            screen1Img = remoteJson.getString("Screen1ImgUrl");
                            screen2Img = remoteJson.getString("Screen2ImgUrl");
                            screen3Img = remoteJson.getString("Screen3ImgUrl");
                            screen4Img = remoteJson.getString("Screen4ImgUrl");




                            if (URLUtil.isValidUrl(homeurl)) {
                                jsonUrl = homeurl;

                                try {
                                    URI uri = new URI(homeurl);
                                    String domain = uri.getHost();
                                    filterdomain = domain;
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }

                                if (EnableWelcomeSlider){
                                    Intent myactivity = new Intent(Splash.this, WelcomeSlider.class);
                                    startActivity(myactivity);
                                    finish();
                                } else {
                                    Intent myactivity = new Intent(Splash.this, WebActivity.class);
                                    myactivity.putExtra("url", jsonUrl);
                                    startActivity(myactivity);
                                    finish();
                                }


                            } else {
                                infotext.setText(R.string.invalide_remote_data);
                                progressBar.setVisibility(View.GONE);
                                if (retryBtn.getVisibility() == View.GONE) {
                                    retryBtn.setVisibility(View.VISIBLE);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            infotext.setText(e.getMessage());
                        }

                    }


                }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                infotext.setText("Error occurred! =" + error.toString());
                progressBar.setVisibility(View.GONE);

                if (retryBtn.getVisibility() == View.GONE) {
                    retryBtn.setVisibility(View.VISIBLE);
                }
            }
        });


// Add the request to the RequestQueue.
        queue.add(stringRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                queue.getCache().clear();
            }
        });
    }

    public void retryCall(View view) {
        clickcount++;
        if (clickcount==3){

            Intent myactivity = new Intent(Splash.this, SettingsActivity.class);
            startActivity(myactivity);
            ShowServerUrlSetUp = true;
        } else{
            ApiCall(Splash.this, Jsonurl);
            if (retryBtn.getVisibility() == View.VISIBLE) {
                retryBtn.setVisibility(View.GONE);
            }
        }
    }}

