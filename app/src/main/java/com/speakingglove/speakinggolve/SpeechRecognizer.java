package com.speakingglove.speakinggolve;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.speakingglove.speakinggolve.signToSpeech.SignToSpeech;
import com.speakingglove.speakinggolve.signToSpeech.RetrieveFeedTask;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeechRecognizer extends Activity {

    protected ImageButton imgBtn;
    protected ImageView imgView;
    android.speech.tts.TextToSpeech t1;
    protected ImageButton speech;
    private boolean speechButton = true;
    public static String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognizer);

        imgBtn = (ImageButton) findViewById(R.id.imageButton);
        imgView = (ImageView) findViewById(R.id.imageView);
        speech = (ImageButton) findViewById(R.id.speech);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        t1=new android.speech.tts.TextToSpeech(getApplicationContext(), new android.speech.tts.TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != android.speech.tts.TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(speechButton) {
                    speechButton = false;
                }
//                while (true) {
//                    if(speechButton) {
//                        break;
//                    }
//                    SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
//                    String IP = prefs.getString("IP", null);
//                    if (IP != null) {
//                        IP = prefs.getString("IP", "No name defined");//"No name defined" is the default value.
//                    }
//                    try {
//                        getSignFromDevice(IP);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
                String IP = prefs.getString("IP", null);
                if (IP != null) {
                    IP = prefs.getString("IP", "No name defined");//"No name defined" is the default value.
                }
                try {
                    getSignFromDevice(IP);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void getSignFromDevice(String IP) throws IOException {

        new RetrieveFeedTask().execute(IP);
        Log.d("IP", IP);
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "NOt supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String res = result.get(0);
                    Toast.makeText(SpeechRecognizer.this, res, Toast.LENGTH_SHORT).show();

                    if (Constants.wordImgMap.containsKey(res)) {
                        if (res.contains("hello") || res.contains("hi")) {
                            imgView.setBackgroundResource(R.drawable.hi);
                        } else if (res.contains("hungry")) {
                            imgView.setBackgroundResource(R.drawable.hungry);
                        } else if (res.contains("help")) {
                            imgView.setBackgroundResource(R.drawable.help);
                        } else if (res.contains("fine") || res.contains("ok")) {
                            imgView.setBackgroundResource(R.drawable.fine);
                        }
                        else {
                            imgView.setBackgroundResource(R.drawable.noword);
                        }
                    }

                }
                break;
            }

        }
    }

    private class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                HttpClient client = new DefaultHttpClient();
                String ip = urls[0];
                String url = "http://" + ip + ":5000/";

                HttpGet request = new HttpGet(url);
                HttpResponse response = client.execute(request);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                Log.d("Result", result.toString());
                return result.toString();
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Result", result);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            t1.speak(getSignCommand(result), android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
        }

        public String getSignCommand(String input) {
            String [] words = {"hello", "help me", "i'm fine", " i'm hungry"};
            if(input.equals("10000")) {
                return words[0];
            } else if(input.equals("11111")) {
                return words[1];
            } else if(input.equals("01111")) {
                return words[2];
            } else if(input.equals("10011")) {
                return words[3];
            } else {
                return "Wrong Sign";
            }
        }
    }
}
