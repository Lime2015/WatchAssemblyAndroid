package com.lime.watchassembly;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lime.watchassembly.kakao.KakaoExtraUserPropertyLayout;

import java.util.HashMap;

/**
 * Created by SeongSan on 2015-06-18.
 */
public class WASignupActivity extends Activity {

    private final String TAG = "WASignupActivity";
    private final String SERVER_URL = "http://192.168.50.184:9080";
    private final String SERVER_SAVE_MEMBER = "/WatchAssemblyWebServer/saveMember.do";

    Button btnWASignup;
    KakaoExtraUserPropertyLayout waExtraUserPropertyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSignup();
    }

    private void showSignup() {
        setContentView(R.layout.wa_signup);
        waExtraUserPropertyLayout = (KakaoExtraUserPropertyLayout) findViewById(R.id.wa_extra_user_property);
        btnWASignup = (Button) findViewById(R.id.btnWASignup);
        btnWASignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickSignup(waExtraUserPropertyLayout.getProperties());
            }
        });
    }

    /**
     * 가입 입력창의 정보를 모아서 가입 API를 호출한다.
     */
    private void onClickSignup(final HashMap<String, String> properties) {
        Log.d(TAG, "request saveMember.do extra signup info...");
    }
}
