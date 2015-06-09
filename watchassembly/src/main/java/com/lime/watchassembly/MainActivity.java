package com.lime.watchassembly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lime.watchassembly.kakao.KakaoActivity;


public class MainActivity extends Activity {

    private Button btnKakao;
    private Button btnPreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnKakao = (Button) findViewById(R.id.btnKakao);
        btnKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryKakaoLogin();
            }
        });
        btnPreView = (Button) findViewById(R.id.btnPreView);
    }


    private void tryKakaoLogin(){
        Intent intent = new Intent(this, KakaoActivity.class);
        startActivity(intent);
        finish();
    }
}
