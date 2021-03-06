﻿
package com.lime.watchassembly;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.kakao.UserProfile;
import com.lime.watchassembly.db.WatchAssemblyDatabase;
import com.lime.watchassembly.kakao.KakaoActivity;
import com.lime.watchassembly.vo.MemberInfo;

public class MainLoginTypeActivity extends Activity {

    private final String TAG="MainLoginTypeActivity";

    private ImageButton btnKakaoLogin;
    private Button btnLoginFree;

    private WatchAssemblyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        btnKakaoLogin = (ImageButton) findViewById(R.id.btnKakaoLogin);
        btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoLogin();
            }
        });

        btnLoginFree = (Button) findViewById(R.id.btnLoginFree);
        btnLoginFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFree();
            }
        });

        initializeDatabase();
        checkLogon();
    }

    private void checkLogon(){
        UserProfile userProfile = UserProfile.loadFromCache();
        if (userProfile != null && userProfile.getId() > 0) {
            kakaoLogin();
        }
    }

    private void kakaoLogin() {
        Intent intent = new Intent(this, KakaoActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginFree() {
        // MainMenuActivity
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("memberInfo", new MemberInfo());
        startActivity(intent);
        finish();
    }


    private void initializeDatabase() {
        if (database != null) {
            database.close();
            database = null;
        }

        database = WatchAssemblyDatabase.getInstance(this);
        boolean isOpen = database.open();
        if (isOpen) {
            Log.d(TAG, "WatchAssembly database is open.");
        } else {
            Log.d(TAG, "WatchAssembly database is not open.");
        }

        Log.d(TAG, "initializeDatabase success!!");
    }
}
