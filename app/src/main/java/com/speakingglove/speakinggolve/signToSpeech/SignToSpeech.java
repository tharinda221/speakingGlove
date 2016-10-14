package com.speakingglove.speakinggolve.signToSpeech;

import android.os.Handler;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by ehelepola on 14/10/2016.
 */
public class SignToSpeech {
    private HttpClient client;
    private String IP;
    private static SignToSpeech instance = null;
    private FutureTask<String> future;

    private SignToSpeech() {
    }

    public static SignToSpeech getSignToSpeechInstance() {
        if(instance == null) {
            return new SignToSpeech();
        } else {
            return instance;
        }
    }

    public String getSignFromDevice() throws IOException {
        client = new DefaultHttpClient();
        String url = "http://www.google.com/search?q=httpClient";

        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        Log.d("IP", IP);
        Log.d("Web Result", result.toString());
        return "Hello";




//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//
//                requestHttp();
//            }
//        }, 100);
    }



    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public FutureTask<String> getFuture() {
        return future;
    }
}
