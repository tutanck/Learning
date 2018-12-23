package com.example.ajoan.clickcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    public final static String COUNTER_KEY ="COUNTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        TextView shwnbclics = (TextView) findViewById(R.id.shownbclics);

        shwnbclics.setText(getIntent().getStringExtra(COUNTER_KEY));
    }
}
