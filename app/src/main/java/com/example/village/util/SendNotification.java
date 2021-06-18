package com.example.village.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONObject;

public class SendNotification {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void sendNotification(String regToken, String title, String messsage) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... parms) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("body", messsage);
                    dataJson.put("title", title);
                    json.put("notification", dataJson);
                    json.put("to", regToken);
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key=" + "AAAAhndylog:APA91bHSg_FRfhJCpCVogr-pVGNPPerRCS5GBtKhBZnPqzl0LhnxU29aWWLp69-tu2LWKYiuU6BgDKMUJgECeDv1D2QPjVxkDTjG2j35AYvpkUytBZ1QpZP03wQJVqeF33p1KcMDUVR-")
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                } catch (Exception e) {
                    Log.d("error", e + "");
                }
                return null;
            }
        }.execute();
    }
}
