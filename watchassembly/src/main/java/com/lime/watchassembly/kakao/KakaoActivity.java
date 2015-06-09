package com.lime.watchassembly.kakao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.MeResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.kakao.helper.Logger;

/**
 * Created by Administrator on 2015-06-09.
 */
public class KakaoActivity extends Activity {
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra("MSG");

        if (message != null) {
            if (message.equals("login")) {
                redirectLoginActivity();
            } else if (message.equals("logout")) {
                redirectLogoutActivity();
            }
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            protected void onSuccess(UserProfile userProfile) {
                Toast.makeText(getApplicationContext(), userProfile.getNickname() + " 로그인 !!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            protected void onNotSignedUp() {
                finish();
            }

            @Override
            protected void onSessionClosedFailure(APIErrorResult apiErrorResult) {
                finish();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
                finish();
            }
        });
    }

    private void redirectLoginActivity() {
        Intent intent = new Intent(this, KakaoLoginActivity.class);
        startActivity(intent);
        //finish();
    }

    private void redirectSignupActivity() {
        Intent intent = new Intent(this, KakaoSignupActivity.class);
        startActivity(intent);
        //finish();
    }

    private void redirectLogoutActivity() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            protected void onSuccess(long l) {
                Toast.makeText(getApplicationContext(), "로그아웃 !!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
                Toast.makeText(getApplicationContext(), "로그아웃 상태입니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
