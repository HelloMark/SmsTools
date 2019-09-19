package com.chinaboy.smstools.tools;

import android.util.Log;
import android.widget.Toast;

import com.chinaboy.smstools.App;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {
    private static String TAG = "OkhttpUtils";
    static OkHttpClient okHttpClient = new OkHttpClient();

    public static void sendSms2Server(String phone,String body){
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", phone)
                .add("body", body)
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                App.getContext().showToast(e.getMessage());
            //    Toast.makeText(App.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }
}
