package com.lime.watchassembly.kakao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.lime.watchassembly.MainLoginTypeActivity;
import com.lime.watchassembly.MainMenuActivity;
import com.lime.watchassembly.R;

import com.lime.watchassembly.WASignupActivity;
import com.lime.watchassembly.db.WatchAssemblyDatabase;
import com.lime.watchassembly.util.GZip;
import com.lime.watchassembly.util.WebServerController;
import com.lime.watchassembly.vo.MemberInfo;
import com.lime.watchassembly.vo.ServerResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2015-06-09.
 */
public class KakaoActivity extends Activity {


    private final String TAG = "KakaoActivity";
        private final String SERVER_URL = "http://52.69.102.82:8080";
//    private final String SERVER_URL = "http://192.168.0.9:9080";
    private final String SERVER_CHECK_MEMBER = "/WatchAssemblyWebServer/checkMember.do";
//    private final String SERVER_SAVE_MEMBER = "/WatchAssemblyWebServer/saveMember.do";

    private final int WA_SIGNUP_CODE = 1100;

    private MemberInfo kakaoMemberInfo;
    private UserProfile userProfile;

//    private Button btnLogout;
//    private TextView txtNickname;
//    private FlatButton btnLogout;
//    private FlatTextView txtNickname;

//    private WebServerController controller;

    //Progress Dialog Object
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
//        checkLoginInfo();
        redirectLoginActivity();

    }

    private void initializeView() {
        setContentView(R.layout.kakao_main);

        kakaoMemberInfo = null;
        userProfile = UserProfile.loadFromCache();
//        controller = new WebServerController();

//        txtNickname = (TextView) findViewById(R.id.txtNickname);
//        btnLogout = (Button) findViewById(R.id.btnLogout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                redirectLogoutActivity();
//            }
//        });

        //Initialize Progress Dialog properties
        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);

        Log.d(TAG, "initializeView success!!");
//        // Initialize the ViewPager and set an adapter
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(new TestAdapter(getSupportFragmentManager()));
//
//        // Bind the tabs to the ViewPager
//        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        tabs.setViewPager(pager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoginInfo();
    }

    private void checkLoginInfo() {
        Log.d(TAG, "checkLoginInfo start >>");
        if (kakaoMemberInfo == null && userProfile != null) {
            long id = userProfile.getId();
            String nickname = userProfile.getNickname();

            if (id > 0) {
                Log.d(TAG, "로그인정보:" + id + "/" + nickname);

                kakaoMemberInfo = new MemberInfo("" + id, 1, nickname);
//                txtNickname.setText(kakaoMemberInfo.getMemberNickname());

                // web server 회원인지 체크
                checkMemberInServer();
            }
        }
    }

    /**
     * check web server member
     */
    private void checkMemberInServer() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Gson gson = new GsonBuilder().create();

        Log.d(TAG, "memberJSON:" + gson.toJson(kakaoMemberInfo));
        params.put("memberJSON", gson.toJson(kakaoMemberInfo));

//        prgDialog.setMessage("check member...");
//        prgDialog.show();

//        client.addHeader("Accept-Encoding", "gzip");
        client.post(SERVER_URL + SERVER_CHECK_MEMBER, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                super.onSuccess(statusCode, headers, responseBody);
                String content = new String(responseBody);
//                prgDialog.hide();

                Log.d(TAG, "AsyncHttpClient response result:" + content);

                Gson gson = new GsonBuilder().create();
                ServerResult serverResult = gson.fromJson(content, ServerResult.class);

                if (serverResult.getResult() == 0) {
                    // 신규회원
                    redirectWASignupActivity();
                } else {
                    // 기존회원
                    showMyPage();
                }
            }

            //            @Override
