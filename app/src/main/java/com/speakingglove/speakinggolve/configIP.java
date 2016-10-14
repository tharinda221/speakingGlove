package com.speakingglove.speakinggolve;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.speakingglove.speakinggolve.signToSpeech.SignToSpeech;


public class configIP extends Activity {

    Button   mButton;
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_ip);

        mButton = (Button)findViewById(R.id.next);
        mEdit   = (EditText)findViewById(R.id.ipAddress);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        if (mEdit.getText() != null) {
                            SignToSpeech obj = SignToSpeech.getSignToSpeechInstance();
                            obj.setIP(mEdit.getText().toString());
                            SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                            editor.putString("IP", mEdit.getText().toString());
                            editor.commit();
                            Intent intent = new Intent(configIP.this, SpeechRecognizer.class);
                            startActivity(intent);

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(configIP.this);
                            builder.setCancelable(false);
                            builder.setTitle("Something is Not Correct");
                            builder.setMessage("Please add the IP address");
                            builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //
                                    dialog.dismiss();
                                }
                            });

                            // Create the AlertDialog object and return it
                            builder.create().show();
                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config_i, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
