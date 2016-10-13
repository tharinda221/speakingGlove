package com.speakingglove.speakinggolve;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognizer extends Activity {

    protected ImageButton imgBtn;
    protected ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognizer);

        imgBtn = (ImageButton) findViewById(R.id.imageButton);
        imgView = (ImageView) findViewById(R.id.imageView);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

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
}
