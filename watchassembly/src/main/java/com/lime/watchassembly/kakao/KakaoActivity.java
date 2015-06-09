package com.lime.watchassembly.kakao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        userProfile = UserProfile.loadFromCache();
        if(userProfile != null) {
            // 로그인 정보를 MainActivity에 전달
        } else{
            // 로그인 상태가 아니면 로그인 화면으로
            redirectLoginActivity();
        }
    }

    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            protected void onSuccess(final long userId) {
                redirectLoginActivity();
            }

            @Override
            protected void onFailure(final APIErrorResult apiErrorResult) {
                Logger.getInstance().d("failed to sign up. msg=" + apiErrorResult);
                redirectLoginActivity();
            }
        });
    }

    private void redirectLoginActivity() {
        Intent intent = new Intent(this, KakaoLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
