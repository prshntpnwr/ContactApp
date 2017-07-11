package com.example.prashant.contactapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.fragments.SmsDetailFragment;

public class SmsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.message_detail_container, new SmsDetailFragment())
                    .commit();

            Log.d(TAG, "Transition happened");
        }
    }
}
