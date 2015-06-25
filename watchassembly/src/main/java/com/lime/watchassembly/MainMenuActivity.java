package com.lime.watchassembly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.lime.watchassembly.vo.MemberInfo;
import com.ogaclejapan.arclayout.Arc;
import com.ogaclejapan.arclayout.ArcLayout;

/**
 * Created by SeongSan on 2015-06-24.
 */
public class MainMenuActivity extends Activity implements OnClickListener {

    private static final String TAG="MainMenuActivity";


    TextView txtNickname;
    ArcLayout arcLayout;
    ImageButton btnLogout;
    MemberInfo memberInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        memberInfo = (MemberInfo)intent.getSerializableExtra("memberInfo");
        txtNickname = (TextView) findViewById(R.id.txtNickname);
        txtNickname.setText(memberInfo.getMemberNickname());

        btnLogout = (ImageButton)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(memberInfo.getMemberId().equals("")){
                    redirectMainActivity();
                }else{
                    redirectLogoutActivity();
                }
            }
        });

        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        arcLayout.setArc(Arc.CENTER);

        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }

    }

    private void redirectLogoutActivity() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            protected void onSuccess(long l) {
                Log.d(TAG, "로그아웃");

                memberInfo = null;
//                txtNickname.setText("");
                redirectMainActivity();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
            }
        });
    }

    private void redirectMainActivity() {
        Intent intent = new Intent(this, MainLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {

        }
    }
}
