package com.lime.watchassembly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lime.watchassembly.vo.MemberInfo;
import com.ogaclejapan.arclayout.Arc;
import com.ogaclejapan.arclayout.ArcLayout;

/**
 * Created by SeongSan on 2015-06-24.
 */
public class MainMenuActivity extends Activity implements OnClickListener {

    private static final String TAB="MainMenuActivity";


    TextView txtNickname;
    ArcLayout arcLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        MemberInfo memberInfo = (MemberInfo)intent.getSerializableExtra("memberInfo");
        txtNickname = (TextView) findViewById(R.id.txtNickname);
        txtNickname.setText(memberInfo.getMemberNickname());

        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        arcLayout.setArc(Arc.CENTER);

        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {

        }
    }
}
