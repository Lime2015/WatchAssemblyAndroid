package com.lime.watchassembly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lime.watchassembly.layout.ExtraUserPropertyLayout;
import com.lime.watchassembly.vo.MemberInfo;
import com.lime.watchassembly.vo.ServerResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by SeongSan on 2015-06-18.
 */
public class WASignupActivity extends Activity {

    private final String TAG = "WASignupActivity";
    private final String SERVER_URL = "http://52.69.102.82:8080";
    private final String SERVER_SAVE_MEMBER = "/WatchAssemblyWebServer/saveMember.do";

    // property keyQu
    private  static final String ADDRESS_KEY = "address";
    private  static final String BIRTHDAY_KEY = "birthday";
    private  static final String GENDER_KEY = "gender";

    Button btnWASignup;
    ExtraUserPropertyLayout waExtraUserPropertyLayout;
    MemberInfo kakaoMemberInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSignup();
    }

    private void showSignup() {

        Intent intent = getIntent();
        kakaoMemberInfo = (MemberInfo)intent.getSerializableExtra("kakaoMemberInfo");

        setContentView(R.layout.wa_signup);
        waExtraUserPropertyLayout = (ExtraUserPropertyLayout) findViewById(R.id.wa_extra_user_property);
        btnWASignup = (Button) findViewById(R.id.btnWASignup);
        btnWASignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickSignup(waExtraUserPropertyLayout.getProperties());
            }
        });
    }

    /**
     * ���� �Է�â�� ������ ��Ƽ� ���� API�� ȣ���Ѵ�.
     */
    private void onClickSignup(final HashMap<String, String> properties) {
        Log.d(TAG, "request saveMember.do extra signup info...");

        // save extra info
        final String strAddress = properties.get(ADDRESS_KEY);
        if (strAddress != null)
            kakaoMemberInfo.setAddress(strAddress);

        final String strBirthday = properties.get(BIRTHDAY_KEY);
        if (strBirthday != null) {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = null;
            try {
                date = transFormat.parse(strBirthday);
                kakaoMemberInfo.setBirthDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        final String strGenger = properties.get(GENDER_KEY);
        if (strGenger != null)
            kakaoMemberInfo.setGender(strGenger);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Gson gson = new GsonBuilder().create();

        Log.d(TAG, "memberJSON:" + gson.toJson(kakaoMemberInfo));
        params.put("memberJSON", gson.toJson(kakaoMemberInfo));

//        prgDialog.setMessage("check member...");
//        prgDialog.show();
        client.post(SERVER_URL + SERVER_SAVE_MEMBER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
//                prgDialog.hide();

                Log.d(TAG, "AsyncHttpClient response result:" + content);

                Gson gson = new GsonBuilder().create();
                ServerResult serverResult = gson.fromJson(content, ServerResult.class);

                if (serverResult.getResult() == 1) {
                    // �ű�ȸ�� ��� ����
                    Log.d(TAG, "complete to signup in server");
                    Toast.makeText(getApplicationContext(), "Success Signup !!", Toast.LENGTH_LONG).show();

                    Intent retIntent = new Intent();
                    retIntent.putExtra("kakaoMemberInfo", kakaoMemberInfo);
                    setResult(RESULT_OK, retIntent);
                }else{
                    // �ű�ȸ�� ó�� ����
                    Log.d(TAG, "fail to request new member info in server");
                    Toast.makeText(getApplicationContext(), "fail to request new member info in server", Toast.LENGTH_LONG).show();

                    Intent retIntent = new Intent();
                    retIntent.putExtra("kakaoMemberInfo", kakaoMemberInfo);
                    setResult(RESULT_CANCELED, retIntent);
                }
                finish();
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
//                prgDialog.hide();
                Log.d(TAG, "AsyncHttpClient response fail:" + statusCode);
                Toast.makeText(getApplicationContext(), "AsyncHttpClient response fail:" + statusCode, Toast.LENGTH_LONG).show();

                Intent retIntent = new Intent();
                retIntent.putExtra("kakaoMemberInfo", kakaoMemberInfo);
                setResult(RESULT_CANCELED, retIntent);
                finish();
            }
        });
    }

}
