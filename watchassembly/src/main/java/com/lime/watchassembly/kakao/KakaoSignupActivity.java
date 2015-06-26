package com.lime.watchassembly.kakao;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import com.kakao.APIErrorResult;
import com.kakao.SignupResponseCallback;
import com.kakao.UserManagement;
import com.lime.template.loginbase.SampleSignupActivity;
import com.lime.watchassembly.R;
import com.lime.watchassembly.layout.ExtraUserPropertyFrame;

import java.util.HashMap;

/**
 * Created by Administrator on 2015-06-09.
 */
public class KakaoSignupActivity extends SampleSignupActivity {

    /**
     * debug TAG
     */
    private final String TAG = "KakaoSignupActivity";

    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, KakaoLoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected void redirectMainActivity() {
        final Intent intent = new Intent(this, KakaoActivity.class);
        startActivity(intent);
        finish();
    }

    protected void showSignup() {
        setContentView(R.layout.kakao_signup);
        final ExtraUserPropertyFrame extraUserPropertyFrame = (ExtraUserPropertyFrame) findViewById(R.id.extra_user_property);
        Button signupButton = (Button) findViewById(R.id.buttonSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickSignup(extraUserPropertyFrame.getProperties());
            }
        });
    }
    /**
     * 가입 입력창의 정보를 모아서 가입 API를 호출한다.
     */
    private void onClickSignup(final HashMap<String, String> properties) {
        UserManagement.requestSignup(new SignupResponseCallback() {
            @Override
            protected void onSuccess(final long userId) {
                redirectMainActivity();
            }

            @Override
            protected void onSessionClosedFailure(final APIErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            protected void onFailure(final APIErrorResult errorResult) {
                String message = "failed to sign up. msg=" + errorResult;
                Log.d(TAG, message);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }, properties);
    }
}
