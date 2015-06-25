package com.lime.watchassembly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.UserProfile;
import com.kakao.exception.KakaoException;
import com.kakao.widget.LoginButton;
import com.lime.template.loginbase.SampleSignupActivity;
import com.lime.watchassembly.kakao.KakaoActivity;
import com.lime.watchassembly.vo.MemberInfo;


public class MainLoginTypeActivity extends Activity {

    private final String TAG="MainLoginTypeActivity";

    private ImageButton btnKakaoLogin;
    private ImageButton btnLoginFree;


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
        btnLoginFree = (ImageButton) findViewById(R.id.btnLoginFree);
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
        // MainMenuActivity
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("memberInfo", new MemberInfo());
        startActivity(intent);
        finish();
    }

}
