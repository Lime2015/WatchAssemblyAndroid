package com.lime.watchassembly.kakao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.kakao.helper.Logger;
import com.lime.watchassembly.MainActivity;
import com.lime.watchassembly.R;

import com.lime.watchassembly.db.WatchAssemblyDatabase;
import com.lime.watchassembly.vo.Member;

/**
 * Created by Administrator on 2015-06-09.
 */
public class KakaoActivity extends Activity {

    public static final String TAG = "KakaoActivity";

    private Member kakaoMember;
    private UserProfile userProfile;

    private Button btnLogout;
    private TextView txtNickname;

    private WatchAssemblyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        redirectLoginActivity();
        initializeDatabase();
    }

    private void initializeView() {
        setContentView(R.layout.kakao_main);

        userProfile = UserProfile.loadFromCache();

        txtNickname = (TextView) findViewById(R.id.txtNickname);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectLogoutActivity();
            }
        });
    }

    private void initializeDatabase() {
        if (database != null) {
            database.close();
            database = null;
        }

        database = WatchAssemblyDatabase.getInstance(this);
        boolean isOpen = database.open();
        if (isOpen) {
            Logger.getInstance().d(TAG + " WatchAssembly database is open.");
        } else {
            Logger.getInstance().d(TAG + " WatchAssembly database is not open.");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (userProfile != null) {
            long id = userProfile.getId();
            String nickname = userProfile.getNickname();

            if (id > 0) {
                Logger.getInstance().d(TAG + " 로그인정보:" + id + "/" + nickname);

                kakaoMember = new Member();
                kakaoMember.setId("" + id);
                kakaoMember.setNickname(nickname);
                txtNickname.setText(kakaoMember.getNickname());
            }
        }
    }

    private void redirectLoginActivity() {
        if (userProfile == null || userProfile.getId() < 0) {
            Intent intent = new Intent(this, KakaoLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectLogoutActivity() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            protected void onSuccess(long l) {
                Logger.getInstance().d(TAG + " 로그아웃");

                kakaoMember = null;
                txtNickname.setText("");
                redirectMainActivity();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
            }
        });
    }
}
