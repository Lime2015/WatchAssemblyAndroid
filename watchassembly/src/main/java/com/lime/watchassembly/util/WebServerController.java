package com.lime.watchassembly.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lime.watchassembly.kakao.KakaoExtraUserPropertyLayout;
import com.lime.watchassembly.vo.MemberInfo;
import com.lime.watchassembly.vo.ServerResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015-06-12.
 */
public class WebServerController {

    private final String TAG = "WebServerController";
    private final String SERVER_URL = "http://192.168.50.184:9080";
    private final String SERVER_CHECK_MEMBER = "/WatchAssemblyWebServer/checkMember.do";
    private final String SERVER_SAVE_MEMBER = "/WatchAssemblyWebServer/saveMember.do";

    private ServerResult serverResult;

    //Progress Dialog Object
    ProgressDialog prgDialog;

    public WebServerController() {
        serverResult = new ServerResult();
    }

    public int checkMember(MemberInfo memberInfo) {

        Log.d(TAG, "start checking server member...");

        Gson gson = new GsonBuilder().create();
        StringBuilder stringBuilder = new StringBuilder();

        HttpParams params = new BasicHttpParams();
//        params.setParameter("data", auth);
        HttpClient httpClient = new DefaultHttpClient(params);

        HttpPost httpPost = new HttpPost(SERVER_URL + SERVER_CHECK_MEMBER);

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("memberJSON", gson.toJson(memberInfo)));

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
            entity.setContentEncoding(HTTP.UTF_8);
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d(TAG, "Failed to checkMember:" + statusCode);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        Log.d(TAG, "response:" + stringBuilder.toString());
        serverResult = gson.fromJson(stringBuilder.toString(), ServerResult.class);
        return serverResult.getResult();

//        BasicHttpClient httpClient = new BasicHttpClient(SERVER_URL);
//        Gson gson = new GsonBuilder().create();
//
//        Log.d(TAG, "memberJSON:" + gson.toJson(memberInfo));
//
//        ParameterMap params = httpClient.newParams()
//                .add("memberJSON", gson.toJson(memberInfo));
//        httpClient.addHeader("someheader", "value");
//        httpClient.setConnectionTimeout(2000); // 2s
//        HttpResponse httpResponse = httpClient.post(SERVER_CHECK_MEMBER, params);
//
//        Log.d(TAG, "httpResponse.getBodyAsString():" + httpResponse.getBodyAsString());
//
//        serverResult = gson.fromJson(httpResponse.getBodyAsString(), new TypeToken<ServerResult>(){}.getType());
//
//        return serverResult.isResult();

//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        Gson gson = new GsonBuilder().create();
//
//        Log.d(TAG, "memberJSON:" + gson.toJson(memberInfo));
//        params.put("memberJSON", gson.toJson(memberInfo));
//
//        prgDialog.setMessage("check member...");
//        prgDialog.show();
//        client.post(SERVER_URL + SERVER_CHECK_MEMBER, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(String content) {
//                Log.d(TAG, "AsyncHttpClient response result:" + content);
//
//                Gson gson = new GsonBuilder().create();
//                serverResult = gson.fromJson(content, ServerResult.class);
//                prgDialog.hide();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Throwable error, String content) {
//                prgDialog.hide();
//                Log.d(TAG, "AsyncHttpClient response fail:" + statusCode);
//                Toast.makeText(context,"AsyncHttpClient response fail:" + statusCode,Toast.LENGTH_LONG).show();
//            }
//        });

//        Log.d(TAG, "AsyncHttpClient serverResult result:" + serverResult.getResult());

    }


}
