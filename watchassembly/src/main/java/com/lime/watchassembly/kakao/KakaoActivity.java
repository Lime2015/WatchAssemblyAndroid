package com.lime.watchassembly.kakao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.lime.watchassembly.MainActivity;
import com.lime.watchassembly.R;

import com.lime.watchassembly.db.WatchAssemblyDatabase;
import com.lime.watchassembly.util.WebServerController;
import com.lime.watchassembly.vo.MemberInfo;

/**
 * Created by Administrator on 2015-06-09.
 */
public class KakaoActivity extends Activity {

    private final String TAG = "KakaoActivity";

    private MemberInfo kakaoMemberInfo;
    private UserProfile userProfile;

    private Button btnLogout;
    private TextView txtNickname;

    private WatchAssemblyDatabase database;
    private WebServerController controller;

    //Progress Dialog Object
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        redirectLoginActivity();
        initializeDatabase();
    }

    private void initializeView() {
        setContentView(R.layout.kakao_main);

        kakaoMemberInfo = null;
        userProfile = UserProfile.loadFromCache();
        controller = new WebServerController();

        txtNickname = (TextView) findViewById(R.id.txtNickname);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectLogoutActivity();
            }
        });

        //Initialize Progress Dialog properties
        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);
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
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (kakaoMemberInfo == null && userProfile != null) {
            long id = userProfile.getId();
            String nickname = userProfile.getNickname();

            if (id > 0) {
                Log.d(TAG, "로그인정보:" + id + "/" + nickname);

                kakaoMemberInfo = new MemberInfo("" + id, 1, nickname);
                txtNickname.setText(kakaoMemberInfo.getMemberNickname());

                // web server 회원인지 체크
                checkMemberInServer();
            }
        }
    }

    /**
     * check web server member
     */
    private void checkMemberInServer(){
        boolean result;
        prgDialog.setMessage("check member...");
        prgDialog.show();
        result = controller.checkMember(kakaoMemberInfo);
        prgDialog.hide();
        if(!result){
            Intent intent = new Intent(this, KakaoExtraUserPropertyLayout.class);
            startActivity(intent);
        }
    }

    private void redirectLoginActivity() {
        if (userProfile == null || userProfile.getId() < 0) {
            Log.d(TAG, "카카오 로그인 시도 시작");
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
                Log.d(TAG, "로그아웃");

                kakaoMemberInfo = null;
                txtNickname.setText("");
                redirectMainActivity();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
            }
        });
    }
}