//            public void onSuccess(String content) {
////                prgDialog.hide();
//
//                Log.d(TAG, "AsyncHttpClient response result:" + content);
//
//                Gson gson = new GsonBuilder().create();
//                ServerResult serverResult = gson.fromJson(content, ServerResult.class);
//
//                if (serverResult.getResult() == 0) {
//                    // 신규회원
//                    redirectWASignupActivity();
//                } else {
//                    // 기존회원
//                    showMyPage();
//                }
//            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
//                prgDialog.hide();
                Log.d(TAG, "AsyncHttpClient response fail:" + statusCode);
                Toast.makeText(getApplicationContext(), "서버연결실패!!" + statusCode, Toast.LENGTH_LONG).show();
                redirectMainActivity();
            }
        });
    }

    private void redirectWASignupActivity() {
        Intent intent = new Intent(this, WASignupActivity.class);
        intent.putExtra("kakaoMemberInfo", kakaoMemberInfo);
        startActivityForResult(intent, WA_SIGNUP_CODE);
        finish();
    }


//        @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
//
////        Toast.makeText(getApplicationContext(), "WA_SIGNUP_CODE:" + WA_SIGNUP_CODE + " RESULT_OK:" + RESULT_OK, Toast.LENGTH_LONG).show();
//        Log.d(TAG, "WA_SIGNUP_CODE:" + requestCode + " RESULT_OK:" + resultCode);
//
//        if (requestCode == WA_SIGNUP_CODE) {
//            if (resultCode == RESULT_OK) {
//                kakaoMemberInfo = (MemberInfo) data.getSerializableExtra("kakaoMemberInfo");
//                showMyPage();
//            }
//        }
//    }

    private void showMyPage() {
//        Toast.makeText(getApplicationContext(), "Show MyPage !!", Toast.LENGTH_LONG).show();

        // MainMenuActivity
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("memberInfo", kakaoMemberInfo);
        startActivity(intent);
        finish();
    }

    private void redirectLoginActivity() {
        if (userProfile == null || userProfile.getId() < 0) {
            Log.d(TAG, "카카오 로그인 시도 시작");
            Intent intent = new Intent(this, KakaoLoginActivity.class);
            startActivity(intent);
            finish();
        }

        Log.d(TAG, "redirectLoginActivity success!!");
    }

    private void redirectMainActivity() {
        Intent intent = new Intent(this, MainLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectLogoutActivity() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            protected void onSuccess(long l) {
                Log.d(TAG, "로그아웃");

                kakaoMemberInfo = null;
//                txtNickname.setText("");
                redirectMainActivity();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
            }
        });
    }

//    private class CheckMemberTask extends AsyncTask<MemberInfo, Void, Integer> {
//
//        private final String TAG = "ServerCheckMember";
//        private final String SERVER_URL = "http://192.168.50.184:9080/WatchAssemblyWebServer/checkMember.do";
//
//        @Override
//        protected Integer doInBackground(MemberInfo... memberInfos) {
//
//            Log.d(TAG, "start checking server member...");
//
//            Gson gson = new GsonBuilder().create();
//            StringBuilder stringBuilder = new StringBuilder();
//
//            HttpParams params = new BasicHttpParams();
////        params.setParameter("data", auth);
//            HttpClient httpClient = new DefaultHttpClient(params);
//
//            HttpPost httpPost = new HttpPost(SERVER_URL);
//
//            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
//            postParams.add(new BasicNameValuePair("memberJSON", gson.toJson(memberInfos[0])));
//
//            try {
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
//                entity.setContentEncoding(HTTP.UTF_8);
//                httpPost.setEntity(entity);
//
//                HttpResponse response = httpClient.execute(httpPost);
//                StatusLine statusLine = response.getStatusLine();
//                int statusCode = statusLine.getStatusCode();
//
//                if (statusCode == 200) {
//                    HttpEntity httpEntity = response.getEntity();
//                    InputStream inputStream = httpEntity.getContent();
//                    BufferedReader reader = new BufferedReader(
//                            new InputStreamReader(inputStream));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        stringBuilder.append(line);
//                    }
//                    inputStream.close();
//                } else {
//                    Log.d(TAG, "Failed to checkMember:" + statusCode);
//                }
//            } catch (Exception e) {
//                Log.d(TAG, e.toString());
//            }
//
//            Log.d(TAG, "response:" + stringBuilder.toString());
//            ServerResult serverResult = gson.fromJson(stringBuilder.toString(), ServerResult.class);
//            return serverResult.getResult();
//        }
//    }
}
