package com.lime.watchassembly.util;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lime.watchassembly.vo.MemberInfo;
import com.lime.watchassembly.vo.ServerResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by Administrator on 2015-06-12.
 */
public class WebServerController {

    private final String TAG = "WebServerController";
    private final String SERVER_URL = "http://192.168.50.184:9080";
    private final String SERVER_CHECK_MEMBER = "/WatchAssemblyWebServer/checkMember.do";
    private final String SERVER_SAVE_MEMBER = "/WatchAssemblyWebServer/saveMember.do";

    private ServerResult serverResult;

    public WebServerController() {
        serverResult = new ServerResult();
    }

    public boolean checkMember(MemberInfo memberInfo) {

        Log.d(TAG, "start checking server member...");

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

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Gson gson = new GsonBuilder().create();

        Log.d(TAG, "memberJSON:" + gson.toJson(memberInfo));
        params.put("memberJSON", gson.toJson(memberInfo));

        client.post(SERVER_URL + SERVER_CHECK_MEMBER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                Log.d(TAG, "AsyncHttpClient response result:" + content);

                Gson gson = new GsonBuilder().create();
                serverResult = gson.fromJson(content, ServerResult.class);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "AsyncHttpClient response fail:" + statusCode);
            }
        });

        Log.d(TAG, "AsyncHttpClient serverResult result:" + serverResult.getResult());
        return true;

    }


}
