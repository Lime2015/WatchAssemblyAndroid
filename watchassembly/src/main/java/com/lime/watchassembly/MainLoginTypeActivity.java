package com.lime.watchassembly;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.lime.template.loginbase.SampleLoginActivity;
import com.lime.template.loginbase.SampleSignupActivity;
import com.lime.watchassembly.db.WatchAssemblyDatabase;
import com.lime.watchassembly.kakao.KakaoActivity;
import com.lime.watchassembly.vo.MemberInfo;


public class MainLoginTypeActivity extends Activity {

    private final String TAG="MainLoginTypeActivity";

//    private ImageButton btnKakaoLogin;
    private Button btnLoginFree;

    private final String SERVER_URL = "http://52.69.102.82:8080";
    //    private final String SERVER_URL = "http://192.168.0.9:9080";
    private final String SERVER_CHECK_MEMBER = "/WatchAssemblyWebServer/checkMember.do";
//    private final String SERVER_SAVE_MEMBER = "/WatchAssemblyWebServer/saveMember.do";

    private final int WA_SIGNUP_CODE = 1100;

    private MemberInfo kakaoMemberInfo;
    private UserProfile userProfile;

    private WatchAssemblyDatabase database;

    //Progress Dialog Object
    ProgressDialog prgDialog;

    private LoginButton loginButton;
    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

//        btnKakaoLogin = (ImageButton) findViewById(R.id.btnKakaoLogin);
//        btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                kakaoLogin();
//            }
//        });

        btnLoginFree = (Button) findViewById(R.id.btnLoginFree);
        btnLoginFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFree();
            }
        });

        // 로그인 버튼을 찾아온다.
        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);

        session = Session.getCurrentSession();
        session.addCallback(mySessionCallback);

//        checkLogon();
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

    private class MySessionStatusCallback implements SessionCallback {
        /**
         * 세션이 오픈되었으면 가입페이지로 이동 한다.
         */
        @Override
        public void onSessionOpened() {
            //뺑글이 종료
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈후 보일 페이지로 이동
            MainLoginTypeActivity.this.onSessionOpened();
        }

        /**
         * 세션이 삭제되었으니 로그인 화면이 보여야 한다.
         * @param exception  에러가 발생하여 close가 된 경우 해당 exception
         */
        @Override
        public void onSessionClosed(final KakaoException exception) {
            //뺑글이 종료
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈을 못했으니 다시 로그인 버튼 노출.
            loginButton.setVisibility(View.VISIBLE);
//            onBackPressed();
        }

        @Override
        public void onSessionOpening() {
            //뺑글이 시작
        }

    }

    protected void onSessionOpened(){
        final Intent intent = new Intent(MainLoginTypeActivity.this, SampleSignupActivity.class);
        startActivity(intent);
        finish();
    }

    protected void setBackground(Drawable drawable) {
        final View root = findViewById(android.R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            root.setBackground(drawable);
        } else {
            root.setBackgroundDrawable(drawable);
        }
    }
}
