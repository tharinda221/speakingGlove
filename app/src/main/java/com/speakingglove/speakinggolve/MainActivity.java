package com.speakingglove.speakinggolve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    Button btnClickMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intialization Button

        btnClickMe = (Button) findViewById(R.id.start);

        btnClickMe.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {

        //Your Logic
//        Log.d("message", getSignCommand("1000"));
        Intent intent = new Intent(MainActivity.this, configIP.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static String getSignCommand(String input) {
        String [] words = {"hello", "help me", "i'm fine", " i'm hungry"};
        if(input.equals("1000")) {
            return words[0];
        } else if(input.equals("11111")) {
            return words[1];
        } else if(input.equals("01111")) {
            return words[2];
        } else {
            return words[3];
        }
    }

}
