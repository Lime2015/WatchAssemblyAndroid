package com.lime.watchassembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

/**
 * Created by SeongSan on 2015-06-24.
 */
public class MainMenuActivity extends ActionBarActivity {

    private static final String TAG = "MainMenuActivity";

    TextView txtNickname;
    ImageButton btnLogout;
    MemberInfo memberInfo;

    Button btnViewMypage;
    Button btnViewAssList;
    Button btnViewBillList;
    Button btnViewHall;
    Button btnViewPublic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        memberInfo = (MemberInfo) intent.getSerializableExtra("memberInfo");
        txtNickname = (TextView) findViewById(R.id.txtNickname);
        txtNickname.setText(memberInfo.getMemberNickname());

        btnLogout = (ImageButton) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (memberInfo.getMemberId().equals("")) {
                    redirectMainActivity();
                } else {
                    redirectLogoutActivity();
                }
            }
        });

        btnViewMypage = (Button) findViewById(R.id.btnViewMypage);
        btnViewMypage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMypage();
            }
        });
        btnViewAssList = (Button) findViewById(R.id.btnViewAssemblymanList);
        btnViewAssList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAssemblymanList();
            }
        });
        btnViewBillList = (Button) findViewById(R.id.btnViewBillList);
        btnViewBillList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBillList();
            }
        });
        btnViewHall = (Button) findViewById(R.id.btnViewHallOfFame);
        btnViewHall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHallOfFame();
            }
        });
        btnViewPublic = (Button) findViewById(R.id.btnViewPublicOpinion);
        btnViewPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPublicOpinion();
            }
        });

        if (memberInfo.getMemberId().equals("")) {
            btnViewMypage.setEnabled(false);
            btnViewMypage.setAlpha(0.3f);
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

    public void redirectMainActivity() {
        Intent intent = new Intent(this, MainLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }

    private void viewMypage() {

    }

    private void viewPublicOpinion() {

    }

    private void viewHallOfFame() {

    }

    private void viewBillList() {

    }

    private void viewAssemblymanList() {
        Intent intent = new Intent(this, AssemblymanListActivity.class);
        startActivity(intent);
    }
}
