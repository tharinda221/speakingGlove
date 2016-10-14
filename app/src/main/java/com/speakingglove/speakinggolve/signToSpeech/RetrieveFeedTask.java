package com.speakingglove.speakinggolve.signToSpeech;

import android.os.AsyncTask;

import com.speakingglove.speakinggolve.SpeechRecognizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.parsers.SAXParser;

/**
 * Created by ehelepola on 14/10/2016.
 */
public class RetrieveFeedTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
            HttpClient client = new DefaultHttpClient();
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
            return result.toString();
        } catch (Exception e) {
            this.exception = e;

            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        SpeechRecognizer.result = result;
    }

}