package com.example.prashant.contactapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.fragments.SmsDetailFragment;

/***
 * it is detail activity for MessageActivity
 * container for SmsDetailFragment
 */
public class SmsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.sms_detail_container, new SmsDetailFragment())
                    .commit();

            Log.d(TAG, "Transition happened to SmsDetailFragment");
        }
    }
}
