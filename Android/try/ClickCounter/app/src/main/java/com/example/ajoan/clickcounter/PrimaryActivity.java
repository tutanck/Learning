package com.example.ajoan.clickcounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrimaryActivity extends AppCompatActivity {

    private int counter =0;
    private Context mySelf=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        Button inc = (Button) findViewById(R.id.button);
        Button stats = (Button) findViewById(R.id.button2);
        final TextView tvtxt = (TextView) findViewById(R.id.nbclicstxt);
        final TextView tvnbc = (TextView) findViewById(R.id.nbclics);

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Status Before Inc", "ok");
                tvnbc.setText("" + ++counter);
                Log.i("Status After Inc", "ok");
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSTATS();
            }
        });
    }


    public void openSTATS(){
        Intent intent=new Intent(this,SecondaryActivity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
//      intent.putExtra(Intent.EXTRA_TEXT,""+counter); //default key
        intent.putExtra(SecondaryActivity.COUNTER_KEY,""+counter); //custom key
        startActivity(intent); //start new activity
//        finish(); //finish this activity
    }
}
