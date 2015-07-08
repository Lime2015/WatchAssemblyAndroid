package com.lime.watchassembly.kakao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.Session;
import com.lime.template.loginbase.SampleLoginActivity;
import com.lime.watchassembly.R;

/**
 * Created by Administrator on 2015-06-09.
 */
public class KakaoLoginActivity extends SampleLoginActivity {

    private final String TAG = "KakaoLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackground(getResources().getDrawable(R.drawable.usermgmt_sample_login_background));
    }

    /**
     * 세션이 오픈되었으면 가입페이지로 이동
     */
    @Override
    protected void onSessionOpened() {
        final Intent intent = new Intent(KakaoLoginActivity.this, KakaoSignupActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            redirectKakaoActivity();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void redirectKakaoActivity() {

        Log.d(TAG, "카카오 로그인 성공!!");
        Intent intent = new Intent(this, KakaoActivity.class);
        startActivity(intent);
        finish();
    }
}