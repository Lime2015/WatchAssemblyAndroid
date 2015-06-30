package com.lime.watchassembly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (memberInfo.getMemberId().equals("")) {
            getSupportActionBar().setTitle("둘러보기 >>");
        } else {
            getSupportActionBar().setTitle(memberInfo.getMemberNickname());
        }

        btnViewMypage = (Button) findViewById(R.id.btnViewMypage);
        btnViewMypage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(4);
            }
        });
        btnViewAssList = (Button) findViewById(R.id.btnViewAssemblymanList);
        btnViewAssList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(0);
            }
        });
        btnViewBillList = (Button) findViewById(R.id.btnViewBillList);
        btnViewBillList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(1);
            }
        });
        btnViewHall = (Button) findViewById(R.id.btnViewHallOfFame);
        btnViewHall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(2);
            }
        });
        btnViewPublic = (Button) findViewById(R.id.btnViewPublicOpinion);
        btnViewPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(3);
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

    private void viewSubmain(int index) {
        Intent intent = new Intent(this, SubmainActivity.class);
        intent.putExtra("memberInfo", memberInfo);
        intent.putExtra("index", index);
        startActivity(intent);
    }
}
