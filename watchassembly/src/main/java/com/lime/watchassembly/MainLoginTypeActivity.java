package com.lime.watchassembly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kakao.UserProfile;
import com.lime.watchassembly.kakao.KakaoActivity;


public class MainLoginTypeActivity extends Activity {

    private Button btnKakaoLogin;
    private Button btnLoginFree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        btnKakaoLogin = (Button) findViewById(R.id.btnKakaoLogin);
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

        finish();
    }
}
